package me.n1ar4.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestRunner extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException(name);
        }
        return defineClass(name, classData, 0, classData.length);
    }

    private byte[] getClassData(String className) {
        if ("me.n1ar4.test.Test".equals(className)) {
            try {
                return Files.readAllBytes(Paths.get("Test_obf.class"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        TestRunner loader = new TestRunner();
        Class<?> clazz = loader.loadClass("me.n1ar4.test.Test");
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getMethod("main", String[].class);
        method.invoke(instance, new Object[]{args});
    }
}
