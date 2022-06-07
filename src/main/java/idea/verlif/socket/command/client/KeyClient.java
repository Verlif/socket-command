package idea.verlif.socket.command.client;

import idea.verlif.socket.command.key.KeyCommand;
import idea.verlif.socket.core.client.Client;
import idea.verlif.socket.core.client.ClientConfig;
import idea.verlif.socket.core.client.ReceiveHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * 允许内置指令的客户端
 *
 * @author Verlif
 * @version 1.0
 * @date 2022/2/16 15:25
 */
public class KeyClient extends Client {

    private final Set<KeyCommand> commands;

    public KeyClient(ClientConfig config) {
        super(config);

        commands = new HashSet<>();
    }

    /**
     * 添加内置指令
     *
     * @param command 内置指令对象
     */
    public void addInnerCommand(KeyCommand command) {
        commands.add(command);
    }

    @Override
    public boolean connect() {
        ReceiveHandler rh = this.receiveHandler;
        ClientKeyHandler innerHandler = new ClientKeyHandler() {

            @Override
            public void defaultReceive(Client client, String message) {
                rh.receive(client, message);
            }
        };
        for (KeyCommand command : commands) {
            innerHandler.addCommand(command);
        }
        this.receiveHandler = innerHandler;
        return super.connect();
    }
}
