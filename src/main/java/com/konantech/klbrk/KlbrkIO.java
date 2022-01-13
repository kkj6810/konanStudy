package com.konantech.klbrk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class KlbrkIO {
    private ArrayList<KlbrkParam> m_param = null;
    private String m_module_name = null;
    private String m_function_name = null;
    private String m_user_log = null;
    private String m_config_id = null;
    private String m_msg = "";
    private int m_curr_pos = 0;
    private String m_char_encoding = null;
    private static final String PROTOCOL_VERSION = "brk-000";
    private static final int TYPE_INT = 10;
    private static final int TYPE_LONG = 11;
    private static final int TYPE_DOUBLE = 12;
    private static final int TYPE_STRING = 30;
    private static final int TYPE_BLOB = 40;

    private int InitMemberVariable() {
        this.m_module_name = null;
        this.m_function_name = null;
        this.m_user_log = null;
        this.m_config_id = null;
        return 0;
    }

    public KlbrkIO() {
        this.m_param = new ArrayList();
        this.InitMemberVariable();
        this.m_char_encoding = System.getProperty("file.encoding");
    }

    public int SetCharacterEncoding(String var1) {
        this.m_char_encoding = var1;
        return 0;
    }

    public int ClearAll() throws IOException {
        if (this.m_param != null) {
            this.m_param.clear();
        }

        return this.InitMemberVariable();
    }

    public String GetMessage() {
        return this.m_msg;
    }

    public int SetModuleName(String var1) {
        this.m_module_name = new String(var1);
        return 0;
    }

    public String GetModuleName() {
        return this.m_module_name;
    }

    public int SetFunctionName(String var1) {
        this.m_function_name = new String(var1);
        return 0;
    }

    public String GetFunctionName() {
        return this.m_function_name;
    }

    public int SetUserLog(String var1) {
        this.m_user_log = new String(var1);
        return 0;
    }

    public String GetUserLog() {
        return this.m_user_log;
    }

    public int SetConfigID(String var1) {
        this.m_config_id = new String(var1);
        return 0;
    }

    public String GetConfigID() {
        return this.m_config_id;
    }

    public int PutInt(int var1) {
        return this.AddValue(10, 4, var1);
    }

    public int GetInt() throws IOException {
        KlbrkParam var1 = this.GetCurrParam();
        return var1 == null ? -1 : (Integer)var1.m_value;
    }

    public int PutLong(long var1) {
        return this.AddValue(11, 8, var1);
    }

    public long GetLong() throws IOException {
        KlbrkParam var1 = this.GetCurrParam();
        return var1 == null ? -1L : (Long)var1.m_value;
    }

    public int PutDouble(double var1) {
        return this.AddValue(12, 8, var1);
    }

    public double GetDouble() {
        this.m_msg = "type double does not support yet";
        return -1.0D;
    }

    public int PutString(String var1) throws UnsupportedEncodingException {
        return this.AddValue(30, var1.getBytes(this.m_char_encoding).length, var1);
    }

    public String GetString() throws IOException {
        KlbrkParam var1 = this.GetCurrParam();
        return var1 == null ? null : (String)var1.m_value;
    }

    public int PutBLOB(byte[] var1, int var2) {
        return this.AddValue(40, var2, var1);
    }

    public int GetBLOB(byte[] var1) throws IOException {
        KlbrkParam var2 = this.GetCurrParam();
        if (var2 == null) {
            return -1;
        } else {
            var1 = (byte[])((byte[])var2.m_value);
            return var2.m_size;
        }
    }

    private int AddValue(int var1, int var2, Object var3) {
        KlbrkParam var4 = null;
        var4 = new KlbrkParam();
        var4.m_type = var1;
        var4.m_size = var2;
        var4.m_value = var3;
        if (!this.m_param.add(var4)) {
            this.m_msg = "cannot add param";
            return -1;
        } else {
            return 0;
        }
    }

    private KlbrkParam GetCurrParam() throws IOException {
        KlbrkParam var1 = null;
        if (this.m_curr_pos >= 0 && this.m_curr_pos < this.m_param.size()) {
            var1 = (KlbrkParam)this.m_param.get(this.m_curr_pos);
            ++this.m_curr_pos;
            return var1;
        } else {
            this.m_msg = "protocol format error(param index out of range(" + this.m_curr_pos + "," + this.m_param.size() + ")).";
            throw new IOException(this.m_msg);
        }
    }

    public int GetParamCount() {
        return this.m_param.size();
    }

    public int GetParamType() throws IOException {
        KlbrkParam var1 = this.GetCurrParam();
        return var1 == null ? -1 : var1.m_type;
    }

    public int GetParamLength() throws IOException {
        KlbrkParam var1 = this.GetCurrParam();
        return var1 == null ? -1 : var1.m_size;
    }

    public byte[] GetRequestPacket() throws IOException {
        boolean var1 = false;
        boolean var2 = false;
        boolean var3 = false;
        KlbrkParam var4 = null;
        boolean var5 = false;
        boolean var6 = false;
        Object var7 = null;
        ByteArrayOutputStream var8 = null;
        if (this.m_module_name == null) {
            this.m_msg = "module name not specified";
            return null;
        } else if (this.m_function_name == null) {
            this.m_msg = "function name not specified";
            return null;
        } else if (this.m_user_log == null) {
            this.m_msg = "user log not specified";
            return null;
        } else if (this.m_config_id == null) {
            this.m_msg = "config id not specified";
            return null;
        } else {
            var8 = new ByteArrayOutputStream();
            byte var14 = 0;
            int var16 = KlbrkUtil.ALIGNED_SIZE("brk-000".length() + 1);
            KlbrkUtil.BA_writeInteger(var8, var14);
            KlbrkUtil.BA_writeInteger(var8, var16);
            int var15 = var14 + var16;
            var16 = KlbrkUtil.ALIGNED_SIZE(this.m_module_name.length() + 1);
            KlbrkUtil.BA_writeInteger(var8, var15);
            KlbrkUtil.BA_writeInteger(var8, var16);
            var15 += var16;
            var16 = KlbrkUtil.ALIGNED_SIZE(this.m_function_name.length() + 1);
            KlbrkUtil.BA_writeInteger(var8, var15);
            KlbrkUtil.BA_writeInteger(var8, var16);
            var15 += var16;
            var16 = KlbrkUtil.ALIGNED_SIZE(this.m_user_log.length() + 1);
            KlbrkUtil.BA_writeInteger(var8, var15);
            KlbrkUtil.BA_writeInteger(var8, var16);
            var15 += var16;
            var16 = KlbrkUtil.ALIGNED_SIZE(this.m_config_id.length() + 1);
            KlbrkUtil.BA_writeInteger(var8, var15);
            KlbrkUtil.BA_writeInteger(var8, var16);
            KlbrkUtil.BA_writeString(var8, "brk-000");
            KlbrkUtil.BA_writeAlignSize(var8);
            KlbrkUtil.BA_writeString(var8, this.m_module_name);
            KlbrkUtil.BA_writeAlignSize(var8);
            KlbrkUtil.BA_writeString(var8, this.m_function_name);
            KlbrkUtil.BA_writeAlignSize(var8);
            KlbrkUtil.BA_writeString(var8, this.m_user_log);
            KlbrkUtil.BA_writeAlignSize(var8);
            KlbrkUtil.BA_writeString(var8, this.m_config_id);
            KlbrkUtil.BA_writeAlignSize(var8);
            int var13 = this.m_param.size();
            KlbrkUtil.BA_writeInteger(var8, var13);
            KlbrkUtil.BA_writeAlignSize(var8);

            for(int var11 = 0; var11 < var13; ++var11) {
                var4 = (KlbrkParam)this.m_param.get(var11);
                if (var4.m_type == 10) {
                    int var20 = (Integer)var4.m_value;
                    KlbrkUtil.BA_writeInteger(var8, var4.m_type);
                    KlbrkUtil.BA_writeInteger(var8, var20);
                    KlbrkUtil.BA_writeAlignSize(var8);
                } else if (var4.m_type == 11) {
                    long var19 = (Long)var4.m_value;
                    KlbrkUtil.BA_writeInteger(var8, var4.m_type);
                    KlbrkUtil.BA_writeLong(var8, var19);
                    KlbrkUtil.BA_writeAlignSize(var8);
                } else {
                    if (var4.m_type == 12) {
                        this.m_msg = "type double does not support yet";
                        return null;
                    }

                    if (var4.m_type == 30) {
                        String var18 = (String)var4.m_value;
                        KlbrkUtil.BA_writeInteger(var8, var4.m_type);
                        KlbrkUtil.BA_writeInteger(var8, var4.m_size);
                        KlbrkUtil.BA_writeString(var8, var18, this.m_char_encoding);
                        KlbrkUtil.BA_writeAlignSize(var8);
                    } else {
                        if (var4.m_type != 40) {
                            this.m_msg = "unknown type (" + var4.m_type + ")";
                            return null;
                        }

                        byte[] var9 = (byte[])((byte[])var4.m_value);
                        KlbrkUtil.BA_writeInteger(var8, var4.m_type);
                        KlbrkUtil.BA_writeInteger(var8, var4.m_size);

                        for(int var12 = 0; var12 < var4.m_size; ++var12) {
                            KlbrkUtil.BA_writeByte(var8, var9[var12]);
                        }

                        KlbrkUtil.BA_writeAlignSize(var8);
                    }
                }
            }

            byte[] var17 = var8.toByteArray();
            return var17;
        }
    }

    public int SetResponsePacket(byte[] var1) throws IOException {
        boolean var2 = false;
        boolean var3 = false;
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        boolean var7 = false;
        byte var8 = 0;
        String var9 = null;
        int var16 = KlbrkUtil.bytes2int(var1, var8);
        int var18 = var8 + 8;
        var16 += 40;
        int var17 = KlbrkUtil.bytes2str(var1, var16);
        var9 = new String(var1, var16, var17, this.m_char_encoding);
        if (var9.compareTo("brk-000") != 0) {
            this.m_msg = "incompatible protocol version";
            return -1;
        } else {
            var16 = KlbrkUtil.bytes2int(var1, var18);
            var18 += 8;
            var16 += 40;
            var17 = KlbrkUtil.bytes2str(var1, var16);
            this.m_module_name = new String(var1, var16, var17, this.m_char_encoding);
            var16 = KlbrkUtil.bytes2int(var1, var18);
            var18 += 8;
            var16 += 40;
            var17 = KlbrkUtil.bytes2str(var1, var16);
            this.m_function_name = new String(var1, var16, var17, this.m_char_encoding);
            var16 = KlbrkUtil.bytes2int(var1, var18);
            var18 += 8;
            var16 += 40;
            var17 = KlbrkUtil.bytes2str(var1, var16);
            this.m_user_log = new String(var1, var16, var17, this.m_char_encoding);
            var16 = KlbrkUtil.bytes2int(var1, var18);
            var18 += 8;
            var16 += 40;
            var17 = KlbrkUtil.bytes2str(var1, var16);
            this.m_config_id = new String(var1, var16, var17, this.m_char_encoding);
            var18 = var16 + KlbrkUtil.ALIGNED_SIZE(var17 + 1);
            int var15 = KlbrkUtil.bytes2int(var1, var18);
            var18 += 4;
            var18 = KlbrkUtil.ALIGNED_SIZE(var18);

            for(int var14 = 0; var14 < var15; ++var14) {
                int var10 = KlbrkUtil.bytes2int(var1, var18);
                var18 += 4;
                int var11;
                int var13;
                if (var10 == 10) {
                    var11 = KlbrkUtil.bytes2int(var1, var18);
                    var18 += 4;
                    var13 = this.PutInt(var11);
                    if (var13 < 0) {
                        return -1;
                    }
                } else if (var10 == 11) {
                    long var19 = KlbrkUtil.bytes2long(var1, var18);
                    var18 += 8;
                    var13 = this.PutLong(var19);
                    if (var13 < 0) {
                        return -1;
                    }
                } else {
                    if (var10 == 12) {
                        this.m_msg = "type double does not support yet";
                        return -1;
                    }

                    if (var10 == 30) {
                        var11 = KlbrkUtil.bytes2int(var1, var18);
                        var18 += 4;
                        var17 = KlbrkUtil.bytes2str(var1, var18);
                        String var12 = new String(var1, var18, var17, this.m_char_encoding);
                        var18 += KlbrkUtil.ALIGNED_SIZE(var11 + 1);
                        var13 = this.PutString(var12);
                        if (var13 < 0) {
                            return -1;
                        }
                    } else {
                        if (var10 != 40) {
                            this.m_msg = "unknown type (" + var10 + ")";
                            return -1;
                        }

                        var11 = KlbrkUtil.bytes2int(var1, var18);
                        var18 += 4;
                        byte[] var20 = KlbrkUtil.bytes2bytes(var1, var18, var11);
                        if (var20 == null) {
                            this.m_msg = "cannot convert BLOB data";
                            return -1;
                        }

                        var18 += KlbrkUtil.ALIGNED_SIZE(var11);
                        var13 = this.PutBLOB(var20, var11);
                        if (var13 < 0) {
                            return -1;
                        }
                    }
                }

                var18 = KlbrkUtil.ALIGNED_SIZE(var18);
            }

            this.m_curr_pos = 0;
            return 0;
        }
    }
}
