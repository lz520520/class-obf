package me.n1ar4.clazz.obfuscator.asm;

import me.n1ar4.clazz.obfuscator.Const;
import me.n1ar4.clazz.obfuscator.base.ClassReference;
import me.n1ar4.clazz.obfuscator.base.MethodReference;
import me.n1ar4.clazz.obfuscator.core.AnalyzeEnv;
import me.n1ar4.clazz.obfuscator.core.ObfEnv;
import me.n1ar4.clazz.obfuscator.utils.ReflectHelp;
import org.objectweb.asm.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class MethodNameClassVisitor extends ClassVisitor {
    private String owner;
    private final List<MethodReference> ignoreMethods = new ArrayList<>();

    public MethodNameClassVisitor(ClassVisitor classVisitor) {
        super(Const.ASMVersion, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.owner = name;
        // 查接口 不改接口方法
        for (String in : interfaces) {
            List<MethodReference> mList = AnalyzeEnv.methodsInClassMap.get(new ClassReference.Handle(in));
            if (mList == null) {
                continue;
            }
            ignoreMethods.addAll(mList);
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }
    static void hideMethod(MethodVisitor mv) {
        int currentLocals = 0;
        try {
            Object curr = ReflectHelp.getFieldValue(mv, "currentLocals");
            // currentLocals默认是比当前参数大1，参数没对齐，反编译则会编译失败。
            currentLocals = Integer.parseInt(curr.toString());
            if (currentLocals < 0) {
                currentLocals = 0;
            }
        }catch (Exception e) {
        }
        mv.visitAttribute(new SyntheticAttribute());
        mv.visitAttribute(new MethodParameterAttribute(currentLocals,5));
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        MethodVisitor mv = null;
        for (MethodReference mr : ignoreMethods) {
            if (mr.getName().equals(name) && mr.getDesc().equals(desc)) {
                mv = super.visitMethod(access, name, desc, signature, exceptions);
                if (ObfEnv.config.isEnableHideMethod()) {
                    hideMethod(mv);
                }
                return mv;
            }
        }

        if (name.equals("<init>") || name.equals("<clinit>")) {
            mv = super.visitMethod(access, name, desc, signature, exceptions);
        } else {
            MethodReference.Handle m = ObfEnv.methodNameObfMapping.get(new MethodReference.Handle(
                    new ClassReference.Handle(owner),
                    name,
                    desc
            ));
            if (ObfEnv.config.isEnableHideMethod()) {
                access = access | Opcodes.ACC_SYNTHETIC;
            }
            if (m == null) {
                mv = super.visitMethod(access, name, desc, signature, exceptions);
            } else {
                mv = super.visitMethod(access, m.getName(), m.getDesc(), signature, exceptions);
            }
        }
        mv =  new MethodNameChangerMethodAdapter(mv);
        if (ObfEnv.config.isEnableHideMethod()) {
            hideMethod(mv);
        }
        return mv;
    }


    static class MethodNameChangerMethodAdapter extends MethodVisitor {
        MethodNameChangerMethodAdapter(MethodVisitor mv) {
            super(Const.ASMVersion, mv);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            MethodReference.Handle m = ObfEnv.methodNameObfMapping.get(new MethodReference.Handle(
                    new ClassReference.Handle(owner),
                    name,
                    descriptor
            ));
            if (m != null) {
                super.visitMethodInsn(opcode, m.getClassReference().getName(), m.getName(), m.getDesc(), isInterface);
                return;
            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
        @Override
        public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
            MethodReference.Handle m = ObfEnv.methodNameObfMapping.get(new MethodReference.Handle(
                    new ClassReference.Handle(bootstrapMethodHandle.getOwner()),
                    bootstrapMethodHandle.getName(),
                    bootstrapMethodHandle.getDesc()
            ));
            Handle handle;
            if (m != null) {
                handle = new Handle(
                        bootstrapMethodHandle.getTag(),
                        m.getClassReference().getName(),
                        m.getName(),
                        m.getDesc(),
                        bootstrapMethodHandle.isInterface());
            } else {
                handle = bootstrapMethodHandle;
            }

            for (int i = 0; i < bootstrapMethodArguments.length; i++) {
                Object obj = bootstrapMethodArguments[i];
                if (obj instanceof Handle) {
                    Handle ho = (Handle) obj;
                    MethodReference.Handle mo = ObfEnv.methodNameObfMapping.get(new MethodReference.Handle(
                            new ClassReference.Handle(ho.getOwner()),
                            ho.getName(),
                            ho.getDesc()
                    ));
                    Handle tempHandle;
                    if (mo != null) {
                        tempHandle = new Handle(
                                ho.getTag(),
                                mo.getClassReference().getName(),
                                mo.getName(),
                                mo.getDesc(),
                                ho.isInterface());
                    } else {
                        tempHandle = ho;
                    }
                    bootstrapMethodArguments[i] = tempHandle;
                }
            }
            super.visitInvokeDynamicInsn(name, descriptor, handle, bootstrapMethodArguments);
        }
    }
}