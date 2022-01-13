package com.konantech.crx;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

class CrxUtil {
    CrxUtil() {
    }

    public static int alignWordSize(int var0) {
        return (var0 - 1 & -8) + 8 - var0;
    }

    public static int ALIGNED_SIZE(int var0) {
        return (var0 - 1 & -8) + 8;
    }

    public static void BA_writeByte(ByteArrayOutputStream var0, byte var1) throws IOException {
        byte[] var2 = new byte[]{var1};
        var0.write(var2, 0, 1);
    }

    public static void BA_writeShort(ByteArrayOutputStream var0, short var1) throws IOException {
        byte[] var2 = new byte[2];
        short2bytes(var1, var2, 0);
        var0.write(var2, 0, 2);
    }

    public static void BA_writeInteger(ByteArrayOutputStream var0, int var1) throws IOException {
        byte[] var2 = new byte[4];
        int2bytes(var1, var2, 0);
        var0.write(var2, 0, 4);
    }

    public static void BA_writeLong(ByteArrayOutputStream var0, long var1) throws IOException {
        byte[] var3 = new byte[8];
        long2bytes(var1, var3, 0);
        var0.write(var3, 0, 8);
    }

    public static void BA_writeString(ByteArrayOutputStream var0, String var1) throws IOException {
        byte[] var2 = var1.getBytes();
        var0.write(var2, 0, var2.length);
        var0.write(0);
    }

    public static void BA_writeString(ByteArrayOutputStream var0, String var1, String var2) throws IOException {
        byte[] var3 = var1.getBytes(var2);
        var0.write(var3, 0, var3.length);
        var0.write(0);
    }

    public static void BA_writeNString(ByteArrayOutputStream var0, String var1, int var2) throws IOException {
        boolean var3 = false;
        byte[] var4 = new byte[var2];
        byte[] var5 = var1.getBytes();

        for(int var6 = 0; var6 < var5.length; ++var6) {
            var4[var6] = var5[var6];
        }

        var0.write(var4, 0, var2);
    }

    public static void BA_writeNString(ByteArrayOutputStream var0, String var1, String var2, int var3) throws IOException {
        boolean var4 = false;
        byte[] var5 = new byte[var3];
        byte[] var6 = var1.getBytes(var2);

        for(int var7 = 0; var7 < var6.length; ++var7) {
            var5[var7] = var6[var7];
        }

        var0.write(var5, 0, var3);
    }

    public static void BA_writeNull(ByteArrayOutputStream var0) throws IOException {
        var0.write(0);
    }

    public static void BA_writeAlignSize(ByteArrayOutputStream var0) throws IOException {
        int var1 = ALIGNED_SIZE(var0.size()) - var0.size();

        for(int var2 = 0; var2 < var1; ++var2) {
            var0.write(0);
        }

    }

    public static void short2bytes(short var0, byte[] var1, int var2) {
        ByteBuffer var3 = ByteBuffer.wrap(var1);
        var3.putShort(var2, var0);
    }

    public static void int2bytes(int var0, byte[] var1, int var2) {
        ByteBuffer var3 = ByteBuffer.wrap(var1);
        var3.putInt(var2, var0);
    }

    public static void long2bytes(long var0, byte[] var2, int var3) {
        ByteBuffer var4 = ByteBuffer.wrap(var2);
        var4.putLong(var3, var0);
    }

    public static short bytes2short(byte[] var0, int var1) {
        ByteBuffer var2 = ByteBuffer.wrap(var0);
        return var2.getShort(var1);
    }

    public static int bytes2int(byte[] var0, int var1) {
        ByteBuffer var2 = ByteBuffer.wrap(var0);
        return var2.getInt(var1);
    }

    public static long bytes2long(byte[] var0, int var1) {
        ByteBuffer var2 = ByteBuffer.wrap(var0);
        return var2.getLong(var1);
    }

    public static int bytes2str(byte[] var0, int var1) {
        if (var0.length <= var1) {
            return 0;
        } else {
            int var2;
            for(var2 = var1; var2 < var0.length && var0[var2] != 0; ++var2) {
            }

            return var2 - var1;
        }
    }
}
