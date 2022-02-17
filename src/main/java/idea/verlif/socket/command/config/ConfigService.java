package idea.verlif.socket.command.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Verlif
 */
public class ConfigService {

    private final ObjectMapper objectMapper;

    private File configDir;

    public ConfigService() {
        objectMapper = new ObjectMapper();
    }

    /**
     * 初始化配置文件夹
     *
     * @throws IOException 无法创建配置文件夹时抛出异常
     */
    public void init() throws IOException {
        configDir = new File("config");
        if (!configDir.exists()) {
            if (!configDir.mkdir()) {
                throw new FileNotFoundException("Can not create config directory - " + configDir.getAbsolutePath());
            }
        }
    }

    /**
     * 获取指令配置
     *
     * @param adapter 默认的指令配置适配器
     * @return 当前的指令配置
     */
    public <T extends ConfigAdapter> T getConfig(T adapter) throws IOException {
        if (configDir == null) {
            throw new FileNotFoundException("Not init the config directory, please use the init() first!");
        }
        File configFile = new File(configDir, buildConfigFilename(adapter));
        if (configFile.exists()) {
            try (JsonParser parser = objectMapper.createParser(configFile)) {
                return (T) parser.readValueAs(adapter.getClass());
            }
        } else {
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            writer.writeValues(configFile).write(adapter).flush();
            return adapter;
        }
    }

    private String buildConfigFilename(ConfigAdapter adapter) {
        return adapter.key() + ".json";
    }
}
