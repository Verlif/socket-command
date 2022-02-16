package idea.verlif.socket.command.server;

import idea.verlif.socket.command.key.KeyCommand;
import idea.verlif.socket.core.server.SocketHandler;
import idea.verlif.socket.core.server.holder.ClientHolder;

import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/2/16 15:33
 */
public class KeyServer extends CommandServer {

    private final Set<KeyCommand> commands;

    public KeyServer(CommandConfig config) {
        super(config);

        commands = new HashSet<>();
    }

    /**
     * 添加内置指令
     *
     * @param command 内置指令对象
     */
    public void addKeyCommand(KeyCommand command) {
        commands.add(command);
    }

    @Override
    public void init() throws IOException {
        SocketHandler socketHandler = config.getHandler();
        ServerKeyHandler keyHandler = new ServerKeyHandler() {

            @Override
            public void onClientConnected(ClientHolder.ClientHandler handler) {
                socketHandler.onClientConnected(handler);
            }

            @Override
            public void defaultReceive(ClientHolder.ClientHandler client, String message) {
                socketHandler.receive(client, message);
            }

            @Override
            public void onRejected(Socket socket) throws IOException {
                socketHandler.onRejected(socket);
            }
        };
        for (KeyCommand command : commands) {
            keyHandler.addCommand(command);
        }
        config.setHandler(keyHandler);
        super.init();
    }
}
