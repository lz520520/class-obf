package me.n1ar4.clazz.obfuscator.utils;

public class ColorUtil {
    private static final String ANSI_RESET = "\033[0m";

    private static final String ANSI_RED = "\033[31m";
    private static final String ANSI_GREEN = "\033[32m";
    private static final String ANSI_YELLOW = "\033[33m";
    private static final String ANSI_BLUE = "\033[34m";
    private static final String ANSI_PURPLE = "\033[35m";
    private static final String ANSI_CYAN = "\033[36m";
    private static final String ANSI_WHITE = "\033[37m";

    public static String red(String input) {
        return ANSI_RED + input + ANSI_RESET;
    }

    public static String green(String input) {
        return ANSI_GREEN + input + ANSI_RESET;
    }

    public static String blue(String input) {
        return ANSI_BLUE + input + ANSI_RESET;
    }

    public static String yellow(String input) {
        return ANSI_YELLOW + input + ANSI_RESET;
    }

    public static String purple(String input) {
        return ANSI_PURPLE + input + ANSI_RESET;
    }

    public static String cyan(String input) {
        return ANSI_CYAN + input + ANSI_RESET;
    }

    public static String white(String input) {
        return ANSI_WHITE + input + ANSI_RESET;
    }
}
