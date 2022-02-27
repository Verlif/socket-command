package idea.verlif.socket.command.client;

import idea.verlif.socket.command.SocketCommand;
import idea.verlif.socket.command.key.KeyCommand;
import idea.verlif.socket.command.key.KeyHandler;
import idea.verlif.socket.core.client.Client;
import idea.verlif.socket.core.client.ReceiveHandler;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/2/16 11:51
 */
public abstract class ClientKeyHandler extends KeyHandler implements ReceiveHandler {

    @Override
    public void receive(Client client, String s) {
        String[] split = s.split(SocketCommand.SPLIT, 2);
        KeyCommand command = getCommand(split[0]);
        if (command == null) {
            defaultReceive(client, s);
        } else {
            command.receiveOnClient(client, split.length == 2 ? split[1] : "");
        }
    }

    /**
     * 非内置指令信息
     *
     * @param client  客户端对象
     * @param message 接收的数据
     */
    public abstract void defaultReceive(Client client, String message);
}
