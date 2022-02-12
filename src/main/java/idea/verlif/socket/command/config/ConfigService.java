package idea.verlif.socket.command.config;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Verlif
 */
public class ConfigService {

    private final JsonFactory factory;

    private File configDir;

    public ConfigService() {
        factory = new MappingJsonFactory();
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
            try (JsonParser parser = factory.createParser(configFile)) {
                return (T) parser.readValueAs(adapter.getClass());
            }
        } else {
            JsonGenerator generator = factory.createGenerator(new FileOutputStream(configFile));
            generator.writePOJO(adapter);
            generator.close();
            return adapter;
        }
    }

    private String buildConfigFilename(ConfigAdapter adapter) {
        return adapter.key() + ".json";
    }
}
