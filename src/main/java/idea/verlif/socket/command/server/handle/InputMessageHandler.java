package idea.verlif.socket.command.server.handle;

import idea.verlif.socket.core.server.holder.ClientHolder;

/**
 * 客户端信息重处理接口
 *
 * @author Verlif
 */
public interface InputMessageHandler {

    /**
     * 客户端消息预处理
     *
     * @param handler 客户端处理器
     * @param message 客户端消息
     * @return 处理后的消息；为null则不继续处理
     */
    String preHandle(ClientHolder.ClientHandler handler, String message);

    /**
     * 客户端消息处理后回调
     *
     * @param handler 客户端处理器
     * @param message 客户端原消息
     * @param handle  客户端处理后的消息
     */
    default void afterHandle(ClientHolder.ClientHandler handler, String message, String handle) {}
}
