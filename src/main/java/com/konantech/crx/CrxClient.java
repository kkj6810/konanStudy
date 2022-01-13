package com.konantech.crx;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

public class CrxClient {
    private Vector vCrx = new Vector();

    public CrxClient() {
    }

    public String getLibraryVersion() {
        return null;
    }

    public long connect(String var1, String var2, String var3, String var4) {
        int var5 = this.vCrx.size();
        this.vCrx.add(new CrxMain());
        CrxMain var6 = (CrxMain)this.vCrx.get(var5);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1L;
        } else {
            int var7 = var6.Connect(var1, var2, var3, var4);
            if (var7 != 0) {
                System.err.println(var6.GetMessage());
                return (long)var7;
            } else {
                return (long)var5;
            }
        }
    }

    public int disconnect(long var1) {
        CrxMain var3 = (CrxMain)this.vCrx.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.Disconnect();
        }
    }

    public int setOption(long var1, String var3, String var4) {
        CrxMain var5 = (CrxMain)this.vCrx.get((int)var1);
        if (var5 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var5.SetOption(var3, var4);
        }
    }

    public int setCharacterEncoding(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var4.SetCharacterEncoding(var3);
        }
    }

    public int clearOption(long var1) {
        CrxMain var3 = (CrxMain)this.vCrx.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.ClearOption();
        }
    }

    public int putRequest(long var1, String var3) throws IOException {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var4.PutRequest(var3);
        }
    }

    public String getResponse(long var1) throws IOException {
        CrxMain var3 = (CrxMain)this.vCrx.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            return var3.GetResponse();
        }
    }

    public int clearRequest(long var1) throws IOException {
        CrxMain var3 = (CrxMain)this.vCrx.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.ClearRequest();
        }
    }

    public int putRequestName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var4.PutRequestName(var3);
        }
    }

    public int putRequestFamily(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var4.PutRequestFamily(var3);
        }
    }

    public int putRequestVersion(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var4.PutRequestVersion(var3);
        }
    }

    public int putRequestParamByte(long var1, String var3, String var4, byte var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            byte[] var7 = new byte[]{var5};
            return var6.PutRequestParam(var3, var4, 1, var7);
        }
    }

    public int putRequestParamShort(long var1, String var3, String var4, short var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            short[] var7 = new short[]{var5};
            return var6.PutRequestParam(var3, var4, 1, var7);
        }
    }

    public int putRequestParamInt(long var1, String var3, String var4, int var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            int[] var7 = new int[]{var5};
            return var6.PutRequestParam(var3, var4, 1, var7);
        }
    }

    public int putRequestParamLong(long var1, String var3, String var4, long var5) {
        CrxMain var7 = (CrxMain)this.vCrx.get((int)var1);
        if (var7 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            long[] var8 = new long[]{var5};
            return var7.PutRequestParam(var3, var4, 1, var8);
        }
    }

    public int putRequestParamString(long var1, String var3, String var4, String var5) throws UnsupportedEncodingException {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var6.PutRequestParam(var3, var4, var5.getBytes(var6.GetCharacterEncoding()).length, var5);
        }
    }

    public int putRequestParamByteArray(long var1, String var3, String var4, byte[] var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var6.PutRequestParam(var3, var4, var5.length, var5);
        }
    }

    public int putRequestParamShortArray(long var1, String var3, String var4, short[] var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var6.PutRequestParam(var3, var4, var5.length, var5);
        }
    }

    public int putRequestParamIntArray(long var1, String var3, String var4, int[] var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var6.PutRequestParam(var3, var4, var5.length, var5);
        }
    }

    public int putRequestParamLongArray(long var1, String var3, String var4, long[] var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var6.PutRequestParam(var3, var4, var5.length, var5);
        }
    }

    public int putRequestParamStringArray(long var1, String var3, String var4, String[] var5) throws UnsupportedEncodingException {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            boolean var7 = false;
            int var8 = var5.length;
            int[] var9 = new int[var8];

            for(int var10 = 0; var10 < var8; ++var10) {
                var9[var10] = var5[var10].getBytes(var6.GetCharacterEncoding()).length;
            }

            return var6.PutRequestParam2D(var3, var4, var8, var9, var5);
        }
    }

    public int putRequestParamByteArray2D(long var1, String var3, String var4, byte[][] var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            boolean var7 = false;
            int var8 = var5.length;
            int[] var9 = new int[var8];

            for(int var10 = 0; var10 < var8; ++var10) {
                var9[var10] = var5[var10].length;
            }

            return var6.PutRequestParam2D(var3, var4, var8, var9, var5);
        }
    }

    public int putRequestParamShortArray2D(long var1, String var3, String var4, short[][] var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            boolean var7 = false;
            int var8 = var5.length;
            int[] var9 = new int[var8];

            for(int var10 = 0; var10 < var8; ++var10) {
                var9[var10] = var5[var10].length;
            }

            return var6.PutRequestParam2D(var3, var4, var8, var9, var5);
        }
    }

    public int putRequestParamIntArray2D(long var1, String var3, String var4, int[][] var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            boolean var7 = false;
            int var8 = var5.length;
            int[] var9 = new int[var8];

            for(int var10 = 0; var10 < var8; ++var10) {
                var9[var10] = var5[var10].length;
            }

            return var6.PutRequestParam2D(var3, var4, var8, var9, var5);
        }
    }

    public int putRequestParamLongArray2D(long var1, String var3, String var4, long[][] var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            boolean var7 = false;
            int var8 = var5.length;
            int[] var9 = new int[var8];

            for(int var10 = 0; var10 < var8; ++var10) {
                var9[var10] = var5[var10].length;
            }

            return var6.PutRequestParam2D(var3, var4, var8, var9, var5);
        }
    }

    public int putRequestParamStringArray2D(long var1, String var3, String var4, String[][] var5) throws UnsupportedEncodingException {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            boolean var7 = false;
            boolean var8 = false;
            int var9 = var5.length;
            int[] var10 = new int[var9];
            int[][] var11 = new int[var9][];

            for(int var12 = 0; var12 < var9; ++var12) {
                var10[var12] = var5[var12].length;
                var11[var12] = new int[var10[var12]];

                for(int var13 = 0; var13 < var9; ++var13) {
                    var11[var12][var13] = var5[var12][var13].getBytes(var6.GetCharacterEncoding()).length;
                }
            }

            return var6.PutRequestParam3D(var3, var4, var9, var10, var11, var5);
        }
    }

    public int putRequestParamByteArray3D(long var1, String var3, String var4, byte[][][] var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            boolean var7 = false;
            boolean var8 = false;
            int var9 = var5.length;
            int[] var10 = new int[var9];
            int[][] var11 = new int[var9][];

            for(int var12 = 0; var12 < var9; ++var12) {
                var10[var12] = var5[var12].length;
                var11[var12] = new int[var10[var12]];

                for(int var13 = 0; var13 < var9; ++var13) {
                    var11[var12][var13] = var5[var12][var13].length;
                }
            }

            return var6.PutRequestParam3D(var3, var4, var9, var10, var11, var5);
        }
    }

    public int putRequestParamShortArray3D(long var1, String var3, String var4, short[][][] var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            boolean var7 = false;
            boolean var8 = false;
            int var9 = var5.length;
            int[] var10 = new int[var9];
            int[][] var11 = new int[var9][];

            for(int var12 = 0; var12 < var9; ++var12) {
                var10[var12] = var5[var12].length;
                var11[var12] = new int[var10[var12]];

                for(int var13 = 0; var13 < var9; ++var13) {
                    var11[var12][var13] = var5[var12][var13].length;
                }
            }

            return var6.PutRequestParam3D(var3, var4, var9, var10, var11, var5);
        }
    }

    public int putRequestParamIntArray3D(long var1, String var3, String var4, int[][][] var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            boolean var7 = false;
            boolean var8 = false;
            int var9 = var5.length;
            int[] var10 = new int[var9];
            int[][] var11 = new int[var9][];

            for(int var12 = 0; var12 < var9; ++var12) {
                var10[var12] = var5[var12].length;
                var11[var12] = new int[var10[var12]];

                for(int var13 = 0; var13 < var9; ++var13) {
                    var11[var12][var13] = var5[var12][var13].length;
                }
            }

            return var6.PutRequestParam3D(var3, var4, var9, var10, var11, var5);
        }
    }

    public int putRequestParamLongArray3D(long var1, String var3, String var4, long[][][] var5) {
        CrxMain var6 = (CrxMain)this.vCrx.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            boolean var7 = false;
            boolean var8 = false;
            int var9 = var5.length;
            int[] var10 = new int[var9];
            int[][] var11 = new int[var9][];

            for(int var12 = 0; var12 < var9; ++var12) {
                var10[var12] = var5[var12].length;
                var11[var12] = new int[var10[var12]];

                for(int var13 = 0; var13 < var9; ++var13) {
                    var11[var12][var13] = var5[var12][var13].length;
                }
            }

            return var6.PutRequestParam3D(var3, var4, var9, var10, var11, var5);
        }
    }

    public int submitRequest(long var1) throws IOException {
        CrxMain var3 = (CrxMain)this.vCrx.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.SubmitRequest();
        }
    }

    public int receiveResponse(long var1) throws IOException {
        CrxMain var3 = (CrxMain)this.vCrx.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.ReceiveResponse();
        }
    }

    public String getWorkerAddress(long var1) {
        CrxMain var3 = (CrxMain)this.vCrx.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            return var3.GetWorkerAddress();
        }
    }

    public int getWorkerTime(long var1) {
        CrxMain var3 = (CrxMain)this.vCrx.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.GetWorkerTime();
        }
    }

    public int getResponseTime(long var1) {
        CrxMain var3 = (CrxMain)this.vCrx.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.GetReponseTime();
        }
    }

    public String getResponseName(long var1) {
        CrxMain var3 = (CrxMain)this.vCrx.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            return var3.GetResponseName();
        }
    }

    public String getResponseFamily(long var1) {
        CrxMain var3 = (CrxMain)this.vCrx.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            return var3.GetResponseFamily();
        }
    }

    public String getResponseVersion(long var1) {
        CrxMain var3 = (CrxMain)this.vCrx.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            return var3.GetResponseVersion();
        }
    }

    public int getResponseParamCount(long var1) {
        CrxMain var3 = (CrxMain)this.vCrx.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.GetResponseParamCount();
        }
    }

    public String getResponseParamName(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            return var4.GetResponseParamName(var3);
        }
    }

    public String getResponseParamType(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            return var4.GetResponseParamType(var3);
        }
    }

    public int getResponseParamValueDimension(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var4.GetResponseParamValueDimension(var3);
        }
    }

    public int getResponseParamElementCount(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var4.GetResponseParamElementCount(var3);
        }
    }

    public int[] getResponseParamElementCount2D(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            return var4.GetResponseParamElementCount2D(var3);
        }
    }

    public int[][] getResponseParamElementCount3D(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (int[][])null;
        } else {
            return var4.GetResponseParamElementCount3D(var3);
        }
    }

    public Object getResponseParamValue(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            return var4.GetResponseParamValue(var3);
        }
    }

    public byte getResponseParamValueByte(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else if ((var4.GetResponseParamType(var3).compareTo("BYTE") == 0 || var4.GetResponseParamType(var3).compareTo("INT8") == 0 || var4.GetResponseParamType(var3).compareTo("UINT8") == 0) && var4.GetResponseParamValueDimension(var3) == 1 && var4.GetResponseParamElementCount(var3) == 1) {
            byte[] var5 = (byte[])((byte[])var4.GetResponseParamValue(var3));
            if (var5.length != 1) {
                throw new ClassCastException("Wrong ResponseParamValue Type :" + var4.GetResponseParamType(var3));
            } else {
                return var5[0];
            }
        } else {
            throw new ClassCastException("Wrong ResponseParamValue Type : " + var4.GetResponseParamType(var3));
        }
    }

    public short getResponseParamValueShort(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else if ((var4.GetResponseParamType(var3).compareTo("INT16") == 0 || var4.GetResponseParamType(var3).compareTo("UINT16") == 0) && var4.GetResponseParamValueDimension(var3) == 1 && var4.GetResponseParamElementCount(var3) == 1) {
            short[] var5 = (short[])((short[])var4.GetResponseParamValue(var3));
            if (var5.length != 1) {
                throw new ClassCastException("Wrong ResponseParamValue Type : " + var4.GetResponseParamType(var3));
            } else {
                return var5[0];
            }
        } else {
            throw new ClassCastException("Wrong ResponseParamValue Type : " + var4.GetResponseParamType(var3));
        }
    }

    public int getResponseParamValueInt(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else if ((var4.GetResponseParamType(var3).compareTo("INT32") == 0 || var4.GetResponseParamType(var3).compareTo("UINT32") == 0) && var4.GetResponseParamValueDimension(var3) == 1 && var4.GetResponseParamElementCount(var3) == 1) {
            int[] var5 = (int[])((int[])var4.GetResponseParamValue(var3));
            if (var5.length != 1) {
                throw new ClassCastException("Wrong ResponseParamValue Type : " + var4.GetResponseParamType(var3));
            } else {
                return var5[0];
            }
        } else {
            throw new ClassCastException("Wrong ResponseParamValue Type : " + var4.GetResponseParamType(var3));
        }
    }

    public long getResponseParamValueLong(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1L;
        } else if ((var4.GetResponseParamType(var3).compareTo("INT64") == 0 || var4.GetResponseParamType(var3).compareTo("UINT64") == 0) && var4.GetResponseParamValueDimension(var3) == 1 && var4.GetResponseParamElementCount(var3) == 1) {
            long[] var5 = (long[])((long[])var4.GetResponseParamValue(var3));
            if (var5.length != 1) {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
            } else {
                return var5[0];
            }
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public String getResponseParamValueString(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else if ((var4.GetResponseParamType(var3).compareTo("CHAR") == 0 || var4.GetResponseParamType(var3).compareTo("UCHAR") == 0) && var4.GetResponseParamValueDimension(var3) == 1) {
            String var5 = (String)var4.GetResponseParamValue(var3);
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public byte[] getResponseParamValueByteArray(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else if ((var4.GetResponseParamType(var3).compareTo("BYTE") == 0 || var4.GetResponseParamType(var3).compareTo("INT8") == 0 || var4.GetResponseParamType(var3).compareTo("UINT8") == 0) && var4.GetResponseParamValueDimension(var3) == 1) {
            byte[] var5 = (byte[])((byte[])var4.GetResponseParamValue(var3));
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public short[] getResponseParamValueShortArray(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else if ((var4.GetResponseParamType(var3).compareTo("INT16") == 0 || var4.GetResponseParamType(var3).compareTo("UINT16") == 0) && var4.GetResponseParamValueDimension(var3) == 1) {
            short[] var5 = (short[])((short[])var4.GetResponseParamValue(var3));
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public int[] getResponseParamValueIntArray(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else if ((var4.GetResponseParamType(var3).compareTo("INT32") == 0 || var4.GetResponseParamType(var3).compareTo("UINT32") == 0) && var4.GetResponseParamValueDimension(var3) == 1) {
            int[] var5 = (int[])((int[])var4.GetResponseParamValue(var3));
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public long[] getResponseParamValueLongArray(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else if ((var4.GetResponseParamType(var3).compareTo("INT64") == 0 || var4.GetResponseParamType(var3).compareTo("UINT64") == 0) && var4.GetResponseParamValueDimension(var3) == 1) {
            long[] var5 = (long[])((long[])var4.GetResponseParamValue(var3));
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public String[] getResponseParamValueStringArray(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else if ((var4.GetResponseParamType(var3).compareTo("CHAR") == 0 || var4.GetResponseParamType(var3).compareTo("UCHAR") == 0) && var4.GetResponseParamValueDimension(var3) == 2) {
            String[] var5 = (String[])((String[])var4.GetResponseParamValue(var3));
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public byte[][] getResponseParamValueByteArray2D(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (byte[][])null;
        } else if ((var4.GetResponseParamType(var3).compareTo("BYTE") == 0 || var4.GetResponseParamType(var3).compareTo("INT8") == 0 || var4.GetResponseParamType(var3).compareTo("UINT8") == 0) && var4.GetResponseParamValueDimension(var3) == 2) {
            byte[][] var5 = (byte[][])((byte[][])var4.GetResponseParamValue(var3));
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public short[][] getResponseParamValueShortArray2D(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (short[][])null;
        } else if ((var4.GetResponseParamType(var3).compareTo("INT16") == 0 || var4.GetResponseParamType(var3).compareTo("UINT16") == 0) && var4.GetResponseParamValueDimension(var3) == 2) {
            short[][] var5 = (short[][])((short[][])var4.GetResponseParamValue(var3));
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public int[][] getResponseParamValueIntArray2D(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (int[][])null;
        } else if ((var4.GetResponseParamType(var3).compareTo("INT32") == 0 || var4.GetResponseParamType(var3).compareTo("UINT32") == 0) && var4.GetResponseParamValueDimension(var3) == 2) {
            int[][] var5 = (int[][])((int[][])var4.GetResponseParamValue(var3));
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public long[][] getResponseParamValueLongArray2D(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (long[][])null;
        } else if ((var4.GetResponseParamType(var3).compareTo("INT64") == 0 || var4.GetResponseParamType(var3).compareTo("UINT64") == 0) && var4.GetResponseParamValueDimension(var3) == 2) {
            long[][] var5 = (long[][])((long[][])var4.GetResponseParamValue(var3));
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public String[][] getResponseParamValueStringArray2D(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (String[][])null;
        } else if ((var4.GetResponseParamType(var3).compareTo("CHAR") == 0 || var4.GetResponseParamType(var3).compareTo("UCHAR") == 0) && var4.GetResponseParamValueDimension(var3) == 3) {
            String[][] var5 = (String[][])((String[][])var4.GetResponseParamValue(var3));
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public byte[][][] getResponseParamValueByteArray3D(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (byte[][][])null;
        } else if ((var4.GetResponseParamType(var3).compareTo("BYTE") == 0 || var4.GetResponseParamType(var3).compareTo("INT8") == 0 || var4.GetResponseParamType(var3).compareTo("UINT8") == 0) && var4.GetResponseParamValueDimension(var3) == 3) {
            byte[][][] var5 = (byte[][][])((byte[][][])var4.GetResponseParamValue(var3));
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public short[][][] getResponseParamValueShortArray3D(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (short[][][])null;
        } else if ((var4.GetResponseParamType(var3).compareTo("INT16") == 0 || var4.GetResponseParamType(var3).compareTo("UINT16") == 0) && var4.GetResponseParamValueDimension(var3) == 3) {
            short[][][] var5 = (short[][][])((short[][][])var4.GetResponseParamValue(var3));
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public int[][][] getResponseParamValueIntArray3D(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (int[][][])null;
        } else if ((var4.GetResponseParamType(var3).compareTo("INT32") == 0 || var4.GetResponseParamType(var3).compareTo("UINT32") == 0) && var4.GetResponseParamValueDimension(var3) == 3) {
            int[][][] var5 = (int[][][])((int[][][])var4.GetResponseParamValue(var3));
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public long[][][] getResponseParamValueLongArray3D(long var1, int var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (long[][][])null;
        } else if ((var4.GetResponseParamType(var3).compareTo("INT64") == 0 || var4.GetResponseParamType(var3).compareTo("UINT64") == 0) && var4.GetResponseParamValueDimension(var3) == 3) {
            long[][][] var5 = (long[][][])((long[][][])var4.GetResponseParamValue(var3));
            return var5;
        } else {
            throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var3));
        }
    }

    public Object getResponseParamValueByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return null;
            } else {
                return var4.GetResponseParamValue(var5);
            }
        }
    }

    public byte getResponseParamValueByteByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return -1;
            } else if ((var4.GetResponseParamType(var5).compareTo("BYTE") == 0 || var4.GetResponseParamType(var5).compareTo("INT8") == 0 || var4.GetResponseParamType(var5).compareTo("UINT8") == 0) && var4.GetResponseParamValueDimension(var5) == 1 && var4.GetResponseParamElementCount(var5) == 1) {
                byte[] var6 = (byte[])((byte[])var4.GetResponseParamValue(var5));
                if (var6.length != 1) {
                    throw new ClassCastException("Wrong ResponseParamValue Type :" + var4.GetResponseParamType(var5));
                } else {
                    return var6[0];
                }
            } else {
                throw new ClassCastException("Wrong ResponseParamValue Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public short getResponseParamValueShortByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return -1;
            } else if ((var4.GetResponseParamType(var5).compareTo("INT16") == 0 || var4.GetResponseParamType(var5).compareTo("UINT16") == 0) && var4.GetResponseParamValueDimension(var5) == 1 && var4.GetResponseParamElementCount(var5) == 1) {
                short[] var6 = (short[])((short[])var4.GetResponseParamValue(var5));
                if (var6.length != 1) {
                    throw new ClassCastException("Wrong ResponseParamValue Type : " + var4.GetResponseParamType(var5));
                } else {
                    return var6[0];
                }
            } else {
                throw new ClassCastException("Wrong ResponseParamValue Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public int getResponseParamValueIntByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return -1;
            } else if ((var4.GetResponseParamType(var5).compareTo("INT32") == 0 || var4.GetResponseParamType(var5).compareTo("UINT32") == 0) && var4.GetResponseParamValueDimension(var5) == 1 && var4.GetResponseParamElementCount(var5) == 1) {
                int[] var6 = (int[])((int[])var4.GetResponseParamValue(var5));
                if (var6.length != 1) {
                    throw new ClassCastException("Wrong ResponseParamValue Type : " + var4.GetResponseParamType(var5));
                } else {
                    return var6[0];
                }
            } else {
                throw new ClassCastException("Wrong ResponseParamValue Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public long getResponseParamValueLongByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1L;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return -1L;
            } else if ((var4.GetResponseParamType(var5).compareTo("INT64") == 0 || var4.GetResponseParamType(var5).compareTo("UINT64") == 0) && var4.GetResponseParamValueDimension(var5) == 1 && var4.GetResponseParamElementCount(var5) == 1) {
                long[] var6 = (long[])((long[])var4.GetResponseParamValue(var5));
                if (var6.length != 1) {
                    throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
                } else {
                    return var6[0];
                }
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public String getResponseParamValueStringByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return null;
            } else if ((var4.GetResponseParamType(var5).compareTo("CHAR") == 0 || var4.GetResponseParamType(var5).compareTo("UCHAR") == 0) && var4.GetResponseParamValueDimension(var5) == 1) {
                String var6 = (String)var4.GetResponseParamValue(var5);
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public byte[] getResponseParamValueByteArrayByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return null;
            } else if ((var4.GetResponseParamType(var5).compareTo("BYTE") == 0 || var4.GetResponseParamType(var5).compareTo("INT8") == 0 || var4.GetResponseParamType(var5).compareTo("UINT8") == 0) && var4.GetResponseParamValueDimension(var5) == 1) {
                byte[] var6 = (byte[])((byte[])var4.GetResponseParamValue(var5));
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public short[] getResponseParamValueShortArrayByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return null;
            } else if ((var4.GetResponseParamType(var5).compareTo("INT16") == 0 || var4.GetResponseParamType(var5).compareTo("UINT16") == 0) && var4.GetResponseParamValueDimension(var5) == 1) {
                short[] var6 = (short[])((short[])var4.GetResponseParamValue(var5));
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public int[] getResponseParamValueIntArrayByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return null;
            } else if ((var4.GetResponseParamType(var5).compareTo("INT32") == 0 || var4.GetResponseParamType(var5).compareTo("UINT32") == 0) && var4.GetResponseParamValueDimension(var5) == 1) {
                int[] var6 = (int[])((int[])var4.GetResponseParamValue(var5));
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public long[] getResponseParamValueLongArrayByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return null;
            } else if ((var4.GetResponseParamType(var5).compareTo("INT64") == 0 || var4.GetResponseParamType(var5).compareTo("UINT64") == 0) && var4.GetResponseParamValueDimension(var5) == 1) {
                long[] var6 = (long[])((long[])var4.GetResponseParamValue(var5));
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public String[] getResponseParamValueStringArrayByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return null;
            } else if ((var4.GetResponseParamType(var5).compareTo("CHAR") == 0 || var4.GetResponseParamType(var5).compareTo("UCHAR") == 0) && var4.GetResponseParamValueDimension(var5) == 2) {
                String[] var6 = (String[])((String[])var4.GetResponseParamValue(var5));
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public byte[][] getResponseParamValueByteArray2DByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (byte[][])null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return (byte[][])null;
            } else if ((var4.GetResponseParamType(var5).compareTo("BYTE") == 0 || var4.GetResponseParamType(var5).compareTo("INT8") == 0 || var4.GetResponseParamType(var5).compareTo("UINT8") == 0) && var4.GetResponseParamValueDimension(var5) == 2) {
                byte[][] var6 = (byte[][])((byte[][])var4.GetResponseParamValue(var5));
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public short[][] getResponseParamValueShortArray2DByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (short[][])null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return (short[][])null;
            } else if ((var4.GetResponseParamType(var5).compareTo("INT16") == 0 || var4.GetResponseParamType(var5).compareTo("UINT16") == 0) && var4.GetResponseParamValueDimension(var5) == 2) {
                short[][] var6 = (short[][])((short[][])var4.GetResponseParamValue(var5));
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public int[][] getResponseParamValueIntArray2DByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (int[][])null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return (int[][])null;
            } else if ((var4.GetResponseParamType(var5).compareTo("INT32") == 0 || var4.GetResponseParamType(var5).compareTo("UINT32") == 0) && var4.GetResponseParamValueDimension(var5) == 2) {
                int[][] var6 = (int[][])((int[][])var4.GetResponseParamValue(var5));
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public long[][] getResponseParamValueLongArray2DByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (long[][])null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return (long[][])null;
            } else if ((var4.GetResponseParamType(var5).compareTo("INT64") == 0 || var4.GetResponseParamType(var5).compareTo("UINT64") == 0) && var4.GetResponseParamValueDimension(var5) == 2) {
                long[][] var6 = (long[][])((long[][])var4.GetResponseParamValue(var5));
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public String[][] getResponseParamValueStringArray2DByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (String[][])null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return (String[][])null;
            } else if ((var4.GetResponseParamType(var5).compareTo("CHAR") == 0 || var4.GetResponseParamType(var5).compareTo("UCHAR") == 0) && var4.GetResponseParamValueDimension(var5) == 3) {
                String[][] var6 = (String[][])((String[][])var4.GetResponseParamValue(var5));
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public byte[][][] getResponseParamValueByteArray3DByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (byte[][][])null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return (byte[][][])null;
            } else if ((var4.GetResponseParamType(var5).compareTo("BYTE") == 0 || var4.GetResponseParamType(var5).compareTo("INT8") == 0 || var4.GetResponseParamType(var5).compareTo("UINT8") == 0) && var4.GetResponseParamValueDimension(var5) == 3) {
                byte[][][] var6 = (byte[][][])((byte[][][])var4.GetResponseParamValue(var5));
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public short[][][] getResponseParamValueShortArray3DByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (short[][][])null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return (short[][][])null;
            } else if ((var4.GetResponseParamType(var5).compareTo("INT16") == 0 || var4.GetResponseParamType(var5).compareTo("UINT16") == 0) && var4.GetResponseParamValueDimension(var5) == 3) {
                short[][][] var6 = (short[][][])((short[][][])var4.GetResponseParamValue(var5));
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public int[][][] getResponseParamValueIntArray3DByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (int[][][])null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return (int[][][])null;
            } else if ((var4.GetResponseParamType(var5).compareTo("INT32") == 0 || var4.GetResponseParamType(var5).compareTo("UINT32") == 0) && var4.GetResponseParamValueDimension(var5) == 3) {
                int[][][] var6 = (int[][][])((int[][][])var4.GetResponseParamValue(var5));
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public long[][][] getResponseParamValueLongArray3DByName(long var1, String var3) {
        CrxMain var4 = (CrxMain)this.vCrx.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return (long[][][])null;
        } else {
            int var5 = var4.GetResponseParamNoByName(var3);
            if (var5 == -1) {
                System.err.println(var4.GetMessage());
                return (long[][][])null;
            } else if ((var4.GetResponseParamType(var5).compareTo("INT64") == 0 || var4.GetResponseParamType(var5).compareTo("UINT64") == 0) && var4.GetResponseParamValueDimension(var5) == 3) {
                long[][][] var6 = (long[][][])((long[][][])var4.GetResponseParamValue(var5));
                return var6;
            } else {
                throw new ClassCastException("Wrong getResponseParamValue() Return Type : " + var4.GetResponseParamType(var5));
            }
        }
    }

    public void printResponse(long var1, PrintStream var3, int var4) {
        CrxMain var5 = (CrxMain)this.vCrx.get((int)var1);
        if (var5 == null) {
            System.err.println("invalid handle");
        } else {
            var5.PrintResponse(var3, var4);
        }
    }

    public String getErrorMessage(long var1) {
        try {
            CrxMain var3 = (CrxMain)this.vCrx.get((int)var1);
            if (var3 == null) {
                System.err.println("invalid handle");
                return "invalid handle";
            } else {
                return var3.GetMessage();
            }
        } catch (ArrayIndexOutOfBoundsException var4) {
            return "invalid handle";
        }
    }
}
