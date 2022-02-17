package idea.verlif.socket.command.server;

import idea.verlif.socket.core.server.holder.ClientHolder;
import idea.verlif.socket.core.server.listener.ConnectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/2/17 11:28
 */
public class ConnectedListenerChainer implements ConnectedListener {

    protected final List<ConnectedListener> connectedChains;

    public ConnectedListenerChainer() {
        this.connectedChains = new ArrayList<>();
    }

    @Override
    public void onClientConnected(ClientHolder.ClientHandler handler) {
        for (ConnectedListener listener : connectedChains) {
            listener.onClientConnected(handler);
        }
    }

    /**
     * 添加连接处理器
     *
     * @param connectedChain 客户端连接时回调接口
     */
    public void addOnConnectedHandler(ConnectedListener connectedChain) {
        connectedChains.add(connectedChain);
    }

    /**
     * 清空连接处理器链
     */
    public void clearConnectedChain() {
        connectedChains.clear();
    }

}
