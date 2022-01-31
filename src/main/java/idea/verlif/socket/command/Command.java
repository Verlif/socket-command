package idea.verlif.socket.command;

import idea.verlif.socket.core.server.holder.ClientHolder;

/**
 * @author Verlif
 */
public interface Command {

    /**
     * 指令Key
     *
     * @return 指令注册的Key列表
     */
    String[] keys();

    /**
     * 当接收到数据时回调
     *
     * @param client 客户端套接字
     * @param param  指令参数
     */
    void run(ClientHolder.ClientHandler client, String param);
}
