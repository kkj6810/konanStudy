package com.konantech.crx;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

class CrxParam {
    private ByteArrayOutputStream m_ba_request = null;
    private byte[] m_packed_request = null;
    private byte[] m_packed_response = null;
    private ArrayList m_req_param = null;
    private String m_request_name = null;
    private String m_request_family = null;
    private String m_request_version = null;
    private String m_response_name = null;
    private String m_response_family = null;
    private String m_response_version = null;
    private int m_response_param_count = 0;
    private CrxParamStruc[] m_response_param = null;
    private int m_response_offset = 0;
    private String m_msg = "";
    private String m_char_enc = null;

    private int InitMemberVariable() {
        this.m_request_name = null;
        this.m_request_family = null;
        this.m_request_version = null;
        this.m_response_name = null;
        this.m_response_family = null;
        this.m_response_version = null;
        this.m_response_param_count = 0;
        this.m_response_param = null;
        this.m_response_offset = 0;
        return 0;
    }

    public CrxParam() {
        this.m_req_param = new ArrayList();
        this.m_ba_request = new ByteArrayOutputStream();
        this.InitMemberVariable();
        this.m_char_enc = System.getProperty("file.encoding");
    }

    public int SetCharacterEncoding(String var1) {
        this.m_char_enc = var1;
        return 0;
    }

    public int ClearRequest() throws IOException {
        if (this.m_req_param != null) {
            this.m_req_param.clear();
        }

        if (this.m_ba_request != null) {
            this.m_ba_request.reset();
            this.m_ba_request.close();
        }

        return this.InitMemberVariable();
    }

    public int SetRequestName(String var1) {
        this.m_request_name = new String(var1);
        return 0;
    }

    public int SetRequestFamily(String var1) {
        this.m_request_family = new String(var1);
        return 0;
    }

    public int SetRequestVersion(String var1) {
        this.m_request_version = new String(var1);
        return 0;
    }

    private Object CopyParamValue1D(String var1, int var2, Object var3) {
        Object var4 = null;
        if (var1.compareTo("CHAR") != 0 && var1.compareTo("UCHAR") != 0) {
            if (var1.compareTo("BYTE") != 0 && var1.compareTo("INT8") != 0 && var1.compareTo("UINT8") != 0) {
                if (var1.compareTo("INT16") != 0 && var1.compareTo("UINT16") != 0) {
                    if (var1.compareTo("INT32") != 0 && var1.compareTo("UINT32") != 0) {
                        if (var1.compareTo("INT64") != 0 && var1.compareTo("UINT64") != 0) {
                            this.m_msg = "unknown param type " + var1 + " (C38643)";
                            var4 = null;
                        } else if (var3.getClass().getName().compareTo("java.lang.Long") == 0) {
                            Long var16 = (Long)var3;
                            var4 = var16;
                        } else {
                            if (var3.getClass().getName().compareTo("[L") != 0) {
                                this.m_msg = "Wrong Param Type : " + var1;
                                return null;
                            }

                            long[] var17 = new long[var2];
                            long[] var15 = (long[])((long[])var3);
                            System.arraycopy(var15, 0, var17, 0, var15.length);
                            var4 = var17;
                        }
                    } else if (var3.getClass().getName().compareTo("java.lang.Integer") == 0) {
                        Integer var12 = (Integer)var3;
                        var4 = var12;
                    } else {
                        if (var3.getClass().getName().compareTo("[I") != 0) {
                            this.m_msg = "Wrong Param Type : " + var1;
                            return null;
                        }

                        int[] var14 = new int[var2];
                        int[] var13 = (int[])((int[])var3);
                        System.arraycopy(var13, 0, var14, 0, var13.length);
                        var4 = var14;
                    }
                } else if (var3.getClass().getName().compareTo("java.lang.Short") == 0) {
                    Short var9 = (Short)var3;
                    var4 = var9;
                } else {
                    if (var3.getClass().getName().compareTo("[S") != 0) {
                        this.m_msg = "Wrong Param Type : " + var1;
                        return null;
                    }

                    short[] var10 = new short[var2];
                    short[] var11 = (short[])((short[])var3);
                    System.arraycopy(var11, 0, var10, 0, var11.length);
                    var4 = var10;
                }
            } else if (var3.getClass().getName().compareTo("java.lang.Byte") == 0) {
                Byte var7 = (Byte)var3;
                var4 = var7;
            } else {
                if (var3.getClass().getName().compareTo("[B") != 0) {
                    this.m_msg = "Wrong Param Type : " + var1;
                    return null;
                }

                byte[] var8 = new byte[var2];
                byte[] var6 = (byte[])((byte[])var3);
                System.arraycopy(var6, 0, var8, 0, var6.length);
                var4 = var8;
            }
        } else {
            if (var3.getClass().getName().compareTo("java.lang.String") != 0) {
                this.m_msg = "Wrong Param Type : " + var1;
                return null;
            }

            new String();
            String var5 = (String)var3;
            var4 = var5;
        }

        return var4;
    }

