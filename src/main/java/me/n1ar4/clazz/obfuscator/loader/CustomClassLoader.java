package me.n1ar4.clazz.obfuscator.loader;

import me.n1ar4.clazz.obfuscator.Const;

import java.nio.file.Files;

public class CustomClassLoader extends ClassLoader {
    @Override
    public Class<?> findClass(String name) {
        byte[] b = loadClassData();
        if (b != null) {
            return defineClass(name, b, 0, b.length);
        }
        return null;
    }

    private byte[] loadClassData() {
        try {
            return Files.readAllBytes(Const.TEMP_PATH);
        } catch (Exception ignored) {
            return null;
        }
    }
}
