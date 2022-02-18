package idea.verlif.socket.command.server;

import idea.verlif.socket.command.key.KeyCommand;
import idea.verlif.socket.core.server.SocketHandler;
import idea.verlif.socket.core.server.holder.ClientHolder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
    private final ServerKeyHandler keyHandler;

    public KeyServer(CommandConfig config) {
        super(config);

        commands = new HashSet<>();

        SocketHandler socketHandler = config.getHandler();
        keyHandler = new ServerKeyHandler() {
            @Override
            public void defaultReceive(ClientHolder.ClientHandler client, String message) {
                socketHandler.receive(client, message);
            }

            @Override
            public void onRejected(Socket socket) throws IOException {
                socketHandler.onRejected(socket);
            }
        };
        config.setHandler(keyHandler);
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
        for (KeyCommand command : commands) {
            keyHandler.addCommand(command);
        }
        super.init();
    }

    @Override
    public void reload() throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException {
        keyHandler.clearKeyCommand();
        for (KeyCommand command : commands) {
            keyHandler.addCommand(command);
        }
        super.reload();
    }
}
