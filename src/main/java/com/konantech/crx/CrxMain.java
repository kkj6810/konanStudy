package com.konantech.crx;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

class CrxMain {
    private static final int eOK = 0;
    private static final int eERR = -1;
    private static final int eTIMEOUT = -2;
    private static final int eONUPDATE = -3;
    private static final int eSERVICESTOP = -4;
    private static final int eNETWORKERR = -5;
    private static final int REXT_NORM = 0;
    private static final int REXT_URGT = 1;
    private static final int REXT_CTRL = 2;
    private static final int REXT_NOOP = 3;
    private static final int REQUEST_CRX = 67;
    private static final int CRX_REQUEST_UNIVERSAL_PARAMETER = 0;
    private static final int CRX_REQUEST_XML = 1;
    private static final int CRX_REQUEST_EXTRACT_KEYWORD_FROM_QUERY = 2;
    private static final int CRX_REQUEST_EXTRACT_KEYWORD_FROM_DOCUMENT = 3;
    private static final int CRX_REQUEST_EVALUATE_REPLICA_SIMILARITY = 4;
    private static final byte[] g_encrypt_key = new byte[]{-89, 24, 95, -119, 3, -68, 85, 38};
    private String m_msg = "";
    private CrxParam m_param = null;
    private CrxSocket m_socket = null;
    private String m_addr = null;
    private int m_port = 0;
    private String m_id = null;
    private String m_pwd = null;
    private String m_copt = null;
    private int m_timeout_request = 15;
    private int m_timeout_linger = 0;
    private int m_tcp_nodelay = 0;
    private int m_socket_reuse_address = 0;
    private int m_response_size = 0;
    private byte[] m_p_response = null;
    private int m_f_response_received = 0;
    private int m_worker_time = 0;
    private String m_worker_address = null;
    private int m_response_time = 0;
    private long m_tv_begin = 0L;
    private String m_char_enc = null;

    public CrxMain() {
        this.m_socket = new CrxSocket();
        this.m_param = new CrxParam();
        this.m_char_enc = System.getProperty("file.encoding");
    }

