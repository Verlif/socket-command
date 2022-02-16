package idea.verlif.socket.command.server.inter;

import idea.verlif.socket.core.server.holder.ClientHolder;

/**
 * 客户端连接触发链
 *
 * @author Verlif
 */
public interface ConnectedChain {

    /**
     * 客户端连接时回调
     *
     * @param handler 客户端处理器
     */
    void onClientConnected(ClientHolder.ClientHandler handler);
}
