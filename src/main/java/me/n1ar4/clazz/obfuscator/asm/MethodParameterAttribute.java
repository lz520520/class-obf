/**
 * @author lz520520
 * @date 2024/3/15 13:15
 */

package me.n1ar4.clazz.obfuscator.asm;


import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassWriter;

public class MethodParameterAttribute extends Attribute {
    public int length;
    public int index;

    public MethodParameterAttribute(int length, int index) {
        super("MethodParameters");
        this.length = length;
        this.index = index;
    }
    @Override
    protected ByteVector write(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
        ByteVector byteVector = new ByteVector();
        byteVector.putByte(length);
        for (int i = 0; i < length; i++) {
            // nameIndex
            byteVector.putByte(0);
            byteVector.putByte(0);
            // access_logs
            byteVector.putByte(0);
            byteVector.putByte(0);
        }

        return byteVector;
    }
}

