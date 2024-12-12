package me.n1ar4.clazz.obfuscator.utils;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class AESUtil {
    public static void addAesDecodeCode(ClassVisitor cv, String aesDecName) {
        MethodVisitor methodVisitor = cv.visitMethod(
                ACC_PUBLIC | ACC_STATIC,
                aesDecName,
                "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;",
                null, new String[]{"java/lang/Exception"});
        methodVisitor.visitCode();
        methodVisitor.visitTypeInsn(NEW, "javax/crypto/spec/SecretKeySpec");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "getBytes", "()[B", false);
        methodVisitor.visitLdcInsn("AES");
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "javax/crypto/spec/SecretKeySpec", "<init>", "([BLjava/lang/String;)V", false);
        methodVisitor.visitVarInsn(ASTORE, 2);
        methodVisitor.visitLdcInsn("AES");
        methodVisitor.visitMethodInsn(INVOKESTATIC, "javax/crypto/Cipher", "getInstance", "(Ljava/lang/String;)Ljavax/crypto/Cipher;", false);
        methodVisitor.visitVarInsn(ASTORE, 3);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitInsn(ICONST_2);
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "javax/crypto/Cipher", "init", "(ILjava/security/Key;)V", false);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitMethodInsn(INVOKESTATIC, "java/util/Base64", "getDecoder", "()Ljava/util/Base64$Decoder;", false);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/Base64$Decoder", "decode", "(Ljava/lang/String;)[B", false);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "javax/crypto/Cipher", "doFinal", "([B)[B", false);
        methodVisitor.visitVarInsn(ASTORE, 4);
        methodVisitor.visitTypeInsn(NEW, "java/lang/String");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 4);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/String", "<init>", "([B)V", false);
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitMaxs(4, 5);
        methodVisitor.visitEnd();
    }
}
