package idea.verlif.socket.command;

import idea.verlif.socket.command.impl.DefaultEcho;
import idea.verlif.socket.core.server.SocketHandler;
import idea.verlif.socket.core.server.holder.ClientHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Verlif
 */
public class CommandParser implements SocketHandler {

    private static final String SPLIT = " ";

    private SocketHandler defaultHandler;
    private final Map<String, SocketCommand> commandMap;

    public CommandParser() {
        commandMap = new HashMap<>();
        defaultHandler = new DefaultEcho();
    }

    @Override
    public void receive(ClientHolder.ClientHandler client, String message) {
        String[] ss = message.split(SPLIT, 2);
        SocketCommand command = commandMap.get(ss[0]);
        if (command != null) {
            if (ss.length == 1) {
                command.run(client, null);
            } else {
                command.run(client, ss[1]);
            }
        } else if (defaultHandler != null) {
            defaultHandler.receive(client, message);
        }
    }

    /**
     * 设定默认指令反馈。<br/>
     * 当指令库不存在客户端指令时，则会调用此handler。
     *
     * @param defaultHandler 指令反馈回调
     */
    public void setDefaultHandler(SocketHandler defaultHandler) {
        if (defaultHandler != null) {
            this.defaultHandler = defaultHandler;
        }
    }

    /**
     * 添加指令
     *
     * @param command 指令对象
     */
    public void addCommand(SocketCommand command) {
        for (String key : command.keys()) {
            commandMap.put(key, command);
        }
    }

    public void removeCommand(String key) {
        commandMap.remove(key);
    }

    public void dropCommand(SocketCommand command) {
        for (String key : command.keys()) {
            commandMap.remove(key);
        }
    }

    /**
     * 清空指令
     */
    public void clear() {
        commandMap.clear();
    }
}
