package me.n1ar4.clazz.obfuscator.asm;

import me.n1ar4.clazz.obfuscator.Const;
import me.n1ar4.clazz.obfuscator.base.ClassField;
import me.n1ar4.clazz.obfuscator.core.ObfEnv;
import org.objectweb.asm.*;

public class FieldNameClassVisitor extends ClassVisitor {
    private String className;

    public FieldNameClassVisitor(ClassVisitor classVisitor) {
        super(Const.ASMVersion, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        return new FieldNameChangerMethodAdapter(mv);
    }


    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        ClassField cf = new ClassField();
        cf.setClassName(this.className);
        cf.setFieldName(name);
        ClassField newCF = ObfEnv.fieldNameObfMapping.getOrDefault(cf, cf);

        FieldVisitor vf =  super.visitField(access, newCF.getFieldName(), descriptor, signature, value);
        if (ObfEnv.config.isEnableHideField()) {
            vf.visitAttribute(new SyntheticAttribute());
        }
        return vf;
    }


    static class FieldNameChangerMethodAdapter extends MethodVisitor {
        FieldNameChangerMethodAdapter(MethodVisitor mv) {
            super(Const.ASMVersion, mv);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
            ClassField cf = new ClassField();
            cf.setClassName(owner);
            cf.setFieldName(name);
            ClassField newCF = ObfEnv.fieldNameObfMapping.getOrDefault(cf, cf);
            super.visitFieldInsn(opcode, owner, newCF.getFieldName(), descriptor);
        }
    }
}