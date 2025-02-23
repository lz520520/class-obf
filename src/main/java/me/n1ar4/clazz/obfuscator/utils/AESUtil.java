package me.n1ar4.clazz.obfuscator.utils;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

public class AESUtil {
    public static void addBase64DecodeCode(ClassVisitor cv, String methodName) {
        MethodVisitor mv = cv.visitMethod(
                ACC_PUBLIC | ACC_STATIC,
                methodName,
                "([B)[B",  // 方法签名：byte[] base64Decode(byte[] in)
                null,
                new String[]{"java/lang/Exception"}
        );

        mv.visitCode();

        // 获取 java.version 属性
        mv.visitLdcInsn("java.version");
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "getProperty", "(Ljava/lang/String;)Ljava/lang/String;", false);
        mv.visitLdcInsn("1.9");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "compareTo", "(Ljava/lang/String;)I", false);
        Label elseBranch = new Label();
        mv.visitJumpInsn(IFLT, elseBranch);

        // Java 9+
        mv.visitLdcInsn("java.util.Base64");
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
        mv.visitVarInsn(ASTORE, 2);

        mv.visitVarInsn(ALOAD, 2);
        mv.visitLdcInsn("getDecoder");
        mv.visitInsn(ICONST_0);
        mv.visitTypeInsn(ANEWARRAY, "java/lang/Class");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
        mv.visitVarInsn(ASTORE, 3);

        mv.visitVarInsn(ALOAD, 3);
        mv.visitVarInsn(ALOAD, 2); // 使用 Class Base64 作为第一个参数
        mv.visitInsn(ICONST_0);
        mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Method", "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", false);
        mv.visitVarInsn(ASTORE, 4);

        mv.visitVarInsn(ALOAD, 4);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
        mv.visitLdcInsn("decode");
        mv.visitInsn(ICONST_1);
        mv.visitTypeInsn(ANEWARRAY, "java/lang/Class");
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_0);
        mv.visitLdcInsn(Type.getType("[B"));
        mv.visitInsn(AASTORE);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
        mv.visitVarInsn(ASTORE, 5);

        mv.visitVarInsn(ALOAD, 5);
        mv.visitVarInsn(ALOAD, 4);
        mv.visitInsn(ICONST_1);
        mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitInsn(AASTORE);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Method", "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", false);
        mv.visitTypeInsn(CHECKCAST, "[B");
        mv.visitVarInsn(ASTORE, 1);

        Label returnLabel = new Label();
        mv.visitJumpInsn(GOTO, returnLabel);

        // Java 8-
        mv.visitLabel(elseBranch);
        mv.visitLdcInsn("sun.misc.BASE64Decoder");
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "newInstance", "()Ljava/lang/Object;", false);
        mv.visitVarInsn(ASTORE, 2);

        mv.visitVarInsn(ALOAD, 2);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
        mv.visitLdcInsn("decodeBuffer");
        mv.visitInsn(ICONST_1);
        mv.visitTypeInsn(ANEWARRAY, "java/lang/Class");
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_0);
        mv.visitLdcInsn(Type.getType("Ljava/lang/String;"));
        mv.visitInsn(AASTORE);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
        mv.visitVarInsn(ASTORE, 3);

        mv.visitVarInsn(ALOAD, 3);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitInsn(ICONST_1);
        mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_0);
        mv.visitTypeInsn(NEW, "java/lang/String");
        mv.visitInsn(DUP);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/String", "<init>", "([B)V", false);
        mv.visitInsn(AASTORE);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Method", "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", false);
        mv.visitTypeInsn(CHECKCAST, "[B");
        mv.visitVarInsn(ASTORE, 1);

        // return classbytes
        mv.visitLabel(returnLabel);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(5, 6);
        mv.visitEnd();
    }



    public static void addAesDecodeCode(ClassVisitor cv,String className,  String aesDecName) {
        addBase64DecodeCode(cv, aesDecName + aesDecName);
        MethodVisitor methodVisitor = cv.visitMethod(
                ACC_PUBLIC | ACC_STATIC,
                aesDecName,
                "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;",
                null, null);

        methodVisitor.visitCode();

        Label startTry = new Label();
        Label endTry = new Label();
        Label catchBlock = new Label();

        methodVisitor.visitTryCatchBlock(startTry, endTry, catchBlock, "java/lang/Exception");

        methodVisitor.visitLabel(startTry);

        methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "reverse", "()Ljava/lang/StringBuilder;", false);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        methodVisitor.visitVarInsn(ASTORE, 1);

        methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "reverse", "()Ljava/lang/StringBuilder;", false);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        methodVisitor.visitVarInsn(ASTORE, 0);

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

        // 调用 base64Decode 代替 Base64.getDecoder().decode
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "getBytes", "()[B", false);
        methodVisitor.visitMethodInsn(INVOKESTATIC, className, aesDecName + aesDecName, "([B)[B", false);
        methodVisitor.visitVarInsn(ASTORE, 4);

        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 4);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "javax/crypto/Cipher", "doFinal", "([B)[B", false);
        methodVisitor.visitVarInsn(ASTORE, 5);

        methodVisitor.visitTypeInsn(NEW, "java/lang/String");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/String", "<init>", "([B)V", false);
        methodVisitor.visitInsn(ARETURN);

        methodVisitor.visitLabel(endTry);

        // Catch block
        methodVisitor.visitLabel(catchBlock);
        methodVisitor.visitFrame(F_SAME1, 0, null, 1, new Object[]{"java/lang/Exception"});
        methodVisitor.visitVarInsn(ASTORE, 6);
        methodVisitor.visitLdcInsn("");
        methodVisitor.visitInsn(ARETURN);

        methodVisitor.visitMaxs(4, 7);
        methodVisitor.visitEnd();
    }


}
