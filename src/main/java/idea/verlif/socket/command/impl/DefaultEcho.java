package idea.verlif.socket.command.impl;

import idea.verlif.socket.core.server.SocketHandler;
import idea.verlif.socket.core.server.holder.ClientHolder;

/**
 * 默认的指令回馈
 *
 * @author Verlif
 */
public class DefaultEcho implements SocketHandler {

    @Override
    public void receive(ClientHolder.ClientHandler clientHandler, String s) {
        clientHandler.sendMessage(s);
    }
}
