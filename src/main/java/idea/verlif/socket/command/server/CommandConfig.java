package idea.verlif.socket.command.server;

import idea.verlif.loader.jar.JarLoader;
import idea.verlif.socket.command.CommandParser;
import idea.verlif.socket.command.SocketCommand;
import idea.verlif.socket.core.server.ServerConfig;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Verlif
 */
public class CommandConfig extends ServerConfig {

    /**
     * 指令加载路径
     */
    private final Set<String> paths;

    public CommandConfig() {
        paths = new HashSet<>();
    }

    public Set<String> getPaths() {
        return paths;
    }

    /**
     * 添加jar文件路径
     *
     * @param path jar文件路径或其文件夹路径
     */
    public void addPath(String... path) {
        this.paths.addAll(Arrays.asList(path));
    }

    public CommandConfig path(String... path) {
        addPath(path);
        return this;
    }
}
