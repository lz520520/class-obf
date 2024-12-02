package me.n1ar4.clazz.obfuscator;

import me.n1ar4.clazz.obfuscator.utils.ColorUtil;

public class Logo {
    public static void printLogo() {
        System.out.println(ColorUtil.green(
                "_________ .__                       ________ ___.    _____ \n" +
                "\\_   ___ \\|  | _____    ______ _____\\_____  \\\\_ |___/ ____\\\n" +
                "/    \\  \\/|  | \\__  \\  /  ___//  ___//   |   \\| __ \\   __\\ \n" +
                "\\     \\___|  |__/ __ \\_\\___ \\ \\___ \\/    |    \\ \\_\\ \\  |   \n" +
                " \\______  /____(____  /____  >____  >_______  /___  /__|   \n" +
                "        \\/          \\/     \\/     \\/        \\/    \\/       "));
        System.out.println(ColorUtil.blue(
                "Class Obfuscator (class-obf) - An Open-Source Class Obfuscation Tool"));
        System.out.println(ColorUtil.yellow(
                "Class Obfuscator (class-obf) - 一个开源的配置简单容易上手的 Class 混淆工具"));
        System.out.println("version: " + ColorUtil.red(Const.VERSION) +
                " url: " + ColorUtil.red(Const.PROJECT_URL));
    }
}
