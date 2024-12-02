package me.n1ar4.clazz.obfuscator.config;

import me.n1ar4.clazz.obfuscator.Const;
import me.n1ar4.clazz.obfuscator.utils.IOUtils;
import me.n1ar4.log.LogManager;
import me.n1ar4.log.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TagInspector;
import org.yaml.snakeyaml.representer.Representer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Parser {
    private static final Logger logger = LogManager.getLogger();
    private String TEMPLATE;
    private static final LoaderOptions lOptions = new LoaderOptions();
    private static final DumperOptions dOptions = new DumperOptions();
    private static final Yaml yaml;

    static {
        TagInspector taginspector =
                tag -> tag.getClassName().equals(BaseConfig.class.getName());
        lOptions.setTagInspector(taginspector);
        dOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dOptions.setPrettyFlow(true);
        yaml = new Yaml(
                new Constructor(BaseConfig.class, lOptions),
                new Representer(dOptions));
    }

    public Parser() {
        InputStream is = Parser.class.getClassLoader().getResourceAsStream("config.yaml");
        if (is == null) {
            return;
        }
        try {
            byte[] data = IOUtils.readAllBytes(is);
            TEMPLATE = new String(data, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            logger.error("read template error: {}", ex);
        }
    }

    public void generateConfig() {
        try {
            Files.write(Const.configPath, TEMPLATE.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            logger.error("write config file error: {}", ex.toString());
        }
    }

    public static void main(String[] args) {
        new Parser().generateConfigSTD();
    }

    @SuppressWarnings("all")
    public void generateConfigSTD() {
        BaseConfig config = new BaseConfig();
        config.setEnableJunk(true);
        config.setEnableAdvanceString(true);
        config.setEnableFieldName(true);
        config.setEnableXOR(true);
        config.setEnableDeleteCompileInfo(true);
        config.setEnableParamName(true);
        config.setEnableMethodName(true);
        config.setEnableHideMethod(false);
        config.setEnableHideField(false);
        config.setJunkLevel(3);
        config.setMaxJunkOneClass(1000);

        config.setLogLevel("info");
        config.setObfuscateChars(new String[]{"i", "l", "L", "1", "I"});
        config.setAdvanceStringName("GLOBAL_LLLiii");
        config.setMethodBlackList(new String[]{"test"});

        String data = yaml.dump(config);
        try {
            Files.write(Const.configPath, data.getBytes());
        } catch (Exception ex) {
            logger.error("write config file error: {}", ex.toString());
        }
    }

    public BaseConfig parse(Path file) {
        if (!Files.exists(file)) {
            logger.error("config file not exist");
            return null;
        }
        try {
            InputStream is = new ByteArrayInputStream(Files.readAllBytes(file));
            return yaml.load(is);
        } catch (Exception ex) {
            logger.error("parse config error: {}", ex.toString());
        }
        return null;
    }
}
