package idea.verlif.socket.command.config;

/**
 * 指令配置适配器
 *
 * @author Verlif
 */
public interface ConfigAdapter {

    /**
     * 配置唯一Key
     *
     * @return 用于生成配置文件名
     */
    String key();
}
