package com.konantech.konansearch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

class BA {
    BA() {
    }

    public static int alignWordSize(int var0) {
        return (var0 - 1 & -8) + 8 - var0;
    }

    public static int alignedSize(int var0) {
        return (var0 - 1 & -8) + 8;
    }

    public static void writeByte(ByteArrayOutputStream var0, byte var1) throws IOException {
        byte[] var2 = new byte[]{var1};
        var0.write(var2, 0, 1);
    }

    public static void writeShort(ByteArrayOutputStream var0, short var1) throws IOException {
        byte[] var2 = new byte[2];
        short2bytes(var1, var2, 0);
        var0.write(var2, 0, 2);
    }

    public static void writeInteger(ByteArrayOutputStream var0, int var1) throws IOException {
        byte[] var2 = new byte[4];
        int2bytes(var1, var2, 0);
        var0.write(var2, 0, 4);
    }

    public static void writeLong(ByteArrayOutputStream var0, long var1) throws IOException {
        byte[] var3 = new byte[8];
        long2bytes(var1, var3, 0);
        var0.write(var3, 0, 8);
    }

    public static void writeDouble(ByteArrayOutputStream var0, double var1) {
        byte[] var3 = new byte[8];
        double2bytes(var1, var3, 0);
        var0.write(var3, 0, 8);
    }

    public static void writeString(ByteArrayOutputStream var0, String var1) throws IOException {
        byte[] var2 = var1.getBytes();
        var0.write(var2, 0, var2.length);
        var0.write(0);
    }

    public static void writeString(ByteArrayOutputStream var0, String var1, String var2) throws IOException {
        byte[] var3 = var1.getBytes(var2);
        var0.write(var3, 0, var3.length);
        var0.write(0);
    }

    public static void writeNString(ByteArrayOutputStream var0, String var1, int var2) throws IOException {
        boolean var3 = false;
        byte[] var4 = new byte[var2];
        byte[] var5 = var1.getBytes();

        for(int var6 = 0; var6 < var2 && var6 < var5.length; ++var6) {
            var4[var6] = var5[var6];
        }

        var0.write(var4, 0, var2);
    }

    public static void writeNString(ByteArrayOutputStream var0, String var1, String var2, int var3) throws IOException {
        boolean var4 = false;
        byte[] var5 = new byte[var3];
        byte[] var6 = var1.getBytes(var2);

        for(int var7 = 0; var7 < var3 && var7 < var6.length; ++var7) {
            var5[var7] = var6[var7];
        }

        var0.write(var5, 0, var3);
    }

    public static void writeNull(ByteArrayOutputStream var0) throws IOException {
        var0.write(0);
    }

    public static void writeAlignSize(ByteArrayOutputStream var0) throws IOException {
        int var1 = alignedSize(var0.size()) - var0.size();

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

    public static void double2bytes(double var0, byte[] var2, int var3) {
        ByteBuffer var4 = ByteBuffer.wrap(var2);
        var4.putDouble(var3, var0);
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

    public static double bytes2double(byte[] var0, int var1) {
        ByteBuffer var2 = ByteBuffer.wrap(var0);
        return var2.getDouble(var1);
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

    public static byte[] bytes2bytes(byte[] var0, int var1, int var2) {
        byte[] var5 = new byte[var2];
        if (var0.length <= var1) {
            return null;
        } else {
            int var3 = var1;

            for(int var4 = 0; var3 < var2 && var3 < var0.length; ++var4) {
                var5[var4] = var0[var3];
                ++var3;
            }

            return var5;
        }
    }
}
