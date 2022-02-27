package idea.verlif.socket.command.server;

import idea.verlif.loader.jar.config.FileFilter;
import idea.verlif.socket.core.server.ServerConfig;
import idea.verlif.socket.core.server.SocketHandler;
import idea.verlif.socket.core.server.listener.ConnectedListener;

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
     * 指令文件过滤器
     */
    private FileFilter fileFilter;

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

    public FileFilter getFileFilter() {
        return fileFilter;
    }

    public void setFileFilter(FileFilter fileFilter) {
        this.fileFilter = fileFilter;
    }

    public CommandConfig path(String... path) {
        addPath(path);
        return this;
    }

    public CommandConfig filter(FileFilter filter) {
        this.fileFilter = filter;
        return this;
    }

    /**
     * 设置连接处理器已被弃用，由内置的指令处理器接管。
     *
     * @param handler 连接监听器
     * @deprecated 此方法已不会再生效
     */
    @Deprecated
    @Override
    public void setHandler(SocketHandler handler) {
        super.setHandler(handler);
    }

    /**
     * 设置连接监听器已被弃用，请使用{@linkplain CommandServer#addOnConnectedHandler(ConnectedListener) CommandServer.addOnConnectedHandler()}
     *
     * @param connectedListener 连接监听器
     * @deprecated 请使用CommandServer下的addOnConnectedHandler方法
     */
    @Deprecated
    @Override
    public void setConnectedListener(ConnectedListener connectedListener) {
        super.setConnectedListener(connectedListener);
    }

    @Override
    public String toString() {
        return "CommandConfig{" +
                "paths=" + Arrays.toString(paths.toArray()) +
                '}';
    }
}
