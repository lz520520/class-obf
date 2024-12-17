package me.n1ar4.test;

import me.n1ar4.clazz.obfuscator.api.ClassObf;
import me.n1ar4.clazz.obfuscator.api.Result;
import me.n1ar4.clazz.obfuscator.config.BaseConfig;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class TestAPIByte {
    public static void main(String[] args) throws Exception {
        BaseConfig config = BaseConfig.Default();
        ClassObf classObf = new ClassObf(config);
        Result result = classObf.run(Files.readAllBytes(Paths.get("target/test-classes/me/n1ar4/test/TestAPI.class")));
        if (result.getMessage().equals(Result.SUCCESS)) {
            System.out.println(Base64.getEncoder().encodeToString(result.getData()));
        }
    }
}
