package me.n1ar4.clazz.obfuscator.utils;

import org.objectweb.asm.ClassReader;

public class ASMUtil {
    public static String getClassName(ClassReader classReader) {
        String className = classReader.getClassName();
        int lastSlashIndex = className.lastIndexOf('/');
        if (lastSlashIndex != -1) {
            return className.substring(lastSlashIndex+1)
                    .replace('/', '.');
        }
        return "";
    }

    public static String getPackageName(ClassReader classReader) {
        String className = classReader.getClassName();
        int lastSlashIndex = className.lastIndexOf('/');
        if (lastSlashIndex != -1) {
            return className.substring(0, lastSlashIndex).replace('/', '.');
        }
        return "";
    }
}
