package idea.verlif.socket.command.server;

import idea.verlif.loader.jar.JarLoader;
import idea.verlif.socket.command.CommandParser;
import idea.verlif.socket.command.SocketCommand;
import idea.verlif.socket.command.config.ConfigAdapter;
import idea.verlif.socket.command.config.ConfigService;
import idea.verlif.socket.core.server.Server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Verlif
 */
public class CommandServer extends Server {

    protected final CommandConfig config;
    protected final CommandParser parser;
    protected final ConfigService configService;

    public CommandServer(CommandConfig config) {
        super(config);
        this.config = config;

        parser = new CommandParser();
        config.handler(parser);

        this.configService = new ConfigService();
    }

    @Override
    public void init() throws IOException {
        super.init();
        configService.init();
        try {
            reload();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取jar文件中的指令。
     *
     * @param path jar文件路径或是文件夹路径
     */
    public void loadCommand(String path) throws InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        JarLoader loader = new JarLoader(path, config.getFileFilter());
        List<SocketCommand> commands = new ArrayList<>();
        List<ConfigAdapter> adapters = new ArrayList<>();
        try {
            commands.addAll(loader.getInstances(SocketCommand.class));
            adapters.addAll(loader.getInstances(ConfigAdapter.class));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        COMMAND:
        for (SocketCommand socketCommand : commands) {
            Class<ConfigAdapter> configCla = (Class<ConfigAdapter>) getAdapterClass(socketCommand);
            if (configCla != null) {
                for (ConfigAdapter adapter : adapters) {
                    Class<?> adapterCla = adapter.getClass();
                    try {
                        adapterCla = configCla.getClassLoader().loadClass(adapterCla.getName());
                        if (configCla.isAssignableFrom(adapterCla)) {
                            ConfigAdapter realAdapter = configService.getConfig((ConfigAdapter) configCla.getClassLoader().loadClass(adapter.getClass().getName()).newInstance());
                            addCommand(socketCommand, realAdapter);
                            continue COMMAND;
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            addCommand(socketCommand);
        }
    }

    /**
     * 获取指令的对应配置类
     *
     * @param command 指令
     */
    public static Class<?> getAdapterClass(SocketCommand<?> command) {
        Type[] types = command.getClass().getGenericInterfaces();
        if (types[0] instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) types[0];
            Type type = parameterizedType.getActualTypeArguments()[0];
            return checkType(type, 0);
        } else {
            return null;
        }
    }

    private static Class<?> checkType(Type type, int index) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type t = pt.getActualTypeArguments()[index];
            return checkType(t, index);
        } else {
            return null;
        }
    }

    /**
     * 添加指令
     *
     * @param command 指令对象
     */
    public void addCommand(SocketCommand<?> command) {
        addCommand(command, null);
    }

    /**
     * 添加带配置的指令
     *
     * @param command 指令对象
     * @param adapter 指令配置
     * @param <T>     指令配置类
     */
    public <T extends ConfigAdapter> void addCommand(SocketCommand<T> command, T adapter) {
        // 销毁会被替换的指令
        Set<SocketCommand<?>> scs = new HashSet<>();
        for (String key : command.keys()) {
            SocketCommand<?> sc = parser.getCommand(key);
            if (sc != null) {
                scs.add(sc);
            }
        }
        command.onLoad(adapter);
        parser.addCommand(command);
        for (SocketCommand<?> sc : scs) {
            // 当解析器中不存在可能被替换的指令时，摧毁指令
            if (!parser.contain(sc)) {
                sc.onDestroy();
            }
        }
    }

    /**
     * 移除指令
     *
     * @param key 指令Key
     */
    public void removeCommand(String key) {
        SocketCommand<?> command = parser.getCommand(key);
        if (command != null) {
            command.onDestroy();
        }
        parser.removeCommand(key);
    }

    /**
     * 移除指令
     *
     * @param command 需要移除的指令
     */
    public void removeCommand(SocketCommand command) {
        for (String key : command.keys()) {
            SocketCommand<?> sc = parser.getCommand(key);
            if (sc == command) {
                sc.onDestroy();
                break;
            }
        }
        parser.dropCommand(command);
    }

    /**
     * 通过paths参数来重载所有路径中的jar文件中的指令
     */
    public void reload() throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (String path : config.getPaths()) {
            loadCommand(path);
        }
    }

    /**
     * 获取指令解析器
     */
    public CommandParser getParser() {
        return parser;
    }

    /**
     * 清空指令
     */
    public void clearCommand() {
        config.getPaths().clear();
        Set<SocketCommand<?>> scs = new HashSet<>();
        for (String key : parser.keys()) {
            SocketCommand<?> command = parser.getCommand(key);
            if (command != null) {
                scs.add(command);
            }
        }
        parser.clear();
        for (SocketCommand<?> command : scs) {
            command.onDestroy();
        }
    }
}
