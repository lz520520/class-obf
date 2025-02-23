/**
 * @author lz520520
 * @date 2024/3/15 10:35
 */

package me.n1ar4.clazz.obfuscator.asm;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassWriter;

public class SyntheticAttribute extends Attribute {

    public SyntheticAttribute() {
        super("Synthetic");
    }
    @Override
    protected ByteVector write(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
        // Synthetic属性没有实际值，因此不需要写入任何内容
        ByteVector byteVector = new ByteVector();
        return byteVector;
    }
}