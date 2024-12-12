package me.n1ar4.clazz.obfuscator.config;

import me.n1ar4.clazz.obfuscator.asm.JunkCodeChanger;
import me.n1ar4.clazz.obfuscator.core.ObfEnv;
import me.n1ar4.clazz.obfuscator.utils.NameUtil;
import me.n1ar4.jrandom.core.JRandom;
import me.n1ar4.log.LogLevel;
import me.n1ar4.log.LogManager;
import me.n1ar4.log.Logger;

public class Manager {
    private static final Logger logger = LogManager.getLogger();

    public static boolean initConfig(BaseConfig config) {
        JRandom random = new JRandom();
        JRandom.setInstance(random);

        // LOG LEVEL
        String logLevel = config.getLogLevel();
        switch (logLevel) {
            case "debug":
                LogManager.setLevel(LogLevel.DEBUG);
                break;
            case "info":
                LogManager.setLevel(LogLevel.INFO);
                break;
            case "warn":
                LogManager.setLevel(LogLevel.WARN);
                break;
            case "error":
                LogManager.setLevel(LogLevel.ERROR);
                break;
            default:
                logger.error("error log level");
                return false;
        }

        // CHARS
        if (config.getObfuscateChars() == null ||
                config.getObfuscateChars().length < 3) {
            logger.warn("obfuscate chars length too small");
            NameUtil.CHAR_POOL = new String[]{"i", "l", "L", "1", "I"};
        } else {
            String[] data = new String[config.getObfuscateChars().length];
            for (int i = 0; i < config.getObfuscateChars().length; i++) {
                String s = config.getObfuscateChars()[i];
                if (s == null || s.isEmpty()) {
                    logger.error("null in obfuscate chars");
                    return false;
                }
                data[i] = s;
            }
            NameUtil.CHAR_POOL = data;
        }

        JunkCodeChanger.MAX_JUNK_NUM = config.getMaxJunkOneClass();

        // 修复 BUG 2024/12/13
        // field / method 都不应该包含这个字符串
        NameUtil.exclude(config.getAdvanceStringName());
        NameUtil.exclude(config.getAesDecName());
        ObfEnv.ADVANCE_STRING_NAME = config.getAdvanceStringName();

        return true;
    }
}