    public int Connect(String var1, String var2, String var3, String var4) {
        int var5 = this.SetServiceAddr(var1);
        if (var5 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else if (var2.length() > 15) {
            this.m_msg = "userid too long (C97172)";
            return -1;
        } else {
            this.m_id = var2;
            if (var3.length() > 15) {
                this.m_msg = "password too long (C97173)";
                return -1;
            } else {
                this.m_pwd = var3;
                this.m_copt = var4;
                return 0;
            }
        }
    }

    public int Disconnect() {
        this.m_addr = null;
        this.m_id = null;
        this.m_pwd = null;
        this.m_copt = null;

        try {
            this.m_socket.disconnect();
            return 0;
        } catch (IOException var2) {
            this.m_msg = "cannot close socket";
            return -1;
        } catch (NullPointerException var3) {
            return -1;
        }
    }

    public int ClearOption() {
        this.m_timeout_request = 0;
        this.m_timeout_linger = 0;
        this.m_tcp_nodelay = 0;
        this.m_socket_reuse_address = 0;
        return 0;
    }

    public int SetOption(String var1, String var2) {
        if (var1.compareTo("TIMEOUT_REQUEST") == 0) {
            this.m_timeout_request = Integer.parseInt(var2);
        } else if (var1.compareTo("TIMEOUT_LINGER") == 0) {
            this.m_timeout_linger = Integer.parseInt(var2);
        } else if (var1.compareTo("TCP_NODELAY") == 0) {
            this.m_tcp_nodelay = Integer.parseInt(var2);
        } else {
            if (var1.compareTo("SOCKET_REUSE_ADDRESS") != 0) {
                this.m_msg = "unknown option " + var1 + "(C18732)";
                return -1;
            }

            this.m_socket_reuse_address = Integer.parseInt(var2);
        }

        return 0;
    }

    public int SetCharacterEncoding(String var1) {
        this.m_char_enc = var1;
        return this.m_param.SetCharacterEncoding(var1);
    }

    public String GetCharacterEncoding() {
        return this.m_char_enc;
    }

    public int PutRequest(String var1) throws IOException {
        boolean var2 = false;
        Object var3 = null;
        ByteArrayOutputStream var4 = new ByteArrayOutputStream();
        if (var1 != null && var1.length() != 0) {
            CrxUtil.BA_writeString(var4, var1, this.m_char_enc);
            byte[] var6 = var4.toByteArray();
            int var5 = this.SendRequest(1, var6);
            return var5;
        } else {
            this.m_msg = "null request";
            return -1;
        }
    }

    public String GetResponse() throws IOException {
        boolean var1 = false;
        String var2 = null;
        int var3 = this.ReadResponse();
        if (var3 < 0) {
            return null;
        } else {
            var2 = new String(this.m_p_response, 0, this.m_response_size, this.m_char_enc);
            return var2;
        }
    }

    public int ClearRequest() throws IOException {
        boolean var1 = false;
        int var2 = this.m_param.ClearRequest();
        if (var2 != 0) {
            this.m_msg = this.m_param.getMessage();
        }

        return var2;
    }

    public int PutRequestName(String var1) {
        boolean var2 = false;
        int var3 = this.m_param.SetRequestName(var1);
        if (var3 != 0) {
            this.m_msg = this.m_param.getMessage();
        }

        return var3;
    }

    public int PutRequestFamily(String var1) {
        boolean var2 = false;
        int var3 = this.m_param.SetRequestFamily(var1);
        if (var3 != 0) {
            this.m_msg = this.m_param.getMessage();
        }

        return var3;
    }

    public int PutRequestVersion(String var1) {
        boolean var2 = false;
        int var3 = this.m_param.SetRequestVersion(var1);
        if (var3 != 0) {
            this.m_msg = this.m_param.getMessage();
        }

        return var3;
    }

    public int PutRequestParam(String var1, String var2, int var3, Object var4) {
        boolean var5 = false;
        int var6 = this.m_param.SetRequestParam(var1, var2, var3, var4);
        if (var6 != 0) {
            this.m_msg = this.m_param.getMessage();
        }

        return var6;
    }

    public int PutRequestParam2D(String var1, String var2, int var3, int[] var4, Object var5) {
        boolean var6 = false;
        int var7 = this.m_param.SetRequestParam2D(var1, var2, var3, var4, var5);
        if (var7 != 0) {
            this.m_msg = this.m_param.getMessage();
        }

        return var7;
    }

    public int PutRequestParam3D(String var1, String var2, int var3, int[] var4, int[][] var5, Object var6) {
        boolean var7 = false;
        int var8 = this.m_param.SetRequestParam3D(var1, var2, var3, var4, var5, var6);
        if (var8 != 0) {
            this.m_msg = this.m_param.getMessage();
        }

        return var8;
    }

    public int SubmitRequest() throws IOException {
        boolean var1 = false;
        Object var2 = null;
        this.m_tv_begin = System.currentTimeMillis();
        byte[] var4 = this.m_param.GetPackedRequest();
        if (var4 == null) {
            this.m_msg = this.m_param.getMessage();
            return -1;
        } else {
            this.m_f_response_received = 0;
            int var3 = this.SendRequest(0, var4);
            return var3;
        }
    }

    public int ReceiveResponse() throws IOException {
        int var1 = this.ReadResponse();
        if (var1 != 0) {
            return -1;
        } else {
            var1 = this.m_param.SetPackedResponse(this.m_p_response);
            if (var1 != 0) {
                this.m_msg = this.m_param.getMessage();
                return -1;
            } else {
                long var2 = System.currentTimeMillis();
                this.m_response_time = (int)(var2 - this.m_tv_begin);
                return var1;
            }
        }
    }

    public String GetWorkerAddress() {
        return this.m_worker_address;
    }

    public int GetWorkerTime() {
        return this.m_worker_time;
    }

    public int GetReponseTime() {
        return this.m_response_time;
    }

    public String GetResponseName() {
        String var1 = null;
        var1 = this.m_param.GetResponseName();
        if (var1 == null) {
            this.m_msg = this.m_param.getMessage();
        }

        return var1;
    }

    public String GetResponseFamily() {
        String var1 = null;
        var1 = this.m_param.GetResponseFamily();
        if (var1 == null) {
            this.m_msg = this.m_param.getMessage();
        }

        return var1;
    }

    public String GetResponseVersion() {
        String var1 = null;
        var1 = this.m_param.GetResponseVersion();
        if (var1 == null) {
            this.m_msg = this.m_param.getMessage();
        }

        return var1;
    }

    public int GetResponseParamCount() {
        boolean var1 = false;
        int var2 = this.m_param.GetResponseParamCount();
        if (var2 == -1) {
            this.m_msg = this.m_param.getMessage();
        }

        return var2;
    }

    public String GetResponseParamName(int var1) {
        String var2 = null;
        var2 = this.m_param.GetResponseParamName(var1);
        if (var2 == null) {
            this.m_msg = this.m_param.getMessage();
        }

        return var2;
    }

    public String GetResponseParamType(int var1) {
        String var2 = null;
        var2 = this.m_param.GetResponseParamType(var1);
        if (var2 == null) {
            this.m_msg = this.m_param.getMessage();
        }

        return var2;
    }

    public int GetResponseParamValueDimension(int var1) {
        boolean var2 = false;
        int var3 = this.m_param.GetResponseParamValueDimension(var1);
        if (var3 == -1) {
            this.m_msg = this.m_param.getMessage();
        }

        return var3;
    }

    public int GetResponseParamElementCount(int var1) {
        boolean var2 = false;
        int var3 = this.m_param.GetResponseParamElementCount(var1);
        if (var3 == -1) {
            this.m_msg = this.m_param.getMessage();
        }

        return var3;
    }

    public int[] GetResponseParamElementCount2D(int var1) {
        Object var2 = null;
        int[] var3 = this.m_param.GetResponseParamElementCount2D(var1);
        if (var3 == null) {
            this.m_msg = "Dimension of Response Param Value is not 2";
        }

        return var3;
    }

    public int[][] GetResponseParamElementCount3D(int var1) {
        int[][] var2 = (int[][])null;
        var2 = this.m_param.GetResponseParamElementCount3D(var1);
        if (var2 == null) {
            this.m_msg = "Dimension of Response Param Value is not 3";
        }

        return var2;
    }

    public Object GetResponseParamValue(int var1) {
        Object var2 = null;
        var2 = this.m_param.GetResponseParamValue(var1);
        if (var2 == null) {
            this.m_msg = this.m_param.getMessage();
        }

        return var2;
    }

    public int GetResponseParamNoByName(String var1) {
        boolean var2 = true;
        int var3 = this.m_param.GetResponseParamNoByName(var1);
        if (var3 == -1) {
            this.m_msg = this.m_param.getMessage();
        }

        return var3;
    }

    public void PrintResponse(PrintStream var1, int var2) {
        this.m_param.PrintResponse(var1, var2);
    }

    public String GetMessage() {
        return this.m_msg;
    }

    private int SendRequest(int var1, byte[] var2) throws IOException {
        boolean var3 = false;
        this.m_f_response_received = 0;
        this.m_response_size = 0;
        this.m_p_response = null;
        ByteArrayOutputStream var6 = new ByteArrayOutputStream();
        int var5 = 302010;
        CrxUtil.BA_writeInteger(var6, var5);
        CrxUtil.BA_writeNString(var6, "", 12);
        CrxUtil.BA_writeNString(var6, this.m_socket.getIPv4SocketAddress(), 16);
        CrxUtil.BA_writeNString(var6, this.m_id, 16);
        CrxUtil.BA_writeNString(var6, this.m_pwd, 16);
        CrxUtil.BA_writeInteger(var6, var1);
        CrxUtil.BA_writeNString(var6, "", 4);
        var6.write(var2, 0, var2.length);
        byte[] var4 = var6.toByteArray();
        this.EncodeDecodeCrxRequest(var4);
        if (this.m_timeout_request > 0) {
            this.m_socket.setTimeOut(this.m_timeout_request, 0);
        }

        int var7 = this.m_socket.connect(this.m_addr, this.m_port);
        if (var7 < 0) {
            this.m_msg = this.m_socket.getMessage();
        } else {
            this.m_socket.setLingerTimeOut(this.m_timeout_linger);
            if (this.m_tcp_nodelay > 0) {
                var7 = this.m_socket.setTcpNoDelay(this.m_tcp_nodelay);
            }

            if (this.m_socket_reuse_address > 0) {
                var7 = this.m_socket.setReuseAddress(this.m_socket_reuse_address);
            }

            var7 = this.m_socket.send(0, 67, var4.length, var4);
            if (var7 < 0) {
                this.m_msg = this.m_socket.getMessage();
            } else {
                var7 = 0;
            }
        }

        if (var7 < 0) {
            this.m_socket.disconnect();
        }

        return var7;
    }

    private int ReadResponse() throws IOException {
        int var1 = 0;
        boolean var2 = false;
        Object var3 = null;
        String var4 = null;
        boolean var5 = false;
        boolean var6 = false;
        if (this.m_f_response_received == 0) {
            if (this.m_timeout_request > 0) {
                this.m_socket.setTimeOut(this.m_timeout_request, 0);
            }

            var1 = this.m_socket.recv();
            if (var1 != 0) {
                this.m_msg = this.m_socket.getMessage();
            } else {
                int var7 = this.m_socket.getRecv_svr_rc();
                byte[] var8 = this.m_socket.getRecv_data();
                int var11;
                if (var7 < 0) {
                    var1 = var7;
                    var11 = CrxUtil.bytes2str(var8, 0);
                    var4 = new String(var8, 0, var11, this.m_char_enc);
                    this.m_msg = var4;
                } else {
                    this.EncodeDecodeCrxRequest(var8);
                    byte var9 = 4;
                    this.m_worker_time = CrxUtil.bytes2int(var8, var9);
                    int var10 = var9 + 4;
                    var9 = 16;
                    var11 = CrxUtil.bytes2str(var8, var9);
                    this.m_worker_address = new String(var8, var9, var11, this.m_char_enc);
                    int var10000 = var9 + var11 + 1;
                    this.m_response_size = var8.length - 72;
                    this.m_p_response = new byte[this.m_response_size];
                    System.arraycopy(var8, 72, this.m_p_response, 0, this.m_response_size);
                    this.m_f_response_received = 1;
                    var1 = 0;
                }
            }
        }

        this.m_socket.disconnect();
        return var1;
    }

    private void EncodeDecodeCrxRequest(byte[] var1) {
        int var3 = 0;

        for(int var2 = 4; var2 < var1.length; ++var2) {
            if (var3 >= 8) {
                var3 = 0;
            }

            var1[var2] ^= g_encrypt_key[var3];
            ++var3;
        }

    }

    private int SetServiceAddr(String var1) {
        boolean var2 = false;
        boolean var3 = false;
        int var4 = 0;
        if (var1 != null && var1.length() != 0) {
            int var5 = var1.indexOf(":");
            if (var5 < 0) {
                return -1;
            } else {
                this.m_addr = var1.substring(0, var5);
                ++var5;

                while(var1.charAt(var5 + var4) == ' ') {
                    ++var4;
                }

                this.m_port = Integer.parseInt(var1.substring(var5 + var4));
                return 0;
            }
        } else {
            return -1;
        }
    }
}
