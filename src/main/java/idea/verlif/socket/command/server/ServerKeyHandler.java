package idea.verlif.socket.command.server;

import idea.verlif.socket.command.SocketCommand;
import idea.verlif.socket.command.key.KeyCommand;
import idea.verlif.socket.command.key.KeyHandler;
import idea.verlif.socket.core.server.SocketHandler;
import idea.verlif.socket.core.server.holder.ClientHolder;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/2/16 11:51
 */
public abstract class ServerKeyHandler extends KeyHandler implements SocketHandler {

    @Override
    public void receive(ClientHolder.ClientHandler client, String s) {
        String[] split = s.split(SocketCommand.SPLIT, 2);
        KeyCommand command = getCommand(split[0]);
        if (command == null) {
            defaultReceive(client, s);
        } else {
            command.receiveOnServer(client, split.length == 2 ? split[1] : "");
        }
    }

    /**
     * 非内置指令信息
     *
     * @param client  客户端处理器
     * @param message 接收的数据
     */
    public abstract void defaultReceive(ClientHolder.ClientHandler client, String message);
}
