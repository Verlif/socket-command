package idea.verlif.socket.command.key;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/2/16 16:19
 */
public class KeyHandler {

    /**
     * 内置指令Map表
     */
    private final Map<String, KeyCommand> innerCommandMap;

    public KeyHandler() {
        innerCommandMap = new HashMap<>();
    }

    public void addCommand(KeyCommand command) {
        innerCommandMap.put(command.buildKey(), command);
    }

    public KeyCommand getCommand(String key) {
        return innerCommandMap.get(key);
    }

    public void clearKeyCommand() {
        innerCommandMap.clear();
    }
}
