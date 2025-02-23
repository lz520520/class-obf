package me.n1ar4.test;

import me.n1ar4.clazz.obfuscator.api.ClassObf;

public class TestQuick {
    public static void main(String[] args) {
        String data = ClassObf.quickRun("target/test-classes/me/n1ar4/test/TestAPI.class");
        System.out.println(data);
    }
}
