package idea.verlif.socket.command.server;

import idea.verlif.socket.core.server.ServerConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Verlif
 */
public class CommandConfig extends ServerConfig {

    /**
     * 指令加载路径
     */
    private final Set<String> paths;

    /**
     * 服务端是否要求数据重编码
     */
    private boolean encode;

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

    public boolean isEncode() {
        return encode;
    }

    public void setEncode(boolean encode) {
        this.encode = encode;
    }

    @Override
    public String toString() {
        return "CommandConfig{" +
                "paths=" + Arrays.toString(paths.toArray()) +
                '}';
    }
}
