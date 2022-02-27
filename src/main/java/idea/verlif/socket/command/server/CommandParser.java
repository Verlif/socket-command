package idea.verlif.socket.command.server;

import idea.verlif.socket.command.SocketCommand;
import idea.verlif.socket.command.impl.DefaultTip;
import idea.verlif.socket.command.server.handle.InputMessageHandler;
import idea.verlif.socket.core.server.SocketHandler;
import idea.verlif.socket.core.server.holder.ClientHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Verlif
 */
public class CommandParser implements SocketHandler {

    private SocketHandler defaultHandler;
    private final Map<String, SocketCommand<?>> commandMap;

    /**
     * 指令别名Map表
     */
    private final Map<String, String> aliasMap;

    /**
     * 接受消息预处理
     */
    private InputMessageHandler messageHandler;

    public CommandParser() {
        this.commandMap = new HashMap<>();
        this.defaultHandler = new DefaultTip();
        this.aliasMap = new HashMap<>();
    }

    @Override
    public void receive(ClientHolder.ClientHandler client, String message) {
        String content = messageHandler == null ? message : messageHandler.preHandle(client, message);
        if (content == null) {
            return;
        }
        String[] ss = content.split(SocketCommand.SPLIT, 2);
        String key = ss[0];
        String param = ss.length == 1 ? "" : ss[1];
        if (aliasMap.containsKey(ss[0])) {
            String[] sp = aliasMap.get(ss[0]).split(SocketCommand.SPLIT, 2);
            key = sp[0];
            if (sp.length > 1) {
                param = sp[1] + param;
            }
        }
        SocketCommand<?> command = commandMap.get(key);
        if (command != null) {
            command.run(client, param);
        } else if (defaultHandler != null) {
            defaultHandler.receive(client, content.trim());
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
    public void addCommand(SocketCommand<?> command) {
        for (String key : command.keys()) {
            commandMap.put(key, command);
        }
    }

    public SocketCommand<?> getCommand(String key) {
        return commandMap.get(key);
    }

    public boolean contain(SocketCommand<?> command) {
        return commandMap.containsValue(command);
    }

    public void removeCommand(String key) {
        commandMap.remove(key);
    }

    public void dropCommand(SocketCommand<?> command) {
        for (String key : command.keys()) {
            commandMap.remove(key, command);
        }
    }

    public Map<String, String> getAliasMap() {
        return aliasMap;
    }

    public void addAlias(String alias, String command) {
        this.aliasMap.put(alias, command);
    }

    public InputMessageHandler getMessageHandler() {
        return messageHandler;
    }

    public void setMessageHandler(InputMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * 获取所有的指令key
     */
    public Set<String> keys() {
        return commandMap.keySet();
    }

    /**
     * 清空指令
     */
    public void clear() {
        commandMap.clear();
    }
}
