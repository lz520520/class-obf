package me.n1ar4.clazz.obfuscator.core;

import me.n1ar4.clazz.obfuscator.base.ClassField;
import me.n1ar4.clazz.obfuscator.base.ClassReference;
import me.n1ar4.clazz.obfuscator.base.MethodReference;
import me.n1ar4.clazz.obfuscator.config.BaseConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObfEnv {
    public static BaseConfig config = null;
    public static String ADVANCE_STRING_NAME = null;
    public static Map<MethodReference.Handle, MethodReference.Handle> methodNameObfMapping = new HashMap<>();
    public static Map<ClassField, ClassField> fieldNameObfMapping = new HashMap<>();
    public static final HashMap<ClassReference.Handle, ArrayList<String>> stringInClass = new HashMap<>();
    public static final HashMap<String, ArrayList<String>> newStringInClass = new HashMap<>();
}
