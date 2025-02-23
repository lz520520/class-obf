/**
 * @author lz520520
 * @date 2025/2/22 22:58
 */

package me.n1ar4.clazz.obfuscator.utils;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadUtil {
    public static void loadClassPath(String classFilePath) throws Exception {
        if (!classFilePath.startsWith("file:/")) {
            classFilePath = "file:/" + classFilePath.replaceFirst("^/", "").replaceAll("\\\\", "/");
            classFilePath = classFilePath.replaceFirst("/$", "") + "/";
        }
        // 指定外部 class 或 JAR 的路径
        URL url = new URL(classFilePath); // 目录
        // 获取当前系统类加载器
        URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        // 通过反射获取 URLClassLoader 的 addURL 方法
        Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        addURL.setAccessible(true);  // 允许访问私有方法
        // 动态添加 URL 到 System ClassLoader
        addURL.invoke(systemClassLoader, url);

    }
}