    private Object CopyParamValue2D(String var1, int var2, int[] var3, Object var4) {
        boolean var5 = false;
        Object[] var6 = new Object[var2];
        Object[] var7 = null;
        if (var6 == null && var2 > 0) {
            this.m_msg = "cannot allocate Object[] param_value";
            return null;
        } else {
            try {
                var7 = (Object[])((Object[])var4);

                for(int var10 = 0; var10 < var2; ++var10) {
                    var6[var10] = this.CopyParamValue1D(var1, var3[var10], var7[var10]);
                }

                return var6;
            } catch (ArrayIndexOutOfBoundsException var9) {
                this.m_msg = var9.toString();
                return null;
            }
        }
    }

    private Object CopyParamValue3D(String var1, int var2, int[] var3, int[][] var4, Object var5) {
        boolean var6 = false;
        Object[] var7 = new Object[var2];
        Object[] var8 = null;
        if (var7 == null && var2 > 0) {
            this.m_msg = "cannot allocate Object[] param_value";
            return null;
        } else {
            try {
                var8 = (Object[])((Object[])var5);

                for(int var11 = 0; var11 < var2; ++var11) {
                    var7[var11] = this.CopyParamValue2D(var1, var3[var11], var4[var11], var8[var11]);
                }

                return var7;
            } catch (ArrayIndexOutOfBoundsException var10) {
                this.m_msg = var10.toString();
                return null;
            }
        }
    }

    private Object CopyParamValue(String var1, int var2, int var3, int[] var4, int[][] var5, Object var6) {
        Object var7 = null;
        if (var2 == 1) {
            var7 = this.CopyParamValue1D(var1, var3, var6);
        } else if (var2 == 2) {
            var7 = this.CopyParamValue2D(var1, var3, var4, var6);
        } else if (var2 == 3) {
            var7 = this.CopyParamValue3D(var1, var3, var4, var5, var6);
        } else {
            this.m_msg = "unknown param dimension " + var2 + "(C38752)";
            var7 = null;
        }

        return var7;
    }

    private int AddParamStruc(String var1, String var2, int var3, int var4, int[] var5, int[][] var6, Object var7) {
        boolean var8 = false;
        boolean var9 = false;
        boolean var10 = false;
        CrxParamStruc var11 = null;
        if (var3 <= 3 && var3 >= 1) {
            var11 = new CrxParamStruc();
            if (var3 >= 3) {
                if (var4 < 1 || var5 == null || var6 == null) {
                    var11.m_2d_element_count = null;
                    var11.m_3d_element_count = (int[][])null;
                    this.m_msg = "cannot allocate to para_3d_element_count (C18764)";
                    return -1;
                }

                var11.m_3d_element_count = new int[var4][];

                for(int var12 = 0; var12 < var4; ++var12) {
                    var11.m_3d_element_count[var12] = new int[var5[var12]];
                }

                System.arraycopy(var6, 0, var11.m_3d_element_count, 0, var6.length);
            }

            if (var3 >= 2) {
                if (var4 < 1 || var5 == null) {
                    var11.m_2d_element_count = null;
                    this.m_msg = "cannot allocate to para_2d_element_count (C18764)";
                    return -1;
                }

                var11.m_2d_element_count = new int[var4];
                System.arraycopy(var5, 0, var11.m_2d_element_count, 0, var5.length);
            }

            var11.m_name = new String(var1);
            var11.m_type = new String(var2);
            var11.m_dimension = var3;
            var11.m_element_count = var4;
            var11.m_value = this.CopyParamValue(var2, var3, var4, var5, var6, var7);
            if (var11.m_value == null) {
                return -1;
            } else if (!this.m_req_param.add(var11)) {
                this.m_msg = "param structure add failed";
                return -1;
            } else {
                return 0;
            }
        } else {
            this.m_msg = "unknown param dimension " + var3 + " (C28765)";
            return -1;
        }
    }

    public int SetRequestParam(String var1, String var2, int var3, Object var4) {
        return this.AddParamStruc(var1, var2, 1, var3, (int[])null, (int[][])null, var4);
    }

    public int SetRequestParam2D(String var1, String var2, int var3, int[] var4, Object var5) {
        return this.AddParamStruc(var1, var2, 2, var3, var4, (int[][])null, var5);
    }

    public int SetRequestParam3D(String var1, String var2, int var3, int[] var4, int[][] var5, Object var6) {
        return this.AddParamStruc(var1, var2, 3, var3, var4, var5, var6);
    }

