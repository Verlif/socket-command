package idea.verlif.socket.command.server;

import idea.verlif.loader.jar.JarLoader;
import idea.verlif.socket.command.CommandParser;
import idea.verlif.socket.command.SocketCommand;
import idea.verlif.socket.core.server.Server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Verlif
 */
public class CommandServer extends Server {

    private final CommandConfig config;
    private final CommandParser parser;

    public CommandServer(CommandConfig config) {
        super(config);
        this.config = config;

        parser = new CommandParser();
        config.handler(parser);
    }

    @Override
    public void init() throws IOException {
        super.init();
        try {
            reload();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取jar文件中的指令
     *
     * @param path jar文件路径或是文件夹路径
     */
    public void loadCommand(String path) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        JarLoader loader = new JarLoader(path);
        List<SocketCommand> commands = loader.getInstances(SocketCommand.class);
        for (SocketCommand socketCommand : commands) {
            parser.addCommand(socketCommand);
        }
    }

    /**
     * 添加指令
     *
     * @param command 指令对象
     */
    public void addCommand(SocketCommand command) {
        parser.addCommand(command);
    }

    /**
     * 移除指令
     *
     * @param key 指令Key
     */
    public void removeCommand(String key) {
        parser.removeCommand(key);
    }

    /**
     * 移除指令
     *
     * @param command 需要移除的指令
     */
    public void removeCommand(SocketCommand command) {
        parser.dropCommand(command);
    }

    /**
     * 通过paths参数来重载所有路径中的jar文件中的指令
     */
    public void reload() throws InvocationTargetException, InstantiationException, IllegalAccessException {
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
        parser.clear();
    }
}
