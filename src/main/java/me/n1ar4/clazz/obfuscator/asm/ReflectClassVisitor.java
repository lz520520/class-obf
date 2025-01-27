package me.n1ar4.clazz.obfuscator.asm;

import me.n1ar4.clazz.obfuscator.Const;
import me.n1ar4.clazz.obfuscator.core.ObfEnv;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ReflectClassVisitor extends ClassVisitor {
    public ReflectClassVisitor(ClassVisitor classVisitor) {
        super(Const.ASMVersion, classVisitor);
    }

    @Override
    public void visitSource(String source, String debug) {
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        return new MethodVisitor(Const.ASMVersion, mv) {
            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                if (opcode == Opcodes.INVOKESPECIAL) {
                    if (!ObfEnv.config.isEnableReflectSpecial()) {
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        return;
                    }
                }
                if (opcode == Opcodes.INVOKEVIRTUAL) {
                    if (!ObfEnv.config.isEnableReflectVirtual()) {
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        return;
                    }
                }
                if (opcode == Opcodes.INVOKESTATIC) {
                    if (!ObfEnv.config.isEnableReflectStatic()) {
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        return;
                    }
                }
                if (opcode == Opcodes.INVOKEINTERFACE) {
                    if (!ObfEnv.config.isEnableReflectInterface()) {
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        return;
                    }
                }

                if (opcode == Opcodes.INVOKEVIRTUAL ||
                        opcode == Opcodes.INVOKESPECIAL ||
                        opcode == Opcodes.INVOKESTATIC ||
                        opcode == Opcodes.INVOKEINTERFACE) {
                    Type[] argumentTypes = Type.getArgumentTypes(descriptor);
                    int numParams = argumentTypes.length;

                    mv.visitIntInsn(Opcodes.BIPUSH, numParams);
                    mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");

                    int localVarIndex = (opcode == Opcodes.INVOKESTATIC) ? 0 : 1;
                    for (int i = 0; i < numParams; i++) {
                        mv.visitInsn(Opcodes.DUP);
                        mv.visitIntInsn(Opcodes.BIPUSH, i);

                        Type argType = argumentTypes[i];
                        switch (argType.getSort()) {
                            case Type.BOOLEAN:
                                mv.visitVarInsn(Opcodes.ILOAD, localVarIndex);
                                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean",
                                        "valueOf", "(Z)Ljava/lang/Boolean;", false);
                                break;
                            case Type.CHAR:
                                mv.visitVarInsn(Opcodes.ILOAD, localVarIndex);
                                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character",
                                        "valueOf", "(C)Ljava/lang/Character;", false);
                                break;
                            case Type.BYTE:
                                mv.visitVarInsn(Opcodes.ILOAD, localVarIndex);
                                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte",
                                        "valueOf", "(B)Ljava/lang/Byte;", false);
                                break;
                            case Type.SHORT:
                                mv.visitVarInsn(Opcodes.ILOAD, localVarIndex);
                                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short",
                                        "valueOf", "(S)Ljava/lang/Short;", false);
                                break;
                            case Type.INT:
                                mv.visitVarInsn(Opcodes.ILOAD, localVarIndex);
                                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer",
                                        "valueOf", "(I)Ljava/lang/Integer;", false);
                                break;
                            case Type.FLOAT:
                                mv.visitVarInsn(Opcodes.FLOAD, localVarIndex);
                                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float",
                                        "valueOf", "(F)Ljava/lang/Float;", false);
                                break;
                            case Type.LONG:
                                mv.visitVarInsn(Opcodes.LLOAD, localVarIndex);
                                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long",
                                        "valueOf", "(J)Ljava/lang/Long;", false);
                                localVarIndex++;
                                break;
                            case Type.DOUBLE:
                                mv.visitVarInsn(Opcodes.DLOAD, localVarIndex);
                                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double",
                                        "valueOf", "(D)Ljava/lang/Double;", false);
                                localVarIndex++;
                                break;
                            default:
                                mv.visitVarInsn(Opcodes.ALOAD, localVarIndex);
                        }

                        mv.visitInsn(Opcodes.AASTORE);
                        localVarIndex++;
                    }

                    String internalNameToClassName = owner.replace('/', '.');
                    mv.visitLdcInsn(internalNameToClassName);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Class", "forName",
                            "(Ljava/lang/String;)Ljava/lang/Class;", false);
                    mv.visitLdcInsn(name);

                    mv.visitIntInsn(Opcodes.BIPUSH, argumentTypes.length);
                    mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Class");
                    for (int i = 0; i < argumentTypes.length; i++) {
                        mv.visitInsn(Opcodes.DUP);
                        mv.visitIntInsn(Opcodes.BIPUSH, i);
                        mv.visitLdcInsn(Type.getType(argumentTypes[i].getDescriptor()));
                        mv.visitInsn(Opcodes.AASTORE);
                    }

                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getMethod",
                            "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);

                    if (opcode == Opcodes.INVOKESTATIC) {
                        mv.visitInsn(Opcodes.ACONST_NULL);
                    } else {
                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                    }

                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Method", "invoke",
                            "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", false);

                    Type returnType = Type.getReturnType(descriptor);
                    if (returnType.getSort() != Type.VOID) {
                        handleReturnType(returnType);
                    } else {
                        mv.visitInsn(Opcodes.POP);
                    }

                    return;
                }
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }

            private void handleReturnType(Type returnType) {
                String wrapperType;
                String unwrapMethod;

                switch (returnType.getSort()) {
                    case Type.BOOLEAN:
                        wrapperType = "java/lang/Boolean";
                        unwrapMethod = "booleanValue";
                        break;
                    case Type.CHAR:
                        wrapperType = "java/lang/Character";
                        unwrapMethod = "charValue";
                        break;
                    case Type.BYTE:
                        wrapperType = "java/lang/Byte";
                        unwrapMethod = "byteValue";
                        break;
                    case Type.SHORT:
                        wrapperType = "java/lang/Short";
                        unwrapMethod = "shortValue";
                        break;
                    case Type.INT:
                        wrapperType = "java/lang/Integer";
                        unwrapMethod = "intValue";
                        break;
                    case Type.FLOAT:
                        wrapperType = "java/lang/Float";
                        unwrapMethod = "floatValue";
                        break;
                    case Type.LONG:
                        wrapperType = "java/lang/Long";
                        unwrapMethod = "longValue";
                        break;
                    case Type.DOUBLE:
                        wrapperType = "java/lang/Double";
                        unwrapMethod = "doubleValue";
                        break;
                    default:
                        if (returnType.getSort() == Type.OBJECT || returnType.getSort() == Type.ARRAY) {
                            mv.visitTypeInsn(Opcodes.CHECKCAST, returnType.getInternalName());
                        }
                        return;
                }

                mv.visitTypeInsn(Opcodes.CHECKCAST, wrapperType);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, wrapperType, unwrapMethod,
                        "()" + returnType.getDescriptor(), false);
            }
        };
    }
}