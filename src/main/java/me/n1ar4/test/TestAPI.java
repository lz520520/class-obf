package me.n1ar4.test;

import me.n1ar4.clazz.obfuscator.api.ClassObf;
import me.n1ar4.clazz.obfuscator.api.Result;
import me.n1ar4.clazz.obfuscator.config.BaseConfig;

import java.util.Base64;

public class TestAPI {
    public static void main(String[] args) {
        BaseConfig config = BaseConfig.Default();
        ClassObf classObf = new ClassObf(config);
        Result result = classObf.run("target/test-classes/me/n1ar4/test/TestAPI.class");
        if (result.getMessage().equals(Result.SUCCESS)) {
            System.out.println(Base64.getEncoder().encodeToString(result.getData()));
        }
    }
}
