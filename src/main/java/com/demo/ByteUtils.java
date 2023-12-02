package com.demo;

import cn.hutool.core.codec.Base64;


import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ByteUtils {
    public ByteUtils() {
    }

    public static String printData(byte[] bytes) {
        List<String> list = new ArrayList();

        for (int i = 0; i < bytes.length; ++i) {
            list.add(String.format("%02X", bytes[i]));
        }

        return Arrays.toString(list.toArray()).replace(",", " ");
    }




    public static String toBase64Hex(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        byte[] var2 = bytes;
        int var3 = bytes.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            stringBuffer.append(String.format("%02x", b & 255));
        }

        return Base64.encode(stringBuffer.toString());
    }

    public static byte[] cryptToBytes(String challenge) {
        String hexStr = new String(Base64.decode(challenge));
        BigInteger bigInteger = new BigInteger(hexStr, 16);
        return bigintToArr(bigInteger, 128);
    }

    public static byte[] bigintToArr(BigInteger bigInteger, int len) {
        byte[] array = bigInteger.toByteArray();
        byte[] byteArray = new byte[len];
        if (array[0] == 0) {
            System.arraycopy(array, 1, byteArray, 0, len);
        } else {
            byteArray = array;
        }

        return byteArray;
    }
}
