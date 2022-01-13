package com.konantech.konansearch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MIO {
    private ArrayList<PARAM> m_param = null;
    private String m_msg;
    private String m_ver;
    private String m_module_name;
    private String m_function_name;
    private String m_user_log;
    private static int m_version_pckt_sz;
    private static int m_module_pckt_sz;
    private static int m_func_pckt_sz;
    private static int m_userlog_pckt_sz;
    private static int m_param_pckt_sz;
    private int m_curr_pos = 0;
    private String m_char_encoding = null;
    private String MOD_IO_VERSION = "005";
    private static final int TYPE_INT = 10;
    private static final int TYPE_LONG = 11;
    private static final int TYPE_DOUBLE = 12;
    private static final int TYPE_STR = 30;
    private static final int TYPE_BIN = 40;

    public int Init() {
        this.m_ver = new String(this.MOD_IO_VERSION);
        this.m_module_name = "";
        this.m_function_name = "";
        this.m_user_log = "";
        m_version_pckt_sz = this.m_ver.length() + 1;
        m_version_pckt_sz += BA.alignedSize(m_version_pckt_sz);
        m_module_pckt_sz = 0;
        m_module_pckt_sz = this.m_module_name.length() + 1;
        m_module_pckt_sz += BA.alignedSize(m_module_pckt_sz);
        m_func_pckt_sz = 0;
        m_func_pckt_sz = this.m_function_name.length() + 1;
        m_func_pckt_sz += BA.alignedSize(m_func_pckt_sz);
        m_userlog_pckt_sz = 0;
        m_userlog_pckt_sz = this.m_user_log.length() + 1;
        m_userlog_pckt_sz += BA.alignedSize(m_userlog_pckt_sz);
        m_param_pckt_sz = 0;
        m_param_pckt_sz += 4;
        m_param_pckt_sz += BA.alignedSize(m_param_pckt_sz);
        if (this.m_param != null) {
            this.m_param.clear();
        } else {
            this.m_param = new ArrayList();
        }

        this.m_param.ensureCapacity(16);
        return 0;
    }

    public MIO() {
        this.m_param = new ArrayList();
        this.m_char_encoding = System.getProperty("file.encoding");
        this.Init();
    }

    public int SetCharacterEncoding(String var1) {
        this.m_char_encoding = var1;
        return 0;
    }

    public String GetMessage() {
        return this.m_msg;
    }

    public String GET_PROTOCOL_VERSION() {
        return this.m_ver;
    }

    public int GET_PARAM_TYPE() {
        PARAM var1 = this.GetCurrParam();
        if (var1 == null) {
            this.m_msg = new String("current index is null.");
            return -1;
        } else {
            return var1.m_type;
        }
    }

    public int GET_PARAM_COUNT() {
        return this.m_param.size();
    }

    public int PUT_MODULE_NAME(String var1) {
        this.m_module_name = new String(var1);
        m_module_pckt_sz = this.m_module_name.length() + 1;
        m_module_pckt_sz += BA.alignedSize(m_module_pckt_sz);
        return 0;
    }

    public String GET_MODULE_NAME() {
        return this.m_module_name;
    }

    public int PUT_FUNCTION_NAME(String var1) {
        this.m_function_name = new String(var1);
        m_func_pckt_sz = this.m_function_name.length() + 1;
        m_func_pckt_sz += BA.alignedSize(m_func_pckt_sz);
        return 0;
    }

    public String GET_FUNCTION_NAME() {
        return this.m_function_name;
    }

    public int PUT_USER_LOG(String var1) throws UnsupportedEncodingException {
        this.m_user_log = new String(var1);
        m_userlog_pckt_sz = this.m_user_log.getBytes(this.m_char_encoding).length + 1;
        m_userlog_pckt_sz += BA.alignedSize(m_userlog_pckt_sz);
        return 0;
    }

    public String GET_USER_LOG() {
        return this.m_user_log;
    }

    public int PUT_INT(int var1) {
        return this.AddValue(10, 4, var1);
    }

    public int GET_INT(RV var1) {
        PARAM var2 = this.GetCurrParam();
        if (var2 == null) {
            this.m_msg = new String("current index is null.");
            return -1;
        } else {
            var1.m_int = (Integer)var2.m_value;
            return 0;
        }
    }

    public int PUT_LONG(long var1) {
        return this.AddValue(11, 8, var1);
    }

    public int GET_LONG(RV var1) {
        PARAM var2 = this.GetCurrParam();
        if (var2 == null) {
            this.m_msg = new String("current index is null.");
            return -1;
        } else {
            var1.m_long = (Long)var2.m_value;
            return 0;
        }
    }

    public int PUT_DOUBLE(double var1) {
        return this.AddValue(12, 8, var1);
    }

    public int GET_DOUBLE(RV var1) {
        PARAM var2 = this.GetCurrParam();
        if (var2 == null) {
            this.m_msg = new String("current index is null.");
            return -1;
        } else {
            var1.m_double = (Double)var2.m_value;
            return 0;
        }
    }

    public int PUT_STR(String var1) throws UnsupportedEncodingException {
        return this.AddValue(30, var1.getBytes(this.m_char_encoding).length, var1);
    }

    public int GET_STR(RV var1) {
        PARAM var2 = this.GetCurrParam();
        if (var2 == null) {
            this.m_msg = new String("current index is null.");
            return -1;
        } else {
            var1.m_str = (String)var2.m_value;
            return 0;
        }
    }

    public int PUT_BIN(byte[] var1, int var2) {
        return this.AddValue(40, var2, var1);
    }

    public int GET_BIN(RV var1) {
        PARAM var2 = this.GetCurrParam();
        if (var2 == null) {
            this.m_msg = new String("current index is null.");
            return -1;
        } else {
            var1.m_bin = (byte[])((byte[])var2.m_value);
            var1.m_sz_bin = var2.m_size;
            return 0;
        }
    }

    private String _typeName(int var1) {
        switch(var1) {
            case 10:
                return "INT";
            case 11:
                return "LONG";
            case 12:
                return "DOUBLE";
            case 30:
                return "STRING";
            case 40:
                return "BINARY";
            default:
                return "Unknown:" + var1;
        }
    }

    private int AddValue(int var1, int var2, Object var3) {
        PARAM var4 = new PARAM();
        var4.m_type = var1;
        var4.m_size = var2;
        var4.m_value = var3;
        if (!this.m_param.add(var4)) {
            this.m_msg = "cannot add param";
            return -1;
        } else {
            CalcPacketSz(var4);
            return 0;
        }
    }

    private PARAM GetCurrParam() {
        if (this.m_curr_pos >= 0 && this.m_curr_pos < this.m_param.size()) {
            PARAM var1 = (PARAM)this.m_param.get(this.m_curr_pos);
            ++this.m_curr_pos;
            return var1;
        } else {
            this.m_msg = "[MIO] param index out-of-range.";
            return null;
        }
    }

    public void REWIND() {
        this.m_curr_pos = 0;
    }

    public void NEXT(int var1) {
        while(this.m_curr_pos < this.m_param.size() && var1 > 0) {
            --var1;
            ++this.m_curr_pos;
        }

    }

    public void PREV(int var1) {
        while(this.m_curr_pos > 0 && var1 > 0) {
            --var1;
            --this.m_curr_pos;
        }

    }

    public boolean IndexOOR() {
        return this.m_curr_pos < 0 || this.m_curr_pos >= this.m_param.size();
    }

    public int GET_VERSION_PACKET_SIZE() {
        return m_version_pckt_sz;
    }

    public int GET_MODULE_PACKET_SIZE() {
        return m_module_pckt_sz;
    }

    public int GET_FUNCTION_PACKET_SIZE() {
        return m_func_pckt_sz;
    }

    public int GET_USER_LOG_PACKET_SIZE() {
        return m_userlog_pckt_sz;
    }

    public int GET_PARAM_PACKET_SIZE() {
        return m_param_pckt_sz;
    }

    private static int CalcPacketSz(PARAM var0) {
        switch(var0.m_type) {
            case 10:
                m_param_pckt_sz += 8;
                break;
            case 11:
                m_param_pckt_sz += 12;
                m_param_pckt_sz += BA.alignedSize(m_param_pckt_sz);
                break;
            case 12:
                m_param_pckt_sz += 12;
                m_param_pckt_sz += BA.alignedSize(m_param_pckt_sz);
                break;
            case 30:
                if (var0.m_size > 0) {
                    m_param_pckt_sz += 8 + var0.m_size + 1;
                } else {
                    m_param_pckt_sz += 8;
                }

                m_param_pckt_sz += BA.alignedSize(m_param_pckt_sz);
                break;
            case 40:
                m_param_pckt_sz += 8 + var0.m_size;
                m_param_pckt_sz += BA.alignedSize(m_param_pckt_sz);
        }

        return 0;
    }

    public byte[] Serialize() throws IOException {
        boolean var1 = false;
        boolean var2 = false;
        boolean var3 = false;
        boolean var4 = false;
        Object var7 = null;
        ByteArrayOutputStream var8 = null;
        if (this.m_module_name == null) {
            this.m_msg = "module name not specified.";
            return null;
        } else if (this.m_function_name == null) {
            this.m_msg = "module name not specified.";
            return null;
        } else if (this.m_user_log == null) {
            this.m_msg = "user log not specified.";
            return null;
        } else {
            var8 = new ByteArrayOutputStream();
            BA.writeInteger(var8, 0);
            BA.writeInteger(var8, 0);
            BA.writeInteger(var8, 0);
            BA.writeInteger(var8, 0);
            BA.writeString(var8, this.MOD_IO_VERSION, this.m_char_encoding);
            BA.writeAlignSize(var8);
            BA.writeString(var8, this.m_module_name, this.m_char_encoding);
            BA.writeAlignSize(var8);
            BA.writeString(var8, this.m_function_name, this.m_char_encoding);
            BA.writeAlignSize(var8);
            BA.writeString(var8, this.m_user_log, this.m_char_encoding);
            BA.writeAlignSize(var8);
            int var6 = this.m_param.size();
            BA.writeInteger(var8, var6);
            BA.writeAlignSize(var8);

            for(int var16 = 0; var16 < var6; ++var16) {
                PARAM var5 = (PARAM)this.m_param.get(var16);
                switch(var5.m_type) {
                    case 10:
                        int var9 = (Integer)var5.m_value;
                        BA.writeInteger(var8, var5.m_type);
                        BA.writeInteger(var8, var9);
                        BA.writeAlignSize(var8);
                        break;
                    case 11:
                        long var10 = (Long)var5.m_value;
                        BA.writeInteger(var8, var5.m_type);
                        BA.writeLong(var8, var10);
                        BA.writeAlignSize(var8);
                        break;
                    case 12:
                        double var12 = (Double)var5.m_value;
                        BA.writeInteger(var8, var5.m_type);
                        BA.writeDouble(var8, var12);
                        BA.writeAlignSize(var8);
                        break;
                    case 30:
                        String var14 = (String)var5.m_value;
                        BA.writeInteger(var8, var5.m_type);
                        BA.writeInteger(var8, var5.m_size);
                        BA.writeString(var8, var14, this.m_char_encoding);
                        BA.writeAlignSize(var8);
                        break;
                    case 40:
                        byte[] var15 = (byte[])((byte[])var5.m_value);
                        BA.writeInteger(var8, var5.m_type);
                        BA.writeInteger(var8, var5.m_size);

                        for(int var17 = 0; var17 < var5.m_size; ++var17) {
                            BA.writeByte(var8, var15[var17]);
                        }

                        BA.writeAlignSize(var8);
                        break;
                    default:
                        this.m_msg = "[MIO Serialize] Unknown parameter type (" + var16 + "/" + var6 + ") : " + var5.m_type;
                }
            }

            byte[] var18 = var8.toByteArray();
            return var18;
        }
    }

    public int Deserialize(byte[] var1) throws IOException {
        boolean var2 = false;
        boolean var3 = false;
        boolean var4 = false;
        byte var5 = 0;
        boolean var6 = false;
        String var9 = null;
        int var20 = var5 + 8;
        int var21 = BA.bytes2str(var1, var20);
        var9 = new String(var1, var20, var21, this.m_char_encoding);
        if (var9.compareTo(this.MOD_IO_VERSION) != 0) {
            this.m_msg = "incompatible protocol version.";
            return -1;
        } else {
            var20 += var21 + 1;
            var20 += BA.alignWordSize(var20);
            var21 = BA.bytes2str(var1, var20);
            this.m_module_name = new String(var1, var20, var21, this.m_char_encoding);
            var20 += var21 + 1;
            var20 += BA.alignWordSize(var20);
            var21 = BA.bytes2str(var1, var20);
            this.m_function_name = new String(var1, var20, var21, this.m_char_encoding);
            var20 += var21 + 1;
            var20 += BA.alignWordSize(var20);
            var21 = BA.bytes2str(var1, var20);
            this.m_user_log = new String(var1, var20, var21, this.m_char_encoding);
            var20 += var21 + 1;
            var20 += BA.alignWordSize(var20);
            int var7 = BA.bytes2int(var1, var20);
            var20 += 4;
            var20 += BA.alignWordSize(var20);

            for(int var19 = 0; var19 < var7; ++var19) {
                int var8 = BA.bytes2int(var1, var20);
                var20 += 4;
                int var10;
                int var18;
                switch(var8) {
                    case 10:
                        int var11 = BA.bytes2int(var1, var20);
                        var20 += 4;
                        var18 = this.PUT_INT(var11);
                        if (var18 < 0) {
                            return -1;
                        }
                        break;
                    case 11:
                        long var12 = BA.bytes2long(var1, var20);
                        var20 += 8;
                        var20 += BA.alignWordSize(var20);
                        var18 = this.PUT_LONG(var12);
                        if (var18 < 0) {
                            return -1;
                        }
                        break;
                    case 12:
                        double var14 = BA.bytes2double(var1, var20);
                        var20 += 8;
                        var20 += BA.alignWordSize(var20);
                        var18 = this.PUT_DOUBLE(var14);
                        if (var18 < 0) {
                            return -1;
                        }
                        break;
                    case 30:
                        var10 = BA.bytes2int(var1, var20);
                        var20 += 4;
                        String var16;
                        if (var10 > 0) {
                            BA.bytes2str(var1, var20);
                            var16 = new String(var1, var20, var10, this.m_char_encoding);
                        } else {
                            var16 = "";
                        }

                        var20 += var10 + 1;
                        var20 += BA.alignWordSize(var20);
                        var18 = this.PUT_STR(var16);
                        if (var18 < 0) {
                            return -1;
                        }
                        break;
                    case 40:
                        var10 = BA.bytes2int(var1, var20);
                        var20 += 4;
                        byte[] var17;
                        if (var10 > 0) {
                            var17 = BA.bytes2bytes(var1, var20, var10);
                        } else {
                            var17 = null;
                        }

                        var20 += var10;
                        var20 += BA.alignWordSize(var20);
                        var18 = this.PUT_BIN(var17, var10);
                        if (var18 < 0) {
                            return -1;
                        }
                        break;
                    default:
                        this.m_msg = "[MIO Deserialize] Unknown parameter type (" + var19 + "/" + var7 + ") : " + var8;
                        return -1;
                }
            }

            this.m_curr_pos = 0;
            return 0;
        }
    }
}