    private int ParamToBytes1D(String var1, int var2, Object var3) throws IOException {
        boolean var4 = false;
        boolean var5 = false;

        byte var9;
        try {
            if (var1.compareTo("CHAR") != 0 && var1.compareTo("UCHAR") != 0) {
                int var10;
                if (var1.compareTo("BYTE") != 0 && var1.compareTo("INT8") != 0 && var1.compareTo("UINT8") != 0) {
                    if (var1.compareTo("INT16") != 0 && var1.compareTo("UINT16") != 0) {
                        if (var1.compareTo("INT32") != 0 && var1.compareTo("UINT32") != 0) {
                            if (var1.compareTo("INT64") != 0 && var1.compareTo("UINT64") != 0) {
                                this.m_msg = "unknown param type " + var1 + " (C38643)";
                                var9 = -1;
                            } else if (var3.getClass().getName().compareTo("java.lang.Long") == 0) {
                                long var17 = (Long)var3;
                                CrxUtil.BA_writeLong(this.m_ba_request, var17);
                                var9 = 0;
                            } else if (var3.getClass().getName().compareTo("[L") == 0) {
                                long[] var18 = (long[])((long[])var3);

                                for(var10 = 0; var10 < var2; ++var10) {
                                    CrxUtil.BA_writeLong(this.m_ba_request, var18[var10]);
                                }

                                var9 = 0;
                            } else {
                                this.m_msg = "use only 'long' type";
                                var9 = -1;
                            }
                        } else if (var3.getClass().getName().compareTo("java.lang.Integer") == 0) {
                            int var15 = (Integer)var3;
                            CrxUtil.BA_writeInteger(this.m_ba_request, var15);
                            var9 = 0;
                        } else if (var3.getClass().getName().compareTo("[I") == 0) {
                            int[] var16 = (int[])((int[])var3);

                            for(var10 = 0; var10 < var2; ++var10) {
                                CrxUtil.BA_writeInteger(this.m_ba_request, var16[var10]);
                            }

                            var9 = 0;
                        } else {
                            this.m_msg = "use only 'int' type";
                            var9 = -1;
                        }
                    } else if (var3.getClass().getName().compareTo("java.lang.Short") == 0) {
                        short var13 = (Short)var3;
                        CrxUtil.BA_writeShort(this.m_ba_request, var13);
                        var9 = 0;
                    } else if (var3.getClass().getName().compareTo("[S") == 0) {
                        short[] var14 = (short[])((short[])var3);

                        for(var10 = 0; var10 < var2; ++var10) {
                            CrxUtil.BA_writeShort(this.m_ba_request, var14[var10]);
                        }

                        var9 = 0;
                    } else {
                        this.m_msg = "use only 'short' type";
                        var9 = -1;
                    }
                } else if (var3.getClass().getName().compareTo("java.lang.Byte") == 0) {
                    byte var11 = (Byte)var3;
                    CrxUtil.BA_writeByte(this.m_ba_request, var11);
                    var9 = 0;
                } else if (var3.getClass().getName().compareTo("[B") == 0) {
                    byte[] var12 = (byte[])((byte[])var3);

                    for(var10 = 0; var10 < var2; ++var10) {
                        CrxUtil.BA_writeByte(this.m_ba_request, var12[var10]);
                    }

                    var9 = 0;
                } else {
                    this.m_msg = "use only 'byte' type";
                    var9 = -1;
                }
            } else if (var3.getClass().getName().compareTo("java.lang.String") == 0) {
                String var6 = (String)var3;
                CrxUtil.BA_writeString(this.m_ba_request, var6, this.m_char_enc);
                var9 = 0;
            } else {
                this.m_msg = "use only 'String' class";
                var9 = -1;
            }
        } catch (ArrayIndexOutOfBoundsException var8) {
            this.m_msg = var8.toString();
            var9 = -1;
        }

        return var9;
    }

