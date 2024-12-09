package me.n1ar4.test;

import me.n1ar4.clazz.obfuscator.api.ClassObf;
import me.n1ar4.clazz.obfuscator.api.Result;
import me.n1ar4.clazz.obfuscator.config.BaseConfig;

import java.util.Base64;

public class TestAPI {
    public static void main(String[] args) {
        BaseConfig config = new BaseConfig();
        config.setLogLevel("info");
        // 默认不开隐藏
        config.setEnableHideField(false);
        config.setEnableHideMethod(false);

        // 默认开启所有混淆
        config.setEnableAdvanceString(true);
        config.setEnableFieldName(true);
        config.setEnableXOR(true);
        config.setEnableDeleteCompileInfo(true);
        config.setEnableParamName(true);
        config.setEnableMethodName(true);

        // 默认花指令配置
        config.setEnableJunk(true);
        config.setJunkLevel(3);
        config.setMaxJunkOneClass(1000);

        // 默认额外配置
        config.setObfuscateChars(new String[]{"i", "l", "L", "1", "I"});
        config.setAdvanceStringName("iii");
        config.setMethodBlackList(new String[]{"main"});

        ClassObf classObf = new ClassObf(config);
        Result result = classObf.run("target/test-classes/me/n1ar4/test/TestAPI.class");
        if (result.getMessage().equals(Result.SUCCESS)) {
            System.out.println(Base64.getEncoder().encodeToString(result.getData()));
        }
    }
}
