package idea.verlif.socket.command;

import idea.verlif.socket.command.config.ConfigAdapter;
import idea.verlif.socket.core.server.holder.ClientHolder;

/**
 * @author Verlif
 */
public interface SocketCommand<T extends ConfigAdapter> {

    /**
     * 指令Key
     *
     * @return 指令注册的Key列表
     */
    String[] keys();

    /**
     * 加载配置
     *
     * @param t 配置对象
     */
    default void onLoad(T t) {}

    /**
     * 当接收到数据时回调
     *
     * @param client 客户端套接字
     * @param param  指令参数
     */
    void run(ClientHolder.ClientHandler client, String param);

    /**
     * 当指令被销毁时调用
     */
    default void onDestroy() {};
}
