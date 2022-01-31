package idea.verlif.socket.command;

import idea.verlif.socket.core.server.SocketHandler;
import idea.verlif.socket.core.server.holder.ClientHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Verlif
 */
public class CommandParser implements SocketHandler {

    private static final String SPLIT = " ";

    private final Map<String, Command> commandMap;

    public CommandParser() {
        commandMap = new HashMap<>();
    }

    @Override
    public void receive(ClientHolder.ClientHandler client, String message) {
        String[] ss = message.split(SPLIT, 2);
        Command command = commandMap.get(ss[0]);
        if (command != null) {
            if (ss.length == 1) {
                command.run(client, null);
            } else {
                command.run(client, ss[1]);
            }
        }
    }

    /**
     * 添加指令
     *
     * @param command 指令对象
     */
    public void addCommand(Command command) {
        for (String key : command.keys()) {
            commandMap.put(key, command);
        }
    }
}
