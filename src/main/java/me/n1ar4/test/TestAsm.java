/**
 * @author lz520520
 * @date 2025/2/23 9:01
 */

package me.n1ar4.test;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Method;
import java.util.Base64;

public class TestAsm {
    public static byte[] iLLiiLiiLLiiLi(byte[] var0) throws Exception {
        byte[] var1;
        Method var3;
        if (System.getProperty("java.version").compareTo("1.9") >= 0) {
            Class var2 = Class.forName("java.util.Base64");
            var3 = var2.getMethod("getDecoder");
            Object var4 = var3.invoke(var2);
            Method var5 = var4.getClass().getMethod("decode", byte[].class);
            var1 = (byte[])var5.invoke(var4, var0);
        } else {
            Object var6 = Class.forName("sun.misc.BASE64Decoder").newInstance();
            var3 = var6.getClass().getMethod("decodeBuffer", String.class);
            var1 = (byte[])var3.invoke(var6, new String(var0));
        }

        return var1;
    }

    public static String iLLiiLi(String var0, String var1) {
        try {
            var1 = (new StringBuilder(var1)).reverse().toString();
            var0 = (new StringBuilder(var0)).reverse().toString();
            SecretKeySpec var2 = new SecretKeySpec(var1.getBytes(), "AES");
            Cipher var3 = Cipher.getInstance("AES");
            var3.init(2, var2);
            byte[] var4 = iLLiiLiiLLiiLi(var0.getBytes());
            byte[] var5 = var3.doFinal(var4);
            return new String(var5);
        } catch (Exception var7) {
            return "";
        }
    }
    private static final String LLiiiLi1i = "fdHgLFq3kifknw9y";
    public static void main(String[] args) throws Exception {
        String ret = iLLiiLi("=Up/OSaX9CPg8ilepGwMVUOgi30fP1IPyP68zxtf1I+H", LLiiiLi1i);
        System.out.println(ret);

    }
}