    private int ParamToBytes(CrxParamStruc var1) throws IOException {
        int var2 = 0;
        boolean var3 = false;
        boolean var4 = false;
        Object var5 = null;
        Object var6 = null;
        Object[] var7 = null;
        Object[] var8 = null;
        CrxUtil.BA_writeString(this.m_ba_request, var1.m_name, this.m_char_enc);
        CrxUtil.BA_writeString(this.m_ba_request, var1.m_type, this.m_char_enc);
        CrxUtil.BA_writeAlignSize(this.m_ba_request);
        CrxUtil.BA_writeInteger(this.m_ba_request, var1.m_dimension);
        CrxUtil.BA_writeInteger(this.m_ba_request, var1.m_element_count);
        int var11;
        if (var1.m_dimension >= 2) {
            for(var11 = 0; var11 < var1.m_element_count; ++var11) {
                CrxUtil.BA_writeInteger(this.m_ba_request, var1.m_2d_element_count[var11]);
            }
        }

        int var12;
        if (var1.m_dimension >= 3) {
            for(var11 = 0; var11 < var1.m_element_count; ++var11) {
                for(var12 = 0; var12 < var1.m_2d_element_count[var11]; ++var12) {
                    CrxUtil.BA_writeInteger(this.m_ba_request, var1.m_3d_element_count[var11][var12]);
                }
            }
        }

        try {
            if (var1.m_dimension == 1) {
                var6 = var1.m_value;
                var2 = this.ParamToBytes1D(var1.m_type, var1.m_element_count, var6);
                if (var2 != 0) {
                    return -1;
                }
            } else if (var1.m_dimension == 2) {
                var7 = (Object[])((Object[])var1.m_value);

                for(var11 = 0; var11 < var1.m_element_count; ++var11) {
                    var6 = var7[var11];
                    var2 = this.ParamToBytes1D(var1.m_type, var1.m_2d_element_count[var11], var6);
                    if (var2 != 0) {
                        return -1;
                    }
                }
            } else {
                if (var1.m_dimension != 3) {
                    this.m_msg = "unknown param dimension " + var1.m_dimension + " (C28745)";
                    return -1;
                }

                var8 = (Object[])((Object[])var1.m_value);

                for(var11 = 0; var11 < var1.m_element_count; ++var11) {
                    var7 = (Object[])((Object[])var8[var11]);

                    for(var12 = 0; var12 < var1.m_2d_element_count[var11]; ++var12) {
                        var6 = var7[var12];
                        var2 = this.ParamToBytes1D(var1.m_type, var1.m_3d_element_count[var11][var12], var6);
                        if (var2 != 0) {
                            return -1;
                        }
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException var10) {
            this.m_msg = var10.toString();
            return -1;
        }

        CrxUtil.BA_writeAlignSize(this.m_ba_request);
        return var2;
    }

    public byte[] GetPackedRequest() throws IOException {
        boolean var1 = false;
        boolean var2 = false;
        boolean var3 = false;
        CrxParamStruc var4 = null;
        Object var5 = null;
        if (this.m_request_name == null) {
            this.m_msg = "request name not specified (C14283)";
            return null;
        } else if (this.m_request_family == null) {
            this.m_msg = "request family not specified (C14284)";
            return null;
        } else {
            if (this.m_request_version == null) {
                this.m_request_version = "";
            }

            CrxUtil.BA_writeString(this.m_ba_request, this.m_request_name, this.m_char_enc);
            CrxUtil.BA_writeString(this.m_ba_request, this.m_request_family, this.m_char_enc);
            CrxUtil.BA_writeString(this.m_ba_request, this.m_request_version, this.m_char_enc);
            CrxUtil.BA_writeAlignSize(this.m_ba_request);
            int var8 = this.m_req_param.size();
            CrxUtil.BA_writeInteger(this.m_ba_request, var8);
            CrxUtil.BA_writeAlignSize(this.m_ba_request);

            for(int var7 = 0; var7 < var8; ++var7) {
                var4 = (CrxParamStruc)this.m_req_param.get(var7);
                int var6 = this.ParamToBytes(var4);
                if (var6 != 0) {
                    return null;
                }
            }

            this.m_packed_request = this.m_ba_request.toByteArray();
            return this.m_packed_request;
        }
    }

    private Object ResolveParamValue1D(String var1, int var2, byte[] var3) throws IOException {
        boolean var4 = false;
        Object var5 = null;
        if (var3.length - this.m_response_offset < var2) {
            this.m_msg = "inconsistent response size " + (var3.length - this.m_response_offset) + "," + var2 + " bytes (C61582)";
            return null;
        } else {
            if (var1.compareTo("CHAR") != 0 && var1.compareTo("UCHAR") != 0) {
                int var8;
                if (var1.compareTo("BYTE") != 0 && var1.compareTo("INT8") != 0 && var1.compareTo("UINT8") != 0) {
                    if (var1.compareTo("INT16") != 0 && var1.compareTo("UINT16") != 0) {
                        if (var1.compareTo("INT32") != 0 && var1.compareTo("UINT32") != 0) {
                            if (var1.compareTo("INT64") != 0 && var1.compareTo("UINT64") != 0) {
                                this.m_msg = "unknown param type " + var1 + " (C38643)";
                                var5 = null;
                            } else {
                                long[] var12 = new long[var2];

                                for(var8 = 0; var8 < var2; ++var8) {
                                    var12[var8] = CrxUtil.bytes2long(var3, this.m_response_offset);
                                    this.m_response_offset += 8;
                                }

                                var5 = var12;
                            }
                        } else {
                            int[] var11 = new int[var2];

                            for(var8 = 0; var8 < var2; ++var8) {
                                var11[var8] = CrxUtil.bytes2int(var3, this.m_response_offset);
                                this.m_response_offset += 4;
                            }

                            var5 = var11;
                        }
                    } else {
                        short[] var10 = new short[var2];

                        for(var8 = 0; var8 < var2; ++var8) {
                            var10[var8] = CrxUtil.bytes2short(var3, this.m_response_offset);
                            this.m_response_offset += 2;
                        }

                        var5 = var10;
                    }
                } else {
                    byte[] var9 = new byte[var2];

                    for(var8 = 0; var8 < var2; ++var8) {
                        var9[var8] = var3[this.m_response_offset];
                        ++this.m_response_offset;
                    }

                    var5 = var9;
                }
            } else {
                int var6 = CrxUtil.bytes2str(var3, this.m_response_offset);
                String var7 = new String(var3, this.m_response_offset, var6, this.m_char_enc);
                this.m_response_offset += var2 + 1;
                var5 = var7;
            }

            return var5;
        }
    }

    private Object ResolveParamValue2D(String var1, int var2, int[] var3, byte[] var4) throws IOException {
        boolean var5 = false;
        Object var6 = null;

        try {
            int var9;
            if (var1.compareTo("CHAR") != 0 && var1.compareTo("UCHAR") != 0) {
                if (var1.compareTo("BYTE") != 0 && var1.compareTo("INT8") != 0 && var1.compareTo("UINT8") != 0) {
                    if (var1.compareTo("INT16") != 0 && var1.compareTo("UINT16") != 0) {
                        if (var1.compareTo("INT32") != 0 && var1.compareTo("UINT32") != 0) {
                            if (var1.compareTo("INT64") != 0 && var1.compareTo("UINT64") != 0) {
                                this.m_msg = "unknown param type " + var1 + " (C38643)";
                                var6 = null;
                            } else {
                                long[][] var13 = new long[var2][];

                                for(var9 = 0; var9 < var2; ++var9) {
                                    var13[var9] = (long[])((long[])this.ResolveParamValue1D(var1, var3[var9], var4));
                                }

                                var6 = var13;
                            }
                        } else {
                            int[][] var12 = new int[var2][];

                            for(var9 = 0; var9 < var2; ++var9) {
                                var12[var9] = (int[])((int[])this.ResolveParamValue1D(var1, var3[var9], var4));
                            }

                            var6 = var12;
                        }
                    } else {
                        short[][] var11 = new short[var2][];

                        for(var9 = 0; var9 < var2; ++var9) {
                            var11[var9] = (short[])((short[])this.ResolveParamValue1D(var1, var3[var9], var4));
                        }

                        var6 = var11;
                    }
                } else {
                    byte[][] var10 = new byte[var2][];

                    for(var9 = 0; var9 < var2; ++var9) {
                        var10[var9] = (byte[])((byte[])this.ResolveParamValue1D(var1, var3[var9], var4));
                    }

                    var6 = var10;
                }
            } else {
                String[] var7 = new String[var2];

                for(var9 = 0; var9 < var2; ++var9) {
                    var7[var9] = (String)this.ResolveParamValue1D(var1, var3[var9], var4);
                }

                var6 = var7;
            }
        } catch (ArrayIndexOutOfBoundsException var8) {
            this.m_msg = var8.toString();
            return null;
        }

        if (var6 == null && var2 > 0) {
            this.m_msg = "cannot allocate Object param_value";
            return null;
        } else {
            return var6;
        }
    }

    private Object ResolveParamValue3D(String var1, int var2, int[] var3, int[][] var4, byte[] var5) throws IOException {
        boolean var6 = false;
        Object var7 = null;

        try {
            int var10;
            if (var1.compareTo("CHAR") != 0 && var1.compareTo("UCHAR") != 0) {
                if (var1.compareTo("BYTE") != 0 && var1.compareTo("INT8") != 0 && var1.compareTo("UINT8") != 0) {
                    if (var1.compareTo("INT16") != 0 && var1.compareTo("UINT16") != 0) {
                        if (var1.compareTo("INT32") != 0 && var1.compareTo("UINT32") != 0) {
                            if (var1.compareTo("INT64") != 0 && var1.compareTo("UINT64") != 0) {
                                this.m_msg = "unknown param type " + var1 + " (C38643)";
                                var7 = null;
                            } else {
                                long[][][] var14 = new long[var2][][];

                                for(var10 = 0; var10 < var2; ++var10) {
                                    var14[var10] = (long[][])((long[][])this.ResolveParamValue2D(var1, var3[var10], var4[var10], var5));
                                }

                                var7 = var14;
                            }
                        } else {
                            int[][][] var13 = new int[var2][][];

                            for(var10 = 0; var10 < var2; ++var10) {
                                var13[var10] = (int[][])((int[][])this.ResolveParamValue2D(var1, var3[var10], var4[var10], var5));
                            }

                            var7 = var13;
                        }
                    } else {
                        short[][][] var12 = new short[var2][][];

                        for(var10 = 0; var10 < var2; ++var10) {
                            var12[var10] = (short[][])((short[][])this.ResolveParamValue2D(var1, var3[var10], var4[var10], var5));
                        }

                        var7 = var12;
                    }
                } else {
                    byte[][][] var11 = new byte[var2][][];

                    for(var10 = 0; var10 < var2; ++var10) {
                        var11[var10] = (byte[][])((byte[][])this.ResolveParamValue2D(var1, var3[var10], var4[var10], var5));
                    }

                    var7 = var11;
                }
            } else {
                String[][] var8 = new String[var2][];

                for(var10 = 0; var10 < var2; ++var10) {
                    var8[var10] = (String[])((String[])this.ResolveParamValue2D(var1, var3[var10], var4[var10], var5));
                }

                var7 = var8;
            }
        } catch (ArrayIndexOutOfBoundsException var9) {
            this.m_msg = var9.toString();
            return null;
        }

        if (var7 == null && var2 > 0) {
            this.m_msg = "cannot allocate Object param_value (c24838)";
            return null;
        } else {
            return var7;
        }
    }

    private Object ResolveParamValue(String var1, int var2, int var3, int[] var4, int[][] var5, byte[] var6) throws IOException {
        Object var7 = null;
        if (var2 == 1) {
            var7 = this.ResolveParamValue1D(var1, var3, var6);
        } else if (var2 == 2) {
            var7 = this.ResolveParamValue2D(var1, var3, var4, var6);
        } else if (var2 == 3) {
            var7 = this.ResolveParamValue3D(var1, var3, var4, var5, var6);
        } else {
            this.m_msg = "unknown param dimension " + var2 + "(C28746)";
            var7 = null;
        }

        return var7;
    }

    private CrxParamStruc GetParamStruc(byte[] var1) throws IOException {
        boolean var2 = false;
        boolean var3 = false;
        boolean var4 = false;
        boolean var6 = false;
        boolean var7 = false;
        CrxParamStruc var8 = new CrxParamStruc();
        if (var8 == null) {
            this.m_msg = "cannot allocate CrxParamStruc param (C416831)";
            return null;
        } else {
            int var11 = CrxUtil.bytes2str(var1, this.m_response_offset);
            var8.m_name = new String(var1, this.m_response_offset, var11, this.m_char_enc);
            this.m_response_offset += var11 + 1;
            var11 = CrxUtil.bytes2str(var1, this.m_response_offset);
            var8.m_type = new String(var1, this.m_response_offset, var11, this.m_char_enc);
            this.m_response_offset += var11 + 1;
            this.m_response_offset += CrxUtil.alignWordSize(this.m_response_offset);
            var8.m_dimension = CrxUtil.bytes2int(var1, this.m_response_offset);
            this.m_response_offset += 4;
            var8.m_element_count = CrxUtil.bytes2int(var1, this.m_response_offset);
            this.m_response_offset += 4;
            var8.m_2d_element_count = null;
            int var9;
            if (var8.m_dimension >= 2) {
                var8.m_2d_element_count = new int[var8.m_element_count];
                if (var8.m_2d_element_count == null && var8.m_element_count > 0) {
                    this.m_msg = "cannot allocate to param.m_2d_element_count (C416832)";
                    return null;
                }

                for(var9 = 0; var9 < var8.m_element_count; ++var9) {
                    var8.m_2d_element_count[var9] = CrxUtil.bytes2int(var1, this.m_response_offset);
                    this.m_response_offset += 4;
                }
            }

            var8.m_3d_element_count = (int[][])null;
            if (var8.m_dimension >= 3) {
                var8.m_3d_element_count = new int[var8.m_element_count][];
                if (var8.m_3d_element_count == null && var8.m_element_count > 0) {
                    this.m_msg = "cannot allocate to param.m_3d_element_count (C416833)";
                    return null;
                }

                for(var9 = 0; var9 < var8.m_element_count; ++var9) {
                    var8.m_3d_element_count[var9] = new int[var8.m_2d_element_count[var9]];
                    if (var8.m_3d_element_count[var9] == null && var8.m_2d_element_count[var9] > 0) {
                        this.m_msg = "cannot allocate to param.m_3d_element_count (C416834)";
                        return null;
                    }

                    for(int var10 = 0; var10 < var8.m_2d_element_count[var9]; ++var10) {
                        var8.m_3d_element_count[var9][var10] = CrxUtil.bytes2int(var1, this.m_response_offset);
                        this.m_response_offset += 4;
                    }
                }
            }

            var8.m_value = this.ResolveParamValue(var8.m_type, var8.m_dimension, var8.m_element_count, var8.m_2d_element_count, var8.m_3d_element_count, var1);
            if (var8.m_value == null) {
                return null;
            } else {
                this.m_response_offset += CrxUtil.alignWordSize(this.m_response_offset);
                return var8;
            }
        }
    }

    public int SetPackedResponse(byte[] var1) throws IOException {
        byte var2 = 0;
        boolean var3 = false;
        boolean var5 = false;
        CrxParamStruc[] var6 = null;
        this.ClearRequest();
        this.m_response_offset = 0;
        int var8 = CrxUtil.bytes2str(var1, this.m_response_offset);
        this.m_response_name = new String(var1, this.m_response_offset, var8, this.m_char_enc);
        this.m_response_offset += var8 + 1;
        var8 = CrxUtil.bytes2str(var1, this.m_response_offset);
        this.m_response_family = new String(var1, this.m_response_offset, var8, this.m_char_enc);
        this.m_response_offset += var8 + 1;
        var8 = CrxUtil.bytes2str(var1, this.m_response_offset);
        this.m_response_version = new String(var1, this.m_response_offset, var8, this.m_char_enc);
        this.m_response_offset += var8 + 1;
        this.m_response_offset += CrxUtil.alignWordSize(this.m_response_offset);
        int var4 = CrxUtil.bytes2int(var1, this.m_response_offset);
        this.m_response_offset += 4;
        this.m_response_offset += CrxUtil.alignWordSize(this.m_response_offset);
        if (var4 > 0) {
            var6 = new CrxParamStruc[var4];
        }

        if (var6 == null && var4 > 0) {
            this.m_msg = "cannot allocate to m_response_param (C61582)";
            return -1;
        } else {
            for(int var7 = 0; var7 < var4; ++var7) {
                var6[var7] = this.GetParamStruc(var1);
                if (var6[var7] == null) {
                    System.out.println("Main SetPackedResponse : param[i] : ");
                    return -1;
                }
            }

            if (var1.length != this.m_response_offset) {
                this.m_msg = "inconsistent response size " + var1.length + "," + this.m_response_offset + " bytes (C61583)";
                return -1;
            } else {
                this.m_response_param_count = var4;
                this.m_response_param = var6;
                return var2;
            }
        }
    }

    public String GetResponseName() {
        return this.m_response_name;
    }

    public String GetResponseFamily() {
        return this.m_response_family;
    }

    public String GetResponseVersion() {
        return this.m_response_version;
    }

    public int GetResponseParamCount() {
        return this.m_response_param_count;
    }

    private int CHECK_PARAM_NO(int var1, int var2) {
        if (var1 >= 0 && var1 < var2) {
            return 0;
        } else {
            this.m_msg = "param_no out of range " + var1 + "," + var2 + " (C72472)";
            return -1;
        }
    }

    public String GetResponseParamName(int var1) {
        return this.CHECK_PARAM_NO(var1, this.m_response_param_count) == -1 ? null : this.m_response_param[var1].m_name;
    }

    public String GetResponseParamType(int var1) {
        return this.CHECK_PARAM_NO(var1, this.m_response_param_count) == -1 ? null : this.m_response_param[var1].m_type;
    }

    public int GetResponseParamValueDimension(int var1) {
        return this.CHECK_PARAM_NO(var1, this.m_response_param_count) == -1 ? -1 : this.m_response_param[var1].m_dimension;
    }

    public int GetResponseParamElementCount(int var1) {
        return this.CHECK_PARAM_NO(var1, this.m_response_param_count) == -1 ? -1 : this.m_response_param[var1].m_element_count;
    }

    public int[] GetResponseParamElementCount2D(int var1) {
        return this.CHECK_PARAM_NO(var1, this.m_response_param_count) == -1 ? null : this.m_response_param[var1].m_2d_element_count;
    }

    public int[][] GetResponseParamElementCount3D(int var1) {
        return this.CHECK_PARAM_NO(var1, this.m_response_param_count) == -1 ? (int[][])null : this.m_response_param[var1].m_3d_element_count;
    }

    public Object GetResponseParamValue(int var1) {
        return this.CHECK_PARAM_NO(var1, this.m_response_param_count) == -1 ? null : this.m_response_param[var1].m_value;
    }

    public int GetResponseParamNoByName(String var1) {
        boolean var2 = false;
        int var3 = -1;
        int var4 = 0;

        for(int var5 = 0; var5 < this.m_response_param_count; ++var5) {
            if (var1.compareTo(this.m_response_param[var5].m_name) == 0) {
                var3 = var5;
                ++var4;
            }
        }

        if (var4 == 0) {
            this.m_msg = "no param name exists. ('" + var1 + ")";
            return -1;
        } else if (var4 > 1) {
            this.m_msg = var4 + "param name exists.";
            return -1;
        } else {
            return var3;
        }
    }

    public void PrintResponse(PrintStream var1, int var2) {
        boolean var3 = false;
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        String var7 = this.GetResponseName();
        byte var18;
        if (var7 == null) {
            var18 = -1;
        } else {
            var1.println("+--------------------------------------------------------------------------+");
            var1.println("| CRX response name        = '" + var7 + "'");
            String var8 = this.GetResponseFamily();
            if (var8 == null) {
                var18 = -1;
            } else {
                var1.println("| CRX response family      = '" + var8 + "'");
                int var9 = this.GetResponseParamCount();
                if (var9 < 0) {
                    var18 = -1;
                } else {
                    var1.println("| CRX response param count = '" + var9 + "'");
                    var1.println("+--------------------------------------------------------------------------+");
                    int var19 = 0;

                    while(true) {
                        if (var19 >= var9) {
                            var18 = 0;
                            break;
                        }

                        String var10 = this.GetResponseParamName(var19);
                        if (var10 == null) {
                            var18 = -1;
                            break;
                        }

                        String var11 = this.GetResponseParamType(var19);
                        if (var11 == null) {
                            var18 = -1;
                            break;
                        }

                        int var12 = this.GetResponseParamValueDimension(var19);
                        if (var12 < 1) {
                            var18 = -1;
                            break;
                        }

                        int var13 = this.GetResponseParamElementCount(var19);
                        if (var13 < 0) {
                            var18 = -1;
                            break;
                        }

                        var1.println("[" + var19 + "] param : name='" + var10 + "', type='" + var11 + "', dimension=" + var12 + ", element_count=" + var13);
                        this.GetResponseParamValue(var19);
                        int var20;
                        if (var12 == 1) {
                            if (var11.compareTo("INT8") != 0 && var11.compareTo("UINT8") != 0) {
                                if (var11.compareTo("INT16") != 0 && var11.compareTo("UINT16") != 0) {
                                    if (var11.compareTo("INT32") != 0 && var11.compareTo("UINT32") != 0) {
                                        if (var11.compareTo("INT64") != 0 && var11.compareTo("UINT64") != 0) {
                                            if (var11.compareTo("CHAR") != 0 && var11.compareTo("CHAR") != 0) {
                                                var1.println("unknown param type " + var11 + " (C38642)");
                                                var18 = -1;
                                                break;
                                            }

                                            String var25 = (String)this.GetResponseParamValue(var19);
                                            var1.println(var25);
                                        } else {
                                            long[] var24 = (long[])((long[])this.GetResponseParamValue(var19));
                                            var1.print("\t");

                                            for(var20 = 0; var20 < var13 && var20 < var2; ++var20) {
                                                var1.print(var24[var20] + " ");
                                            }

                                            var1.println();
                                        }
                                    } else {
                                        int[] var23 = (int[])((int[])this.GetResponseParamValue(var19));
                                        var1.print("\t");

                                        for(var20 = 0; var20 < var13 && var20 < var2; ++var20) {
                                            var1.print(var23[var20] + " ");
                                        }

                                        var1.println();
                                    }
                                } else {
                                    short[] var22 = (short[])((short[])this.GetResponseParamValue(var19));
                                    var1.print("\t");

                                    for(var20 = 0; var20 < var13 && var20 < var2; ++var20) {
                                        var1.print(var22[var20] + " ");
                                    }

                                    var1.println();
                                }
                            } else {
                                byte[] var17 = (byte[])((byte[])this.GetResponseParamValue(var19));
                                var1.print("\t");

                                for(var20 = 0; var20 < var13 && var20 < var2; ++var20) {
                                    var1.print(var17[var20] + " ");
                                }

                                var1.println();
                            }
                        } else {
                            int[] var14;
                            int var21;
                            if (var12 == 2) {
                                var14 = this.GetResponseParamElementCount2D(var19);
                                if (var14 == null) {
                                    var18 = -1;
                                    break;
                                }

                                if (var11.compareTo("INT8") != 0 && var11.compareTo("UINT8") != 0) {
                                    if (var11.compareTo("INT16") == 0 || var11.compareTo("UINT16") == 0) {
                                        short[][] var30 = (short[][])((short[][])this.GetResponseParamValue(var19));

                                        for(var20 = 0; var20 < var13 && var20 < var2; ++var20) {
                                            var1.print("\t");

                                            for(var21 = 0; var21 < var14[var20] && var21 < var2; ++var21) {
                                                var1.print(var30[var20][var21] + " ");
                                            }

                                            var1.println();
                                        }
                                    } else if (var11.compareTo("INT32") == 0 || var11.compareTo("UINT32") == 0) {
                                        int[][] var29 = (int[][])((int[][])this.GetResponseParamValue(var19));

                                        for(var20 = 0; var20 < var13 && var20 < var2; ++var20) {
                                            var1.print("\t");

                                            for(var21 = 0; var21 < var14[var20] && var21 < var2; ++var21) {
                                                var1.print(var29[var20][var21] + " ");
                                            }

                                            var1.println();
                                        }
                                    } else if (var11.compareTo("INT64") == 0 || var11.compareTo("UINT64") == 0) {
                                        long[][] var28 = (long[][])((long[][])this.GetResponseParamValue(var19));

                                        for(var20 = 0; var20 < var13 && var20 < var2; ++var20) {
                                            var1.print("\t");

                                            for(var21 = 0; var21 < var14[var20] && var21 < var2; ++var21) {
                                                var1.print(var28[var20][var21] + " ");
                                            }

                                            var1.println();
                                        }
                                    } else {
                                        if (var11.compareTo("CHAR") != 0 && var11.compareTo("CHAR") != 0) {
                                            var1.println("unknown param type " + var11 + " (C38642)");
                                            var18 = -1;
                                            break;
                                        }

                                        String[] var27 = (String[])((String[])this.GetResponseParamValue(var19));

                                        for(var20 = 0; var20 < var13 && var20 < var2; ++var20) {
                                            var1.println(var27[var20]);
                                        }
                                    }
                                } else {
                                    byte[][] var26 = (byte[][])((byte[][])this.GetResponseParamValue(var19));

                                    for(var20 = 0; var20 < var13 && var20 < var2; ++var20) {
                                        var1.print("\t");

                                        for(var21 = 0; var21 < var14[var20] && var21 < var2; ++var21) {
                                            var1.print(var26[var20][var21] + " ");
                                        }

                                        var1.println();
                                    }
                                }
                            } else if (var12 == 3) {
                                var14 = this.GetResponseParamElementCount2D(var19);
                                if (var14 == null) {
                                    var18 = -1;
                                    break;
                                }

                                int[][] var15 = this.GetResponseParamElementCount3D(var19);
                                if (var15 == null) {
                                    var18 = -1;
                                    break;
                                }

                                if (var11.compareTo("CHAR") != 0 && var11.compareTo("UCHAR") != 0) {
                                    var1.println("unknown param type " + var11 + " (C38642)");
                                    var18 = -1;
                                    break;
                                }

                                String[][] var31 = (String[][])((String[][])this.GetResponseParamValue(var19));

                                for(var20 = 0; var20 < var13 && var20 < var2; ++var20) {
                                    for(var21 = 0; var21 < var14[var20] && var21 < var2; ++var21) {
                                        if (var31[var20][var21].length() < 20) {
                                            var1.print(var31[var20][var21] + " ");
                                        } else {
                                            var1.print(var31[var20][var21].substring(0, 20) + " ");
                                        }
                                    }

                                    var1.println();
                                }
                            } else {
                                var1.println("unknown param dimension " + var12 + " (C38643)");
                            }
                        }

                        var1.println("+--------------------------------------------------------------------------+");
                        ++var19;
                    }
                }
            }
        }

        if (var18 == -1) {
            var1.println("Error : " + this.m_msg);
        }

    }

    public String getMessage() {
        return this.m_msg;
    }
}
