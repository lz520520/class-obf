package me.n1ar4.clazz.obfuscator.core;

import me.n1ar4.clazz.obfuscator.Const;
import me.n1ar4.clazz.obfuscator.analyze.MethodCallClassVisitor;
import me.n1ar4.clazz.obfuscator.base.ClassFileEntity;
import me.n1ar4.clazz.obfuscator.base.MethodReference;
import me.n1ar4.log.LogManager;
import me.n1ar4.log.Logger;
import org.objectweb.asm.ClassReader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MethodCallRunner {
    private static final Logger logger = LogManager.getLogger();

    public static void start(Set<ClassFileEntity> classFileList, HashMap<MethodReference.Handle,
            HashSet<MethodReference.Handle>> methodCalls) {
        logger.info("start analyze method calls");
        for (ClassFileEntity file : classFileList) {
            try {
                MethodCallClassVisitor mcv =
                        new MethodCallClassVisitor(methodCalls);
                ClassReader cr = new ClassReader(file.getFile());
                cr.accept(mcv, Const.ReaderASMOptions);
            } catch (Exception e) {
                logger.error("method call error: {}", e.toString());
            }
        }
    }
}
