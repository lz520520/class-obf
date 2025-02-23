package me.n1ar4.test;

import me.n1ar4.clazz.obfuscator.utils.LoadUtil;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Test {
    private String a = "cal";
    private String b = "c.exe";
    private int c = 1;

    static void eval() throws Exception {
        Test test = new Test();
        Runtime rt = Runtime.getRuntime();
        rt.exec(test.a + test.b);
        System.out.println(test.c);
    }
    public void eval2() throws Exception  {
        Test test = new Test();
        Runtime rt = Runtime.getRuntime();
        rt.exec(test.a + test.b);
        System.out.println(test.c);
    }

    public static void main(String[] args) throws Exception {
        LoadUtil.loadClassPath("E:\\code\\java\\github\\class-obf\\tests\\wrap_payload.class");

        Class.forName("wrap_payload");
        eval();
    }
}
