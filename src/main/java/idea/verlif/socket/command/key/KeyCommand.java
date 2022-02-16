package idea.verlif.socket.command.key;

import idea.verlif.socket.core.client.Client;
import idea.verlif.socket.core.server.holder.ClientHolder;

/**
 * 内置指令
 *
 * @author Verlif
 * @version 1.0
 * @date 2022/2/16 11:31
 */
public interface KeyCommand {

    /**
     * 内置指令前缀
     */
    String COMMAND_PREFIX = "__";

    /**
     * 内置指令Key。<br/>
     * 用于优先判定的指令Key。不需要带有指令标识符，例如只需要返回"plus"即可。
     *
     * @return 指令Key
     */
    String key();

    /**
     * 获取构建后的内置指令Key。<br/>
     * 此处获取的是用于传输的指令Key，带有指令标识符。
     *
     * @return 内置指令Key
     */
    default String buildKey() {
        return COMMAND_PREFIX + key();
    }

    /**
     * 当服务端接收到指令时回调
     *
     * @param client 客户端处理器
     * @param param  指令参数
     */
    void receiveOnServer(ClientHolder.ClientHandler client, String param);

    /**
     * 当客户端端接收到指令时回调
     *
     * @param client 客户端对象
     * @param param  指令参数
     */
    void receiveOnClient(Client client, String param);
}
