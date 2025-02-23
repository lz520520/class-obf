package me.n1ar4.clazz.obfuscator;

import com.beust.jcommander.JCommander;
import me.n1ar4.clazz.obfuscator.config.BaseCmd;
import me.n1ar4.clazz.obfuscator.config.BaseConfig;
import me.n1ar4.clazz.obfuscator.config.Manager;
import me.n1ar4.clazz.obfuscator.config.Parser;
import me.n1ar4.clazz.obfuscator.core.Runner;
import me.n1ar4.clazz.obfuscator.utils.LoadUtil;
import me.n1ar4.log.LogManager;
import me.n1ar4.log.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final BaseCmd baseCmd = new BaseCmd();
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        Logo.printLogo();
        Parser parser = new Parser();
        JCommander commander = JCommander.newBuilder()
                .addObject(baseCmd)
                .build();
        try {
            commander.parse(args);
        } catch (Exception ignored) {
            commander.usage();
            return;
        }

        if (baseCmd.isVersion()) {
            return;
        }
        if (baseCmd.isGenerate()) {
            parser.generateConfig();
            logger.info("generate config.yaml file");
            return;
        }
        if (baseCmd.getConfig() == null || baseCmd.getConfig().isEmpty()) {
            baseCmd.setConfig("config.yaml");
        }
        if (baseCmd.getPath() == null || baseCmd.getPath().isEmpty()) {
            logger.error("need -i/--input file");
            commander.usage();
            return;
        }
        BaseConfig config = parser.parse(Paths.get(baseCmd.getConfig()));
        if (config == null) {
            logger.warn("need config.yaml config");
            logger.info("generate config.yaml file");
            parser.generateConfig();
            return;
        }
        String p = baseCmd.getPath();
        Path path = Paths.get(p);
        if (!Files.exists(path)) {
            logger.error("class file not exist");
            commander.usage();
            return;
        }

        boolean success = Manager.initConfig(config);
        if (!success) {
            return;
        }

        logger.info("start class obfuscate");
        LoadUtil.loadClassPath(path.getParent().toAbsolutePath().toString());
        Runner.run(path, config, false, baseCmd);
    }
}
