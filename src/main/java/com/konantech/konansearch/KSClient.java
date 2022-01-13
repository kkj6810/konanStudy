package com.konantech.konansearch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.StringTokenizer;

class KSClient {
    private static final byte[] g_default_encrypt_key = new byte[]{-89, 24, 95, -119, 3, -68, 85, 38};
    private MIO m_io_search = null;
    private MIO[] m_p_io_hilite = null;
    private static final String API_VER_INDEX_SEARCH = "001";
    private static final String API_VER_HILITE = "001";
    private static final int eOK = 0;
    private static final int eERR = -1;
    private static final int eTIMEOUT = -2;
    private static final int eONUPDATE = -3;
    private static final int eSERVICESTOP = -4;
    private static final int eNETWORKERR = -5;
    private static final int REQUEST_SUBMIT_QUERY = 1;
    private static final int REQUEST_EXTRACT_KEYWORD = 17;
    private static final int REQUEST_EXTRACT_KEYWORD2 = 27;
    private static final int REQUEST_INSERT = 32;
    private static final int REQUEST_DELETE = 33;
    private static final int REQUEST_UPDATE = 34;
    private static final int REQUEST_SEARCH = 35;
    private static final int REQUEST_MODULE_LOAD = 39;
    private static final int REQUEST_MODULE_UNLOAD = 40;
    private static final int REQUEST_MODULE_RELOAD = 41;
    private static final int REQUEST_GET_QUERY_ATTRIBUTE = 48;
    private static final int REQUEST_COMPLETE_KEYWORD = 49;
    private static final int REQUEST_SPELL_CHECK = 50;
    private static final int REQUEST_RECOMMEND_KEYWORD = 51;
    private static final int REQUEST_GET_POPULAR_KEYWORD = 52;
    private static final int REQUEST_ANCHOR_TEXT = 53;
    private static final int REQUEST_GET_SYNONYM_LIST = 54;
    private static final int REQUEST_GET_REAL_TIME_POPULAR_KEYWORD = 55;
    private static final int REQUEST_CENSOR_SEARCH_WORDS = 56;
    private static final int REQUEST_TRANSLITERATE = 57;
    private static final int REQUEST_PUT_CACHE = 59;
    private static final int REQUEST_GET_CACHE = 60;
    private static final int REQUEST_MOD_IO = 99;
    private static final int g_xor_key = -1446176383;
    private static final int CS_DEFAULT = 0;
    private static final int CS_EUCKR = 1;
    private static final int CS_EUCCN = 2;
    private static final int CS_EUCJP = 3;
    private static final int CS_UTF8 = 4;
    private static final int CS_USASCII = 5;
    private static final int CS_BIG5 = 6;
    private static final int CS_SJIS = 7;
    private static final int QEXP_K2K = 1;
    private static final int QEXP_K2E = 2;
    private static final int QEXP_E2E = 4;
    private static final int QEXP_E2K = 8;
    private static final int QEXP_TRL = 16;
    private static final int QEXP_RCM = 32;
    private static final int QPP_OP_NONE = 0;
    private static final int QPP_OP_EQ = 1;
    private static final int QPP_OP_LT = 2;
    private static final int QPP_OP_LE = 3;
    private static final int QPP_OP_GT = 4;
    private static final int QPP_OP_GE = 5;
    private static final int QPP_DOMAIN_NONE = 0;
    private static final int QPP_DOMAIN_CAR = 1;
    private static final int OPTION_SOCKET_TIMEOUT_REQUEST = 1;
    private static final int OPTION_SOCKET_TIMEOUT_LINGER = 2;
    private static final int OPTION_SOCKET_3WAY_MODE = 3;
    private static final int OPTION_SOCKET_ASYNC_REQUEST = 4;
    private static final int OPTION_SOCKET_CONNECTION_TIMEOUT_MSEC = 6;
    private static final int OPTION_SOCKET_REQUEST_TIMEOUT_MSEC = 7;
    private static final int OPTION_REQUEST_PRIORITY = 10;
    private static final int OPTION_REQUEST_CHARSET_UTF8 = 11;
    private static final int OPTION_RETRY_ON_NETWORK_ERROR = 20;
    private static final int OPTION_REPORT_CLIENT_ERROR = 30;
    private static final int OPTION_SOCKET_RETRY_ON_ERROR = 40;
    private Socket m_socket = null;
    private int m_timeout = 300;
    private String m_msg = "";
    private int m_searchTime = 0;
    private int m_n_total = 0;
    private int m_n_row = 0;
    private int m_n_col = 0;
    private String[] m_col_name = null;
    private int m_col_name_count = 0;
    private int[] m_record_no = null;
    private int[] m_score = null;
    private String[][] m_field_data = (String[][])null;
    private int[][] m_field_size = (int[][])null;
    private String m_p_cooked = "";
    private Socket[] m_p_io_socks = null;
    private int m_linger_timeout = 0;
    private int m_use_socket_3way_mode = 0;
    private int m_request_timeout = 15;
    private int m_request_priority = 0;
    private int m_retry_on_network_error = 0;
    private int m_option_socket_async_request = 0;
    private int m_f_request_sent = 0;
    private int m_f_request_type = 0;
    private int m_f_response_received = 0;
    private int m_connection_timeout_msec = 0;
    private int m_request_timeout_msec = -1;
    private int m_option_socket_retry_on_error = 2;
    private int m_max_n_cluster = 0;
    private int m_max_n_clu_record = 0;
    private String m_field_list = "";
    private String[] m_prev_title = null;
    private String m_key_list = "";
    private int m_clu_flag = 0;
    private String m_expand_query = null;
    private int m_cmd_expand_query = 0;
    private int m_max_n_related_term = 0;
    private int m_n_out_cluster = 0;
    private int[] m_out_rec_cnt_per_class = null;
    private int[][] m_out_class_record_no = (int[][])null;
    private String[] m_cluster_title = null;
    private int m_n_rec_term = 0;
    private String m_rec_term = null;
    private int m_n_exp_k2k = 0;
    private String m_exp_k2k = null;
    private int m_n_exp_k2e = 0;
    private String m_exp_k2e = null;
    private int m_n_exp_e2k = 0;
    private String m_exp_e2k = null;
    private int m_n_exp_e2e = 0;
    private String m_exp_e2e = null;
    private int m_n_exp_trl = 0;
    private String m_exp_trl = null;
    private int m_n_exp_rcm = 0;
    private String m_exp_rcm = null;
    private String m_auth_code = "";
    private String m_log = "";
    private String m_addr = null;
    private int m_port = 0;
    private String m_char_enc = null;
    private int m_n_connect = 0;
    private int m_n_err_connect = 0;
    private int m_n_err_send = 0;
    private int m_n_err_recv = 0;
    private int m_n_err_acpt_full = 0;
    private int m_n_err_cli_recv = 0;
    private int m_n_err_recv_full = 0;
    private int m_n_err_timeout = 0;
    private int m_union_table_count = 0;
    private int m_tabno_count = 0;
    private int[] m_tabno = null;
    private String[][] m_union_table_name = (String[][])null;
    private int m_group_count = 0;
    private int m_group_key_count = 0;
    private int[] m_group_size = null;
    private String[][] m_group_key_val = (String[][])null;
    private int m_f_get_cache = 0;
    private int m_cache_hit_flag = 0;
    private String m_cache_out_data = null;
    private int m_rext = 0;
    private int[] m_matchfield = null;
    private int[] m_userkey_idx = null;

    public KSClient() {
        this.m_char_enc = System.getProperty("file.encoding");
    }

    public void Cleanup() {
        this.ClearSearchCondition();
        this.ClearSearchResult();
    }

    public int SetOption(int var1, int var2) {
        byte var3 = 0;
        if (var1 < 0) {
            return -1;
        } else {
            switch(var1) {
                case 1:
                    this.m_request_timeout = var2;
                    break;
                case 2:
                    this.m_linger_timeout = var2;
                    break;
                case 3:
                    this.m_use_socket_3way_mode = var2;
                    break;
                case 4:
                    this.m_option_socket_async_request = var2;
                case 5:
                case 8:
                case 9:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                default:
                    break;
                case 6:
                    this.m_connection_timeout_msec = var2;
                    break;
                case 7:
                    this.m_request_timeout_msec = var2;
                    break;
                case 10:
                    if (var2 == 4) {
                        this.m_rext = 1;
                    }

                    this.m_request_priority = var2;
                    break;
                case 11:
                    this.m_msg = "specified option not available in JAVA";
                    var3 = -1;
                    break;
                case 20:
                    this.m_retry_on_network_error = var2;
                    break;
                case 40:
                    this.m_option_socket_retry_on_error = var2;
            }

            return var3;
        }
    }

    public int SetTimeOut(int var1) {
        if (var1 < 0) {
            return -1;
        } else {
            this.m_request_timeout = var1;
            return 0;
        }
    }

    public String GetMessage() {
        return this.m_msg;
    }

    public void ClearNetworkErrorStatistics() {
        this.m_msg = "";
        this.m_n_connect = 0;
        this.m_n_err_connect = 0;
        this.m_n_err_send = 0;
        this.m_n_err_recv = 0;
        this.m_n_err_acpt_full = 0;
        this.m_n_err_cli_recv = 0;
        this.m_n_err_recv_full = 0;
        this.m_n_err_timeout = 0;
    }

    public void ClearSearchCondition() {
        this.m_max_n_cluster = 0;
        this.m_max_n_clu_record = 0;
        this.m_field_list = "";
        this.m_prev_title = null;
        this.m_key_list = null;
        this.m_clu_flag = 0;
        this.m_max_n_related_term = 0;
        this.m_expand_query = null;
        this.m_cmd_expand_query = 0;
        this.m_rext = 0;
    }

    private void ClearSearchResult() {
        this.m_searchTime = 0;
        this.m_n_total = 0;
        this.m_n_row = 0;
        this.m_n_col = 0;
        this.m_col_name = null;
        this.m_col_name_count = 0;
        this.m_record_no = null;
        this.m_score = null;
        this.m_field_data = (String[][])null;
        this.m_field_size = (int[][])null;
        this.m_n_out_cluster = 0;
        this.m_out_rec_cnt_per_class = null;
        this.m_out_class_record_no = (int[][])null;
        this.m_n_rec_term = 0;
        this.m_rec_term = null;
        this.m_n_exp_k2k = 0;
        this.m_exp_k2k = null;
        this.m_n_exp_k2e = 0;
        this.m_exp_k2e = null;
        this.m_n_exp_e2k = 0;
        this.m_exp_e2k = null;
        this.m_n_exp_e2e = 0;
        this.m_exp_e2e = null;
        this.m_n_exp_trl = 0;
        this.m_exp_trl = null;
        this.m_n_exp_rcm = 0;
        this.m_exp_rcm = null;
        this.m_f_request_sent = 0;
        this.m_f_request_type = 0;
        this.m_f_response_received = 0;
    }

    public int SetClusterCondition(int var1, int var2, String var3, String[] var4, String var5, int var6) {
        this.m_max_n_cluster = var1;
        this.m_max_n_clu_record = var2;
        this.m_field_list = new String(var3);
        if (var4 == null) {
            this.m_prev_title = null;
        } else {
            this.m_prev_title = new String[var4.length];

            for(int var7 = 0; var7 < var4.length; ++var7) {
                this.m_prev_title[var7] = new String(var4[var7]);
            }
        }

        this.m_key_list = new String(var5);
        this.m_clu_flag = var6;
        return 0;
    }

    public int SetRelatedTermCondition(int var1) {
        this.m_max_n_related_term = var1;
        return 0;
    }

    public int SetExpandQueryCondition(String var1, int var2) {
        this.m_expand_query = new String(var1);
        this.m_cmd_expand_query = var2;
        return 0;
    }

    public int SetCharacterEncoding(String var1) {
        this.m_char_enc = var1;
        return 0;
    }

    public String GetCharacterEncoding() {
        return this.m_char_enc;
    }

    public int Connect(String var1, int var2, Socket var3) {
        byte var4 = 0;

        byte var10;
        try {
            var3.connect(new InetSocketAddress(var1, var2), this.m_connection_timeout_msec);
        } catch (SocketTimeoutException var7) {
            this.m_msg = "connection timeout(" + this.m_connection_timeout_msec + " msec) to server (" + var1 + ":" + var2 + ")";
            var10 = -5;
            ++this.m_n_err_connect;
            return var10;
        } catch (IllegalArgumentException var8) {
            this.m_msg = "illegal argument (" + var1 + ":" + var2 + ")";
            var10 = -5;
            ++this.m_n_err_connect;
            return var10;
        } catch (IOException var9) {
            this.m_msg = "cannot connect to server (" + var1 + ":" + var2 + "):" + var9.getMessage();
            var10 = -5;
            ++this.m_n_err_connect;
            return var10;
        }

        try {
            if (this.m_linger_timeout > 0) {
                var3.setSoLinger(true, this.m_linger_timeout);
            } else {
                var3.setSoLinger(true, 0);
            }
        } catch (IOException var6) {
            this.m_msg = "cannot set so_linger";
        }

        return var4;
    }

    public int SendRequest(byte[] var1, Socket var2) {
        byte var3 = 0;

        try {
            OutputStream var4 = var2.getOutputStream();
            var4.write(var1);
            return var3;
        } catch (IOException var5) {
            byte var6 = -1;
            ++this.m_n_err_send;
            return var6;
        }
    }

    public byte[] RecvResult(long var1, IOPoll var3, Socket var4) {
        InputStream var8 = null;
        long var5 = System.currentTimeMillis();
        int var7;
        if (this.m_request_timeout_msec > 0) {
            var7 = this.m_request_timeout_msec;
        } else if (this.m_request_timeout <= 7) {
            var7 = this.m_request_timeout * 1000;
        } else {
            var7 = this.m_request_timeout * 1000 - (int)(var5 - var1);
            if (var7 < 7000) {
                var7 = 7000;
            }
        }

        try {
            var4.setSoTimeout(var7);
        } catch (SocketException var19) {
            this.m_msg = "cannot set socket recv timeout";
            ++this.m_n_err_recv;
            return null;
        }

        try {
            var8 = var4.getInputStream();
        } catch (IOException var18) {
            this.m_msg = "cannot acquire socket read stream";
            ++this.m_n_err_recv;
            return null;
        }

        byte[] var9 = new byte[8];

        int var10;
        try {
            var10 = this.socketRead(var8, var9, var3);
        } catch (InterruptedIOException var16) {
            this.m_msg = "socket read timed out (while receiving size)";
            ++this.m_n_err_timeout;
            return null;
        } catch (IOException var17) {
            this.m_msg = "socket read error:" + var17.getMessage();
            ++this.m_n_err_recv;
            return null;
        }

        if (var10 != 8) {
            this.m_msg = "cannot receive 8-byte size information";
            ++this.m_n_err_recv;
            return null;
        } else {
            int var11 = bytes2int(var9, 0);
            int var12 = bytes2int(var9, 4);
            var12 ^= -1446176383;
            if (var11 != var12) {
                this.m_msg = "incompatible client version";
                ++this.m_n_err_recv;
                return null;
            } else {
                int var13 = var11 - 8;
                byte[] var14 = new byte[var13];

                try {
                    var10 = this.socketRead(var8, var14, (IOPoll)null);
                    if (this.m_rext == 1) {
                        this.DECRYPT_DATA_WITH_KEY(var14, (byte[])null, 8);
                    }
                } catch (InterruptedIOException var20) {
                    this.m_msg = "socket read timed out (while receiving size)";
                    ++this.m_n_err_timeout;
                    return null;
                } catch (IOException var21) {
                    this.m_msg = "socket read error:" + var21.getMessage();
                    ++this.m_n_err_recv;
                    return null;
                }

                if (var10 != var13) {
                    this.m_msg = "cannot receive data (got=" + var10 + ", expected=" + var13 + ")";
                    ++this.m_n_err_recv;
                    return null;
                } else {
                    return var14;
                }
            }
        }
    }

    public void GetResultCodes(byte[] var1, int[] var2) {
        int var3 = 0;
        boolean var4 = false;
        int var6 = bytes2int(var1, 0);
        if (var6 != 0) {
            String var5 = new String(var1, 8, var1.length - 8);
            this.m_msg = var5;
            if (var5.startsWith("volume busy")) {
                var3 = -3;
            } else {
                var3 = var6;
            }

            if (var6 == -11) {
                ++this.m_n_err_acpt_full;
            } else if (var6 == -13) {
                ++this.m_n_err_cli_recv;
            } else if (var6 == -14) {
                ++this.m_n_err_recv_full;
            }
        }

        var2[0] = var3;
        var2[1] = var6;
        var2[2] = 8;
    }

    public boolean RetryOnErr(int var1, int var2, long var3) {
        boolean var5 = false;
        if (this.m_retry_on_network_error > 0 && var1 != 0 && (var2 == 0 || -15 <= var2 && var2 <= -11)) {
            ++this.m_n_connect;
            long var6 = System.currentTimeMillis();
            if ((long)(this.m_request_timeout * 1000) <= var6 - var3) {
                this.m_msg = "request timeout (" + this.m_n_connect + "," + this.m_request_timeout * 1000 + "," + (var6 - var3) + ") E(N:" + this.m_n_err_connect + "," + this.m_n_err_send + "," + this.m_n_err_recv + " T:" + this.m_n_err_timeout + " S:" + this.m_n_err_acpt_full + "," + this.m_n_err_cli_recv + "," + this.m_n_err_recv_full + ")";
            } else {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException var9) {
                }

                var5 = true;
            }
        }

        return var5;
    }

    public int SEND_REQUEST(String var1, int var2, byte[] var3, Socket var4) {
        int var5 = this.Connect(var1, var2, var4);
        if (var5 != 0) {
            return var5;
        } else {
            var5 = this.SendRequest(var3, var4);
            return var5 != 0 ? var5 : var5;
        }
    }

    public byte[] RECV_RESPONSE(long var1, IOPoll var3, int[] var4, Socket var5) {
        byte[] var6 = this.RecvResult(var1, var3, var5);
        if (var6 == null) {
            var4[0] = -1;
            return null;
        } else {
            this.GetResultCodes(var6, var4);
            return var4[0] >= 0 && var4[1] == 0 ? var6 : null;
        }
    }

    public byte[] SEND_AND_RECV(String var1, int var2, byte[] var3, long var4, IOPoll var6, int[] var7, Socket var8) {
        int var9 = this.SEND_REQUEST(var1, var2, var3, var8);
        if (var9 < 0) {
            var7[0] = var9;
            return null;
        } else {
            return this.RECV_RESPONSE(var4, var6, var7, var8);
        }
    }

    public int SubmitQuery(String var1, int var2, String var3, String var4, String var5, String var6, String var7, String var8, int var9, int var10, int var11, int var12) throws IOException {
        boolean var13 = false;
        boolean var14 = false;
        boolean var15 = false;
        this.ClearNetworkErrorStatistics();
        this.ClearSearchResult();
        if (var3 == null) {
            var3 = this.m_auth_code;
        }

        if (var4 == null) {
            var4 = this.m_log;
        }

        ByteArrayOutputStream var22 = new ByteArrayOutputStream();
        BA_writeInteger(var22, 0);
        BA_writeInteger(var22, 0);
        BA_writeInteger(var22, 0);
        BA_writeInteger(var22, this.m_request_priority);
        BA_writeInteger(var22, this.m_max_n_cluster);
        BA_writeInteger(var22, this.m_max_n_clu_record);
        BA_writeInteger(var22, this.m_prev_title == null ? 0 : this.m_prev_title.length);
        BA_writeInteger(var22, this.m_clu_flag);
        BA_writeInteger(var22, var9);
        BA_writeInteger(var22, var10);
        BA_writeInteger(var22, this.m_max_n_related_term);
        BA_writeInteger(var22, var11);
        BA_writeInteger(var22, var12);
        BA_writeString(var22, var3, this.m_char_enc);
        BA_writeString(var22, var4, this.m_char_enc);
        BA_writeString(var22, var5, this.m_char_enc);
        BA_writeString(var22, var6, this.m_char_enc);
        BA_writeString(var22, var7, this.m_char_enc);
        BA_writeString(var22, var8, this.m_char_enc);
        if (this.m_field_list == null) {
            this.m_field_list = "\u0000";
        }

        BA_writeString(var22, this.m_field_list);
        if (this.m_key_list == null) {
            this.m_key_list = "\u0000";
        }

        BA_writeString(var22, this.m_key_list);
        if (this.m_prev_title != null) {
            for(int var23 = 0; var23 < this.m_prev_title.length; ++var23) {
                BA_writeString(var22, this.m_prev_title[var23]);
            }
        }

        Object var39 = null;
        byte[] var40 = var22.toByteArray();
        int var24 = ALIGNED_SIZE(var40.length) - var40.length;

        int var25;
        for(var25 = 0; var25 < var24; ++var25) {
            BA_writeNull(var22);
        }

        BA_writeInteger(var22, this.m_cmd_expand_query);
        if (this.m_expand_query == null) {
            BA_writeString(var22, "\u0000");
        } else {
            BA_writeString(var22, this.m_expand_query, this.m_char_enc);
        }

        var40 = var22.toByteArray();
        var25 = var40.length;
        int var26 = var25 ^ -1446176383;
        int2bytes(var25, var40, 0);
        int2bytes(var26, var40, 4);
        int2bytes(1, var40, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var40, (byte[])null, 16);
        }

        long var16 = System.currentTimeMillis();
        IOPoll var29 = new IOPoll(var16, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
        int var30 = this.m_option_socket_retry_on_error;

        while(true) {
            while(true) {
                var13 = false;
                int var38 = 0;
                if (this.m_socket == null) {
                    this.m_socket = new Socket();
                }

                int var37 = this.SEND_REQUEST(var1, var2, var40, this.m_socket);
                if (var37 != 0) {
                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (var30-- > 0) {
                        continue;
                    }
                } else {
                    this.m_f_request_sent = 1;
                    this.m_f_request_type = 1;
                    this.m_f_response_received = 0;
                    if (this.m_option_socket_async_request > 0) {
                        return 0;
                    }

                    int[] var31 = new int[3];
                    byte[] var32 = this.RECV_RESPONSE(var16, var29, var31, this.m_socket);
                    if (var32 == null) {
                        if (var30-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }

                        var37 = var31[0];
                        var38 = var31[1];
                    } else {
                        var37 = var31[0];
                        var38 = var31[1];
                        int var33 = var31[2];
                        this.m_searchTime = bytes2int(var32, var33);
                        var33 += 4;
                        this.m_n_total = bytes2int(var32, var33);
                        var33 += 4;
                        this.m_n_row = bytes2int(var32, var33);
                        var33 += 4;
                        this.m_n_col = bytes2int(var32, var33);
                        var33 += 4;
                        this.m_record_no = new int[this.m_n_row];

                        int var34;
                        for(var34 = 0; var34 < this.m_n_row; ++var34) {
                            this.m_record_no[var34] = bytes2int(var32, var33);
                            var33 += 4;
                        }

                        this.m_score = new int[this.m_n_row];

                        for(var34 = 0; var34 < this.m_n_row; ++var34) {
                            this.m_score[var34] = bytes2int(var32, var33);
                            var33 += 4;
                        }

                        this.m_field_data = new String[this.m_n_row][this.m_n_col];
                        this.m_field_size = new int[this.m_n_row][this.m_n_col];

                        int var35;
                        for(var34 = 0; var34 < this.m_n_row; ++var34) {
                            for(var35 = 0; var35 < this.m_n_col; ++var35) {
                                int var36 = bytes2str(var32, var33);
                                this.m_field_data[var34][var35] = new String(var32, var33, var36, this.m_char_enc);
                                var33 += var36 + 1;
                            }

                            var33 += alignWordSize(var33);

                            for(var35 = 0; var35 < this.m_n_col; ++var35) {
                                this.m_field_size[var34][var35] = bytes2int(var32, var33);
                                var33 += 4;
                            }
                        }

                        var33 += alignWordSize(var33);
                        this.m_n_out_cluster = bytes2int(var32, var33);
                        var33 += 4;
                        this.m_out_rec_cnt_per_class = new int[this.m_n_out_cluster];

                        for(var34 = 0; var34 < this.m_n_out_cluster; ++var34) {
                            this.m_out_rec_cnt_per_class[var34] = bytes2int(var32, var33);
                            var33 += 4;
                        }

                        this.m_out_class_record_no = new int[this.m_n_out_cluster][];

                        for(var34 = 0; var34 < this.m_n_out_cluster; ++var34) {
                            this.m_out_class_record_no[var34] = new int[this.m_out_rec_cnt_per_class[var34]];

                            for(var35 = 0; var35 < this.m_out_rec_cnt_per_class[var34]; ++var35) {
                                this.m_out_class_record_no[var34][var35] = bytes2int(var32, var33);
                                var33 += 4;
                            }
                        }

                        this.m_cluster_title = new String[this.m_n_out_cluster];

                        for(var34 = 0; var34 < this.m_n_out_cluster; ++var34) {
                            var35 = bytes2str(var32, var33);
                            this.m_cluster_title[var34] = new String(var32, var33, var35, this.m_char_enc);
                            var33 += var35 + 1;
                        }

                        var33 += alignWordSize(var33);
                        this.m_n_rec_term = bytes2int(var32, var33);
                        var33 += 4;
                        var34 = bytes2str(var32, var33);
                        this.m_rec_term = new String(var32, var33, var34);
                        var33 += var34 + 1;
                        var24 = ALIGNED_SIZE(var33) - var33;
                        var33 += var24;
                        this.m_n_exp_k2k = bytes2int(var32, var33);
                        var33 += 4;
                        this.m_n_exp_k2e = bytes2int(var32, var33);
                        var33 += 4;
                        this.m_n_exp_e2e = bytes2int(var32, var33);
                        var33 += 4;
                        this.m_n_exp_e2k = bytes2int(var32, var33);
                        var33 += 4;
                        this.m_n_exp_trl = bytes2int(var32, var33);
                        var33 += 4;
                        this.m_n_exp_rcm = bytes2int(var32, var33);
                        var33 += 4;
                        var34 = bytes2str(var32, var33);
                        this.m_exp_k2k = new String(var32, var33, var34, this.m_char_enc);
                        var33 += var34 + 1;
                        var34 = bytes2str(var32, var33);
                        this.m_exp_k2e = new String(var32, var33, var34, this.m_char_enc);
                        var33 += var34 + 1;
                        var34 = bytes2str(var32, var33);
                        this.m_exp_e2e = new String(var32, var33, var34, this.m_char_enc);
                        var33 += var34 + 1;
                        var34 = bytes2str(var32, var33);
                        this.m_exp_e2k = new String(var32, var33, var34, this.m_char_enc);
                        var33 += var34 + 1;
                        var34 = bytes2str(var32, var33);
                        this.m_exp_trl = new String(var32, var33, var34, this.m_char_enc);
                        var33 += var34 + 1;
                        var34 = bytes2str(var32, var33);
                        this.m_exp_rcm = new String(var32, var33, var34, this.m_char_enc);
                        var33 += var34 + 1;
                        var33 += alignWordSize(var33);
                        if (var33 + 4 > var32.length) {
                            this.m_f_response_received = 1;
                        } else {
                            this.m_col_name_count = bytes2int(var32, var33);
                            var33 += 4;
                            this.m_col_name = new String[this.m_col_name_count];

                            for(var35 = 0; var35 < this.m_col_name_count; ++var35) {
                                var34 = bytes2str(var32, var33);
                                this.m_col_name[var35] = new String(var32, var33, var34, this.m_char_enc);
                                var33 += var34 + 1;
                            }

                            if ((var32.length - var33 - alignWordSize(var33)) / 4 >= 2 * this.m_n_row) {
                                var33 += alignWordSize(var33);
                                this.m_matchfield = new int[this.m_n_row];
                                this.m_userkey_idx = new int[this.m_n_row];

                                for(var35 = 0; var35 < this.m_n_row; ++var35) {
                                    this.m_matchfield[var35] = bytes2int(var32, var33);
                                    var33 += 4;
                                }

                                for(var35 = 0; var35 < this.m_n_row; ++var35) {
                                    this.m_userkey_idx[var35] = bytes2int(var32, var33);
                                    var33 += 4;
                                }
                            }

                            this.m_f_response_received = 1;
                        }
                    }
                }

                if (this.m_socket != null) {
                    this.m_socket.close();
                    this.m_socket = null;
                }

                if (!this.RetryOnErr(var37, var38, var16)) {
                    this.ClearSearchCondition();
                    return var37;
                }
            }
        }
    }

    private void EncodeHeader(byte[] var1, int var2) {
        int var3 = var1.length;
        int var4 = var3 ^ -1446176383;
        int2bytes(var3, var1, 0);
        int2bytes(var4, var1, 4);
        int2bytes(var2, var1, 8);
    }

    private void ClearIOSocks(int var1) throws IOException {
        for(; var1 >= 0; --var1) {
            if (this.m_p_io_socks != null && this.m_p_io_socks[var1] != null) {
                this.m_p_io_socks[var1].close();
                this.m_p_io_socks[var1] = null;
            }
        }

    }

    public int Hilite(String var1, String var2, String var3, int var4, int[] var5, String[] var6, String var7, String var8, String var9, String var10, int[] var11, int[] var12, String var13, int var14, int var15) throws IOException {
        boolean var16 = false;
        long var21 = System.currentTimeMillis();
        int[][] var23 = new int[1][0];
        String[][] var24 = new String[1][0];
        this.ClearNetworkErrorStatistics();
        this.parseAddrList(var24, var23, var1);
        int var20 = var24[0].length;
        if (var13 == null) {
            var13 = this.m_log;
        }

        if (this.m_p_io_hilite == null || this.m_p_io_hilite.length < var20) {
            this.m_p_io_hilite = new MIO[var20];
        }

        int var19;
        for(var19 = 0; var19 < var20; ++var19) {
            if (this.m_p_io_hilite[var19] == null) {
                this.m_p_io_hilite[var19] = new MIO();
            }

            this.m_p_io_hilite[var19].Init();
        }

        this.m_p_io_hilite[0].PUT_MODULE_NAME("KERN");
        this.m_p_io_hilite[0].PUT_FUNCTION_NAME("HILITE");
        this.m_p_io_hilite[0].PUT_USER_LOG(var13);
        int var37 = this.m_p_io_hilite[0].PUT_STR("001");
        if (var37 != 0) {
            this.m_msg = this.m_p_io_hilite[0].GetMessage();
            return -1;
        } else {
            var37 = this.m_p_io_hilite[0].PUT_STR(var2);
            if (var37 != 0) {
                this.m_msg = this.m_p_io_hilite[0].GetMessage();
                return -1;
            } else {
                var37 = this.m_p_io_hilite[0].PUT_STR(var3);
                if (var37 != 0) {
                    this.m_msg = this.m_p_io_hilite[0].GetMessage();
                    return -1;
                } else {
                    var37 = this.m_p_io_hilite[0].PUT_STR(var8);
                    if (var37 != 0) {
                        this.m_msg = this.m_p_io_hilite[0].GetMessage();
                        return -1;
                    } else {
                        var37 = this.m_p_io_hilite[0].PUT_STR(var7);
                        if (var37 != 0) {
                            this.m_msg = this.m_p_io_hilite[0].GetMessage();
                            return -1;
                        } else {
                            var37 = this.m_p_io_hilite[0].PUT_STR(this.m_p_cooked);
                            if (var37 != 0) {
                                this.m_msg = this.m_p_io_hilite[0].GetMessage();
                                return -1;
                            } else {
                                var37 = this.m_p_io_hilite[0].PUT_STR(var13);
                                if (var37 != 0) {
                                    this.m_msg = this.m_p_io_hilite[0].GetMessage();
                                    return -1;
                                } else {
                                    var37 = this.m_p_io_hilite[0].PUT_INT(var14);
                                    if (var37 != 0) {
                                        this.m_msg = this.m_p_io_hilite[0].GetMessage();
                                        return -1;
                                    } else {
                                        var37 = this.m_p_io_hilite[0].PUT_INT(var15);
                                        if (var37 != 0) {
                                            this.m_msg = this.m_p_io_hilite[0].GetMessage();
                                            return -1;
                                        } else {
                                            var37 = this.m_p_io_hilite[0].PUT_INT(var4);
                                            if (var37 != 0) {
                                                this.m_msg = this.m_p_io_hilite[0].GetMessage();
                                                return -1;
                                            } else {
                                                for(var19 = 0; var19 < var4; ++var19) {
                                                    var37 = this.m_p_io_hilite[0].PUT_STR(var6[var19]);
                                                    if (var37 != 0) {
                                                        this.m_msg = this.m_p_io_hilite[0].GetMessage();
                                                        return -1;
                                                    }

                                                    var37 = this.m_p_io_hilite[0].PUT_INT(var5[var19]);
                                                    if (var37 != 0) {
                                                        this.m_msg = this.m_p_io_hilite[0].GetMessage();
                                                        return -1;
                                                    }
                                                }

                                                byte[] var25 = this.m_p_io_hilite[0].Serialize();
                                                if (var25 == null) {
                                                    this.m_msg = this.m_io_search.GetMessage();
                                                    return var37;
                                                } else {
                                                    this.EncodeHeader(var25, 99);
                                                    long var17 = System.currentTimeMillis();
                                                    IOPoll var28 = new IOPoll(var17, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
                                                    int var29 = this.m_option_socket_retry_on_error;
                                                    if (this.m_p_io_socks == null || this.m_p_io_socks.length < var20) {
                                                        this.m_p_io_socks = new Socket[var20];
                                                    }

                                                    for(var19 = 0; var19 < var20; ++var19) {
                                                        if (this.m_p_io_socks[var19] == null) {
                                                            this.m_p_io_socks[var19] = new Socket();
                                                        }

                                                        var37 = this.SEND_REQUEST(var24[0][var19], var23[0][var19], var25, this.m_p_io_socks[var19]);
                                                        if (var37 != 0) {
                                                            if (this.m_p_io_socks[var19] != null) {
                                                                this.m_p_io_socks[var19].close();
                                                                this.m_p_io_socks[var19] = null;
                                                            }

                                                            if (var29-- > 0) {
                                                            }
                                                        }

                                                        if (var37 != 0) {
                                                            this.ClearIOSocks(var19);
                                                            return -1;
                                                        }
                                                    }

                                                    this.m_f_request_sent = 1;
                                                    this.m_f_request_type = 99;
                                                    this.m_f_response_received = 0;
                                                    Object var30 = null;

                                                    for(var19 = 0; var19 < var20; ++var19) {
                                                        int[] var31 = new int[3];
                                                        byte[] var38 = this.RECV_RESPONSE(var17, var28, var31, this.m_p_io_socks[var19]);
                                                        if (var38 == null) {
                                                            this.ClearIOSocks(var20 - 1);
                                                            return -1;
                                                        }

                                                        this.ClearIOSocks(var19);
                                                        this.m_p_io_hilite[var19].Init();
                                                        var37 = this.m_p_io_hilite[var19].Deserialize(var38);
                                                        if (var37 != 0) {
                                                            return -1;
                                                        }
                                                    }

                                                    this.m_f_response_received = 1;
                                                    RV var39 = new RV();

                                                    int var32;
                                                    int var33;
                                                    for(var19 = 0; var19 < var20; ++var19) {
                                                        String var34 = "";
                                                        this.m_p_io_hilite[var19].REWIND();
                                                        var37 = this.m_p_io_hilite[var19].GET_STR(var39);
                                                        if (var37 != 0) {
                                                            this.m_msg = this.m_p_io_hilite[var19].GetMessage();
                                                            return -1;
                                                        }

                                                        if (var39.m_str.compareTo("001") > 0) {
                                                            this.m_msg = "[HILITE] newer server protocol (" + var39.m_str + "," + "001" + ")";
                                                            return -1;
                                                        }

                                                        var37 = this.m_p_io_hilite[var19].GET_INT(var39);
                                                        if (var37 != 0) {
                                                            this.m_msg = this.m_p_io_hilite[var19].GetMessage();
                                                            return -1;
                                                        }

                                                        var33 = var39.m_int;
                                                        var37 = this.m_p_io_hilite[var19].GET_STR(var39);
                                                        if (var37 != 0) {
                                                            this.m_msg = this.m_p_io_hilite[var19].GetMessage();
                                                            return -1;
                                                        }

                                                        var34 = var39.m_str;
                                                        if (var33 != 0) {
                                                            this.m_msg = var34;
                                                            return -1;
                                                        }

                                                        var37 = this.m_p_io_hilite[var19].GET_INT(var39);
                                                        if (var37 != 0) {
                                                            this.m_msg = this.m_p_io_hilite[var19].GetMessage();
                                                            return -1;
                                                        }

                                                        this.m_col_name_count = var39.m_int;
                                                        if (this.m_n_col != 0 && this.m_n_col != this.m_col_name_count) {
                                                            this.m_msg = "schema-mismatched table exists(" + var24[0][var19] + ":" + var23[0][var19] + "," + this.m_n_col + "," + this.m_col_name_count + ")(C6874)";
                                                            return -1;
                                                        }

                                                        if (this.m_n_col != 0) {
                                                            this.m_p_io_hilite[var19].NEXT(this.m_col_name_count + 1);
                                                        } else {
                                                            this.m_n_col = this.m_col_name_count;
                                                            this.m_col_name = new String[this.m_col_name_count];

                                                            for(var32 = 0; var32 < this.m_col_name_count; ++var32) {
                                                                var37 = this.m_p_io_hilite[var19].GET_STR(var39);
                                                                if (var37 != 0) {
                                                                    this.m_msg = this.m_p_io_hilite[var19].GetMessage();
                                                                    return -1;
                                                                }

                                                                this.m_col_name[var32] = var39.m_str;
                                                            }

                                                            var37 = this.m_p_io_hilite[var19].GET_INT(var39);
                                                            if (var37 != 0) {
                                                                this.m_msg = this.m_p_io_hilite[var19].GetMessage();
                                                                return -1;
                                                            }

                                                            this.m_n_row = var39.m_int;
                                                        }
                                                    }

                                                    this.m_record_no = new int[this.m_n_row];
                                                    this.m_score = new int[this.m_n_row];
                                                    this.m_field_data = new String[this.m_n_row][this.m_n_col];
                                                    this.m_field_size = new int[this.m_n_row][this.m_n_col];

                                                    for(var19 = 0; var19 < this.m_n_row; ++var19) {
                                                        for(var32 = 0; var32 < var20; ++var32) {
                                                            boolean var40 = true;
                                                            String var35 = "";
                                                            boolean var36 = false;
                                                            if (!this.m_p_io_hilite[var32].IndexOOR()) {
                                                                var37 = this.m_p_io_hilite[var32].GET_INT(var39);
                                                                if (var37 != 0) {
                                                                    this.m_msg = this.m_p_io_hilite[var32].GetMessage();
                                                                    return -1;
                                                                }

                                                                int var41 = var39.m_int;
                                                                var37 = this.m_p_io_hilite[var32].GET_STR(var39);
                                                                if (var37 != 0) {
                                                                    this.m_msg = this.m_p_io_hilite[var32].GetMessage();
                                                                    return -1;
                                                                }

                                                                var35 = var39.m_str;
                                                                var37 = this.m_p_io_hilite[var32].GET_INT(var39);
                                                                if (var37 != 0) {
                                                                    this.m_msg = this.m_p_io_hilite[var32].GetMessage();
                                                                    return -1;
                                                                }

                                                                int var42 = var39.m_int;
                                                                if (var42 == 0) {
                                                                    this.m_p_io_hilite[var32].NEXT(this.m_n_col);
                                                                    --var32;
                                                                } else {
                                                                    if (var41 == var5[var19] && var35.equals(var6[var19])) {
                                                                        for(var33 = 0; var33 < this.m_n_col; ++var33) {
                                                                            var37 = this.m_p_io_hilite[var32].GET_INT(var39);
                                                                            if (var37 != 0) {
                                                                                this.m_msg = this.m_p_io_hilite[var32].GetMessage();
                                                                                return -1;
                                                                            }

                                                                            this.m_field_size[var19][var33] = var39.m_int;
                                                                            var37 = this.m_p_io_hilite[var32].GET_STR(var39);
                                                                            if (var37 != 0) {
                                                                                this.m_msg = this.m_p_io_hilite[var32].GetMessage();
                                                                                return -1;
                                                                            }

                                                                            this.m_field_data[var19][var33] = var39.m_str;
                                                                        }

                                                                        this.m_record_no[var19] = var41;
                                                                        break;
                                                                    }

                                                                    this.m_p_io_hilite[var32].PREV(3);
                                                                }
                                                            }
                                                        }

                                                        if (var32 == var20) {
                                                            for(var33 = 0; var33 < this.m_n_col; ++var33) {
                                                                this.m_field_size[var19][var33] = 0;
                                                                this.m_field_data[var19][var33] = "";
                                                            }
                                                        }
                                                    }

                                                    this.m_searchTime = (int)(System.currentTimeMillis() - var21);
                                                    return 0;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int IndexSearch(int[] var1, int[] var2, String[] var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, int[] var12, int[] var13, String var14, int var15, int var16, int var17, int var18) throws IOException {
        boolean var19 = false;
        long var23 = System.currentTimeMillis();
        this.ClearNetworkErrorStatistics();
        this.ClearSearchResult();
        if (var14 == null) {
            var14 = this.m_log;
        }

        if (this.m_io_search != null) {
            this.m_io_search.Init();
        } else {
            this.m_io_search = new MIO();
        }

        this.m_io_search.PUT_MODULE_NAME("KERN");
        this.m_io_search.PUT_FUNCTION_NAME("INDEX_SEARCH");
        this.m_io_search.PUT_USER_LOG(var14);
        int var34 = this.m_io_search.PUT_STR("001");
        if (var34 != 0) {
            this.m_msg = this.m_io_search.GetMessage();
            return var34;
        } else {
            var34 = this.m_io_search.PUT_STR(var6);
            if (var34 != 0) {
                this.m_msg = this.m_io_search.GetMessage();
                return var34;
            } else {
                var34 = this.m_io_search.PUT_STR(var5);
                if (var34 != 0) {
                    this.m_msg = this.m_io_search.GetMessage();
                    return var34;
                } else {
                    var34 = this.m_io_search.PUT_STR(var7);
                    if (var34 != 0) {
                        this.m_msg = this.m_io_search.GetMessage();
                        return var34;
                    } else {
                        var34 = this.m_io_search.PUT_STR(var8);
                        if (var34 != 0) {
                            this.m_msg = this.m_io_search.GetMessage();
                            return var34;
                        } else {
                            var34 = this.m_io_search.PUT_STR(var9);
                            if (var34 != 0) {
                                this.m_msg = this.m_io_search.GetMessage();
                                return var34;
                            } else {
                                var34 = this.m_io_search.PUT_STR(var14);
                                if (var34 != 0) {
                                    this.m_msg = this.m_io_search.GetMessage();
                                    return var34;
                                } else {
                                    var34 = this.m_io_search.PUT_INT(0);
                                    if (var34 != 0) {
                                        this.m_msg = this.m_io_search.GetMessage();
                                        return var34;
                                    } else {
                                        var34 = this.m_io_search.PUT_INT(0);
                                        if (var34 != 0) {
                                            this.m_msg = this.m_io_search.GetMessage();
                                            return var34;
                                        } else {
                                            var34 = this.m_io_search.PUT_INT(var15);
                                            if (var34 != 0) {
                                                this.m_msg = this.m_io_search.GetMessage();
                                                return var34;
                                            } else {
                                                var34 = this.m_io_search.PUT_INT(var16);
                                                if (var34 != 0) {
                                                    this.m_msg = this.m_io_search.GetMessage();
                                                    return var34;
                                                } else {
                                                    var34 = this.m_io_search.PUT_INT(var17);
                                                    if (var34 != 0) {
                                                        this.m_msg = this.m_io_search.GetMessage();
                                                        return var34;
                                                    } else {
                                                        var34 = this.m_io_search.PUT_INT(var18);
                                                        if (var34 != 0) {
                                                            this.m_msg = this.m_io_search.GetMessage();
                                                            return var34;
                                                        } else {
                                                            byte[] var25 = this.m_io_search.Serialize();
                                                            if (var25 == null) {
                                                                this.m_msg = this.m_io_search.GetMessage();
                                                                return var34;
                                                            } else {
                                                                this.EncodeHeader(var25, 99);
                                                                var34 = this.SetServiceAddr(var4);
                                                                if (var34 < 0) {
                                                                    this.m_msg = "serviceAddr string wrong";
                                                                    return -5;
                                                                } else {
                                                                    long var20 = System.currentTimeMillis();
                                                                    IOPoll var28 = new IOPoll(var20, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
                                                                    int var29 = this.m_option_socket_retry_on_error;
                                                                    byte[] var30 = null;
                                                                    if (this.m_socket == null) {
                                                                        this.m_socket = new Socket();
                                                                    }

                                                                    label224: {
                                                                        var34 = this.SEND_REQUEST(this.m_addr, this.m_port, var25, this.m_socket);
                                                                        if (var34 != 0) {
                                                                            if (this.m_socket != null) {
                                                                                this.m_socket.close();
                                                                                this.m_socket = null;
                                                                            }

                                                                            if (var29-- > 0) {
                                                                                break label224;
                                                                            }
                                                                        } else {
                                                                            this.m_f_request_sent = 1;
                                                                            this.m_f_request_type = 99;
                                                                            this.m_f_response_received = 0;
                                                                            int[] var31 = new int[3];
                                                                            var30 = this.RECV_RESPONSE(var20, var28, var31, this.m_socket);
                                                                            if (var30 == null && var29-- > 0) {
                                                                                if (this.m_socket != null) {
                                                                                    this.m_socket.close();
                                                                                    this.m_socket = null;
                                                                                }
                                                                                break label224;
                                                                            }
                                                                        }

                                                                        if (this.m_socket != null) {
                                                                            this.m_socket.close();
                                                                            this.m_socket = null;
                                                                        }

                                                                        if (this.RetryOnErr(var34, 0, var20)) {
                                                                        }
                                                                    }

                                                                    if (var30 == null) {
                                                                        return -5;
                                                                    } else {
                                                                        this.m_io_search.Init();
                                                                        var34 = this.m_io_search.Deserialize(var30);
                                                                        if (var34 != 0) {
                                                                            this.m_msg = this.m_io_search.GetMessage();
                                                                            return var34;
                                                                        } else {
                                                                            this.m_io_search.REWIND();
                                                                            boolean var35 = false;
                                                                            String var32 = "";
                                                                            RV var33 = new RV();
                                                                            var34 = this.m_io_search.GET_STR(var33);
                                                                            if (var34 != 0) {
                                                                                this.m_msg = this.m_io_search.GetMessage();
                                                                                return var34;
                                                                            } else if (var33.m_str.compareTo("001") > 0) {
                                                                                this.m_msg = "[INDEX_SEARCH] incompatible protocol (" + var33.m_str + "," + "001" + "(C6834)";
                                                                                return -1;
                                                                            } else {
                                                                                var34 = this.m_io_search.GET_INT(var33);
                                                                                if (var34 != 0) {
                                                                                    this.m_msg = this.m_io_search.GetMessage();
                                                                                    return -1;
                                                                                } else {
                                                                                    int var36 = var33.m_int;
                                                                                    var34 = this.m_io_search.GET_STR(var33);
                                                                                    if (var34 != 0) {
                                                                                        this.m_msg = this.m_io_search.GetMessage();
                                                                                        return -1;
                                                                                    } else {
                                                                                        var32 = var33.m_str;
                                                                                        if (var36 != 0) {
                                                                                            this.m_msg = var32;
                                                                                            return -1;
                                                                                        } else {
                                                                                            var34 = this.m_io_search.GET_STR(var33);
                                                                                            if (var34 != 0) {
                                                                                                this.m_msg = this.m_io_search.GetMessage();
                                                                                                return -1;
                                                                                            } else {
                                                                                                var34 = this.m_io_search.GET_INT(var33);
                                                                                                if (var34 != 0) {
                                                                                                    this.m_msg = this.m_io_search.GetMessage();
                                                                                                    return -1;
                                                                                                } else {
                                                                                                    var34 = this.m_io_search.GET_INT(var33);
                                                                                                    if (var34 != 0) {
                                                                                                        this.m_msg = this.m_io_search.GetMessage();
                                                                                                        return -1;
                                                                                                    } else {
                                                                                                        this.m_n_total = var33.m_int;
                                                                                                        var34 = this.m_io_search.GET_INT(var33);
                                                                                                        if (var34 != 0) {
                                                                                                            this.m_msg = this.m_io_search.GetMessage();
                                                                                                            return -1;
                                                                                                        } else {
                                                                                                            this.m_n_row = var33.m_int;
                                                                                                            var34 = this.m_io_search.GET_STR(var33);
                                                                                                            if (var34 != 0) {
                                                                                                                this.m_msg = this.m_io_search.GetMessage();
                                                                                                                return -1;
                                                                                                            } else {
                                                                                                                this.m_p_cooked = var33.m_str;
                                                                                                                var34 = this.m_io_search.GET_INT(var33);
                                                                                                                if (var34 != 0) {
                                                                                                                    this.m_msg = this.m_io_search.GetMessage();
                                                                                                                    return -1;
                                                                                                                } else {
                                                                                                                    this.m_group_key_count = var33.m_int;
                                                                                                                    this.m_group_count = 0;
                                                                                                                    if (this.m_group_key_count > 0) {
                                                                                                                        this.m_group_count = this.m_n_row;
                                                                                                                    }

                                                                                                                    var34 = this.m_io_search.GET_INT(var33);
                                                                                                                    if (var34 != 0) {
                                                                                                                        this.m_msg = this.m_io_search.GetMessage();
                                                                                                                        return -1;
                                                                                                                    } else {
                                                                                                                        var34 = this.m_io_search.GET_STR(var33);
                                                                                                                        if (var34 != 0) {
                                                                                                                            this.m_msg = this.m_io_search.GetMessage();
                                                                                                                            return -1;
                                                                                                                        } else {
                                                                                                                            var34 = this.m_io_search.GET_STR(var33);
                                                                                                                            if (var34 != 0) {
                                                                                                                                this.m_msg = this.m_io_search.GetMessage();
                                                                                                                                return -1;
                                                                                                                            } else {
                                                                                                                                if (this.m_group_count > 0) {
                                                                                                                                    this.m_group_size = new int[this.m_group_count];
                                                                                                                                    this.m_group_key_val = new String[this.m_group_count][this.m_group_key_count];
                                                                                                                                }

                                                                                                                                var1[0] = this.m_n_row;

                                                                                                                                for(int var22 = 0; var22 < this.m_n_row; ++var22) {
                                                                                                                                    var34 = this.m_io_search.GET_STR(var33);
                                                                                                                                    if (var34 != 0) {
                                                                                                                                        this.m_msg = this.m_io_search.GetMessage();
                                                                                                                                        return -1;
                                                                                                                                    }

                                                                                                                                    var3[var22] = var33.m_str;
                                                                                                                                    var34 = this.m_io_search.GET_INT(var33);
                                                                                                                                    if (var34 != 0) {
                                                                                                                                        this.m_msg = this.m_io_search.GetMessage();
                                                                                                                                        return -1;
                                                                                                                                    }

                                                                                                                                    var2[var22] = var33.m_int;
                                                                                                                                    var34 = this.m_io_search.GET_INT(var33);
                                                                                                                                    if (var34 != 0) {
                                                                                                                                        this.m_msg = this.m_io_search.GetMessage();
                                                                                                                                        return -1;
                                                                                                                                    }

                                                                                                                                    var34 = this.m_io_search.GET_INT(var33);
                                                                                                                                    if (var34 != 0) {
                                                                                                                                        this.m_msg = this.m_io_search.GetMessage();
                                                                                                                                        return -1;
                                                                                                                                    }

                                                                                                                                    var34 = this.m_io_search.GET_INT(var33);
                                                                                                                                    if (var34 != 0) {
                                                                                                                                        this.m_msg = this.m_io_search.GetMessage();
                                                                                                                                        return -1;
                                                                                                                                    }

                                                                                                                                    var34 = this.m_io_search.GET_STR(var33);
                                                                                                                                    if (var34 != 0) {
                                                                                                                                        this.m_msg = this.m_io_search.GetMessage();
                                                                                                                                        return -1;
                                                                                                                                    }

                                                                                                                                    if (this.m_group_count > 0) {
                                                                                                                                        this.m_group_key_val[var22][0] = var33.m_str;
                                                                                                                                    }

                                                                                                                                    var34 = this.m_io_search.GET_INT(var33);
                                                                                                                                    if (var34 != 0) {
                                                                                                                                        this.m_msg = this.m_io_search.GetMessage();
                                                                                                                                        return -1;
                                                                                                                                    }

                                                                                                                                    if (this.m_group_count > 0) {
                                                                                                                                        this.m_group_size[var22] = var33.m_int;
                                                                                                                                    }
                                                                                                                                }

                                                                                                                                this.m_f_response_received = 1;
                                                                                                                                this.m_searchTime = (int)(System.currentTimeMillis() - var23);
                                                                                                                                this.ClearSearchCondition();
                                                                                                                                return 0;
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int Search(String var1, String var2, String var3, String var4, String var5, String var6, int var7, int var8, int var9, int var10) throws IOException {
        boolean var11 = false;
        boolean var12 = false;
        boolean var13 = false;
        this.ClearNetworkErrorStatistics();
        this.ClearSearchResult();
        String var20 = this.m_auth_code;
        if (var20 == null) {
            var20 = "";
        }

        if (var6 == null) {
            var6 = this.m_log;
        }

        ByteArrayOutputStream var21 = new ByteArrayOutputStream();
        BA_writeInteger(var21, 0);
        BA_writeInteger(var21, 0);
        BA_writeInteger(var21, 0);
        BA_writeInteger(var21, this.m_request_priority);

        for(int var22 = 0; var22 < 16; ++var22) {
            BA_writeNull(var21);
        }

        BA_writeInteger(var21, var7);
        BA_writeInteger(var21, var8);
        BA_writeInteger(var21, var9);
        BA_writeInteger(var21, var10);
        BA_writeString(var21, var20, this.m_char_enc);
        BA_writeString(var21, var2, this.m_char_enc);
        BA_writeString(var21, var3, this.m_char_enc);
        BA_writeString(var21, var4, this.m_char_enc);
        BA_writeString(var21, var5, this.m_char_enc);
        BA_writeString(var21, var6, this.m_char_enc);
        Object var39 = null;
        byte[] var40 = var21.toByteArray();
        int var23 = ALIGNED_SIZE(var40.length) - var40.length;

        int var24;
        for(var24 = 0; var24 < var23; ++var24) {
            BA_writeNull(var21);
        }

        BA_writeInteger(var21, this.m_cmd_expand_query);
        if (this.m_expand_query == null) {
            BA_writeString(var21, "\u0000");
        } else {
            BA_writeString(var21, this.m_expand_query, this.m_char_enc);
        }

        var40 = var21.toByteArray();
        var24 = var40.length;
        int var25 = var24 ^ -1446176383;
        int2bytes(var24, var40, 0);
        int2bytes(var25, var40, 4);
        int2bytes(35, var40, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var40, (byte[])null, 16);
        }

        int var28 = this.SetServiceAddr(var1);
        if (var28 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var14 = System.currentTimeMillis();
            IOPoll var29 = new IOPoll(var14, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var30 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var11 = false;
                    int var38 = 0;
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    int var37 = this.SEND_REQUEST(this.m_addr, this.m_port, var40, this.m_socket);
                    if (var37 != 0) {
                        if (this.m_socket != null) {
                            this.m_socket.close();
                            this.m_socket = null;
                        }

                        if (var30-- > 0) {
                            continue;
                        }
                    } else {
                        this.m_f_request_sent = 1;
                        this.m_f_request_type = 35;
                        this.m_f_response_received = 0;
                        if (this.m_option_socket_async_request > 0) {
                            return 0;
                        }

                        int[] var31 = new int[3];
                        byte[] var32 = this.RECV_RESPONSE(var14, var29, var31, this.m_socket);
                        if (var32 == null) {
                            if (var30-- > 0) {
                                if (this.m_socket != null) {
                                    this.m_socket.close();
                                    this.m_socket = null;
                                }
                                continue;
                            }

                            var37 = var31[0];
                            var38 = var31[1];
                        } else {
                            var37 = var31[0];
                            var38 = var31[1];
                            int var33 = var31[2];
                            this.m_searchTime = bytes2int(var32, var33);
                            var33 += 4;
                            this.m_n_total = bytes2int(var32, var33);
                            var33 += 4;
                            this.m_n_row = bytes2int(var32, var33);
                            var33 += 4;
                            this.m_n_col = bytes2int(var32, var33);
                            var33 += 4;
                            this.m_record_no = new int[this.m_n_row];

                            int var34;
                            for(var34 = 0; var34 < this.m_n_row; ++var34) {
                                this.m_record_no[var34] = bytes2int(var32, var33);
                                var33 += 4;
                            }

                            this.m_score = new int[this.m_n_row];

                            for(var34 = 0; var34 < this.m_n_row; ++var34) {
                                this.m_score[var34] = bytes2int(var32, var33);
                                var33 += 4;
                            }

                            this.m_field_data = new String[this.m_n_row][this.m_n_col];
                            this.m_field_size = new int[this.m_n_row][this.m_n_col];

                            int var35;
                            int var36;
                            for(var34 = 0; var34 < this.m_n_row; ++var34) {
                                for(var35 = 0; var35 < this.m_n_col; ++var35) {
                                    var36 = bytes2str(var32, var33);
                                    this.m_field_data[var34][var35] = new String(var32, var33, var36, this.m_char_enc);
                                    var33 += var36 + 1;
                                }

                                var33 += alignWordSize(var33);

                                for(var35 = 0; var35 < this.m_n_col; ++var35) {
                                    this.m_field_size[var34][var35] = bytes2int(var32, var33);
                                    var33 += 4;
                                }
                            }

                            var33 += alignWordSize(var33);
                            this.m_n_out_cluster = bytes2int(var32, var33);
                            var33 += 4;
                            this.m_out_rec_cnt_per_class = new int[this.m_n_out_cluster];

                            for(var34 = 0; var34 < this.m_n_out_cluster; ++var34) {
                                this.m_out_rec_cnt_per_class[var34] = bytes2int(var32, var33);
                                var33 += 4;
                            }

                            this.m_out_class_record_no = new int[this.m_n_out_cluster][];

                            for(var34 = 0; var34 < this.m_n_out_cluster; ++var34) {
                                this.m_out_class_record_no[var34] = new int[this.m_out_rec_cnt_per_class[var34]];

                                for(var35 = 0; var35 < this.m_out_rec_cnt_per_class[var34]; ++var35) {
                                    this.m_out_class_record_no[var34][var35] = bytes2int(var32, var33);
                                    var33 += 4;
                                }
                            }

                            this.m_cluster_title = new String[this.m_n_out_cluster];

                            for(var34 = 0; var34 < this.m_n_out_cluster; ++var34) {
                                var35 = bytes2str(var32, var33);
                                this.m_cluster_title[var34] = new String(var32, var33, var35, this.m_char_enc);
                                var33 += var35 + 1;
                            }

                            var33 += alignWordSize(var33);
                            this.m_n_rec_term = bytes2int(var32, var33);
                            var33 += 4;
                            var34 = bytes2str(var32, var33);
                            this.m_rec_term = new String(var32, var33, var34, this.m_char_enc);
                            var33 += var34 + 1;
                            var23 = ALIGNED_SIZE(var33) - var33;
                            var33 += var23;
                            this.m_n_exp_k2k = bytes2int(var32, var33);
                            var33 += 4;
                            this.m_n_exp_k2e = bytes2int(var32, var33);
                            var33 += 4;
                            this.m_n_exp_e2e = bytes2int(var32, var33);
                            var33 += 4;
                            this.m_n_exp_e2k = bytes2int(var32, var33);
                            var33 += 4;
                            this.m_n_exp_trl = bytes2int(var32, var33);
                            var33 += 4;
                            this.m_n_exp_rcm = bytes2int(var32, var33);
                            var33 += 4;
                            var34 = bytes2str(var32, var33);
                            this.m_exp_k2k = new String(var32, var33, var34, this.m_char_enc);
                            var33 += var34 + 1;
                            var34 = bytes2str(var32, var33);
                            this.m_exp_k2e = new String(var32, var33, var34, this.m_char_enc);
                            var33 += var34 + 1;
                            var34 = bytes2str(var32, var33);
                            this.m_exp_e2e = new String(var32, var33, var34, this.m_char_enc);
                            var33 += var34 + 1;
                            var34 = bytes2str(var32, var33);
                            this.m_exp_e2k = new String(var32, var33, var34, this.m_char_enc);
                            var33 += var34 + 1;
                            var34 = bytes2str(var32, var33);
                            this.m_exp_trl = new String(var32, var33, var34, this.m_char_enc);
                            var33 += var34 + 1;
                            var34 = bytes2str(var32, var33);
                            this.m_exp_rcm = new String(var32, var33, var34, this.m_char_enc);
                            var33 += var34 + 1;
                            var33 += alignWordSize(var33);
                            if (var33 + 4 > var32.length) {
                                this.m_f_response_received = 1;
                            } else {
                                this.m_col_name_count = bytes2int(var32, var33);
                                var33 += 4;
                                this.m_col_name = new String[this.m_col_name_count];

                                for(var35 = 0; var35 < this.m_col_name_count; ++var35) {
                                    var34 = bytes2str(var32, var33);
                                    this.m_col_name[var35] = new String(var32, var33, var34, this.m_char_enc);
                                    var33 += var34 + 1;
                                }

                                var33 += alignWordSize(var33);
                                if (var33 + 4 > var32.length) {
                                    this.m_f_response_received = 1;
                                } else {
                                    this.m_union_table_count = bytes2int(var32, var33);
                                    var33 += 4;
                                    this.m_union_table_name = new String[this.m_union_table_count][3];

                                    for(var35 = 0; var35 < this.m_union_table_count; ++var35) {
                                        for(var36 = 0; var36 < 3; ++var36) {
                                            var34 = bytes2str(var32, var33);
                                            this.m_union_table_name[var35][var36] = new String(var32, var33, var34, this.m_char_enc);
                                            var33 += var34 + 1;
                                        }
                                    }

                                    var33 += alignWordSize(var33);
                                    this.m_tabno_count = bytes2int(var32, var33);
                                    var33 += 4;
                                    this.m_tabno = new int[this.m_tabno_count];

                                    for(var35 = 0; var35 < this.m_tabno_count; ++var35) {
                                        this.m_tabno[var35] = bytes2int(var32, var33);
                                        var33 += 4;
                                    }

                                    var33 += alignWordSize(var33);
                                    this.m_group_count = bytes2int(var32, var33);
                                    var33 += 4;
                                    this.m_group_key_count = bytes2int(var32, var33);
                                    var33 += 4;
                                    this.m_group_size = new int[this.m_group_count];
                                    this.m_group_key_val = new String[this.m_group_count][this.m_group_key_count];

                                    for(var35 = 0; var35 < this.m_group_count; ++var35) {
                                        this.m_group_size[var35] = bytes2int(var32, var33);
                                        var33 += 4;
                                    }

                                    for(var35 = 0; var35 < this.m_group_count; ++var35) {
                                        for(var36 = 0; var36 < this.m_group_key_count; ++var36) {
                                            var34 = bytes2str(var32, var33);
                                            this.m_group_key_val[var35][var36] = new String(var32, var33, var34, this.m_char_enc);
                                            var33 += var34 + 1;
                                        }
                                    }

                                    var33 += alignWordSize(var33);
                                    this.m_f_response_received = 1;
                                }
                            }
                        }
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var37, var38, var14)) {
                        this.ClearSearchCondition();
                        return var37;
                    }
                }
            }
        }
    }

    public int GetResponse() throws IOException {
        boolean var1 = false;
        boolean var2 = false;
        boolean var3 = false;
        if (this.m_f_response_received > 0) {
            return 0;
        } else if (this.m_option_socket_async_request == 0) {
            this.m_msg = "asyncronous request option not set (C013080)";
            return -1;
        } else if (this.m_f_request_sent == 0) {
            this.m_msg = "request not submitted (C013081)";
            return -1;
        } else {
            boolean var11 = false;
            boolean var12 = false;
            long var4 = System.currentTimeMillis();
            IOPoll var13 = new IOPoll(var4, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);

            int var20;
            int var21;
            do {
                var1 = false;
                var2 = false;
                if (this.m_socket == null) {
                    var1 = true;
                    throw new IOException("null socket exception. pending error message: '" + this.m_msg + "'");
                }

                int[] var14 = new int[3];
                byte[] var15 = this.RECV_RESPONSE(var4, var13, var14, this.m_socket);
                if (var15 == null) {
                    var20 = var14[0];
                    var21 = var14[1];
                } else {
                    var20 = var14[0];
                    var21 = var14[1];
                    int var16 = var14[2];
                    this.m_searchTime = bytes2int(var15, var16);
                    var16 += 4;
                    this.m_n_total = bytes2int(var15, var16);
                    var16 += 4;
                    this.m_n_row = bytes2int(var15, var16);
                    var16 += 4;
                    this.m_n_col = bytes2int(var15, var16);
                    var16 += 4;
                    this.m_record_no = new int[this.m_n_row];

                    int var17;
                    for(var17 = 0; var17 < this.m_n_row; ++var17) {
                        this.m_record_no[var17] = bytes2int(var15, var16);
                        var16 += 4;
                    }

                    this.m_score = new int[this.m_n_row];

                    for(var17 = 0; var17 < this.m_n_row; ++var17) {
                        this.m_score[var17] = bytes2int(var15, var16);
                        var16 += 4;
                    }

                    this.m_field_data = new String[this.m_n_row][this.m_n_col];
                    this.m_field_size = new int[this.m_n_row][this.m_n_col];
                    var17 = 0;

                    while(true) {
                        int var18;
                        int var23;
                        if (var17 >= this.m_n_row) {
                            var16 += alignWordSize(var16);
                            this.m_n_out_cluster = bytes2int(var15, var16);
                            var16 += 4;
                            this.m_out_rec_cnt_per_class = new int[this.m_n_out_cluster];

                            for(var17 = 0; var17 < this.m_n_out_cluster; ++var17) {
                                this.m_out_rec_cnt_per_class[var17] = bytes2int(var15, var16);
                                var16 += 4;
                            }

                            this.m_out_class_record_no = new int[this.m_n_out_cluster][];

                            for(var17 = 0; var17 < this.m_n_out_cluster; ++var17) {
                                this.m_out_class_record_no[var17] = new int[this.m_out_rec_cnt_per_class[var17]];

                                for(var18 = 0; var18 < this.m_out_rec_cnt_per_class[var17]; ++var18) {
                                    this.m_out_class_record_no[var17][var18] = bytes2int(var15, var16);
                                    var16 += 4;
                                }
                            }

                            this.m_cluster_title = new String[this.m_n_out_cluster];

                            for(var17 = 0; var17 < this.m_n_out_cluster; ++var17) {
                                var23 = bytes2str(var15, var16);
                                this.m_cluster_title[var17] = new String(var15, var16, var23, this.m_char_enc);
                                var16 += var23 + 1;
                            }

                            var16 += alignWordSize(var16);
                            this.m_n_rec_term = bytes2int(var15, var16);
                            var16 += 4;
                            var23 = bytes2str(var15, var16);
                            this.m_rec_term = new String(var15, var16, var23, this.m_char_enc);
                            var16 += var23 + 1;
                            int var22 = ALIGNED_SIZE(var16) - var16;
                            var16 += var22;
                            this.m_n_exp_k2k = bytes2int(var15, var16);
                            var16 += 4;
                            this.m_n_exp_k2e = bytes2int(var15, var16);
                            var16 += 4;
                            this.m_n_exp_e2e = bytes2int(var15, var16);
                            var16 += 4;
                            this.m_n_exp_e2k = bytes2int(var15, var16);
                            var16 += 4;
                            this.m_n_exp_trl = bytes2int(var15, var16);
                            var16 += 4;
                            this.m_n_exp_rcm = bytes2int(var15, var16);
                            var16 += 4;
                            var23 = bytes2str(var15, var16);
                            this.m_exp_k2k = new String(var15, var16, var23, this.m_char_enc);
                            var16 += var23 + 1;
                            var23 = bytes2str(var15, var16);
                            this.m_exp_k2e = new String(var15, var16, var23, this.m_char_enc);
                            var16 += var23 + 1;
                            var23 = bytes2str(var15, var16);
                            this.m_exp_e2e = new String(var15, var16, var23, this.m_char_enc);
                            var16 += var23 + 1;
                            var23 = bytes2str(var15, var16);
                            this.m_exp_e2k = new String(var15, var16, var23, this.m_char_enc);
                            var16 += var23 + 1;
                            var23 = bytes2str(var15, var16);
                            this.m_exp_trl = new String(var15, var16, var23, this.m_char_enc);
                            var16 += var23 + 1;
                            var23 = bytes2str(var15, var16);
                            this.m_exp_rcm = new String(var15, var16, var23, this.m_char_enc);
                            var16 += var23 + 1;
                            var16 += alignWordSize(var16);
                            this.m_col_name_count = bytes2int(var15, var16);
                            var16 += 4;
                            this.m_col_name = new String[this.m_col_name_count];

                            for(var17 = 0; var17 < this.m_col_name_count; ++var17) {
                                var23 = bytes2str(var15, var16);
                                this.m_col_name[var17] = new String(var15, var16, var23, this.m_char_enc);
                                var16 += var23 + 1;
                            }

                            if (this.m_f_request_type == 1) {
                                if ((var15.length - var16 - alignWordSize(var16)) / 4 >= 2 * this.m_n_row) {
                                    var16 += alignWordSize(var16);
                                    this.m_matchfield = new int[this.m_n_row];
                                    this.m_userkey_idx = new int[this.m_n_row];

                                    for(var17 = 0; var17 < this.m_n_row; ++var17) {
                                        this.m_matchfield[var17] = bytes2int(var15, var16);
                                        var16 += 4;
                                    }

                                    for(var17 = 0; var17 < this.m_n_row; ++var17) {
                                        this.m_userkey_idx[var17] = bytes2int(var15, var16);
                                        var16 += 4;
                                    }
                                }
                            } else if (this.m_f_request_type == 35) {
                                var16 += alignWordSize(var16);
                                if (var16 + 4 > var15.length) {
                                    this.m_f_response_received = 1;
                                    break;
                                }

                                var23 = bytes2str(var15, var16);
                                new String(var15, var16, var23, this.m_char_enc);
                                this.m_union_table_count = bytes2int(var15, var16);
                                var16 += 4;
                                this.m_union_table_name = new String[this.m_union_table_count][3];

                                int var19;
                                for(var18 = 0; var18 < this.m_union_table_count; ++var18) {
                                    for(var19 = 0; var19 < 3; ++var19) {
                                        var23 = bytes2str(var15, var16);
                                        this.m_union_table_name[var18][var19] = new String(var15, var16, var23, this.m_char_enc);
                                        var16 += var23 + 1;
                                    }
                                }

                                var16 += alignWordSize(var16);
                                this.m_tabno_count = bytes2int(var15, var16);
                                var16 += 4;
                                this.m_tabno = new int[this.m_tabno_count];

                                for(var18 = 0; var18 < this.m_tabno_count; ++var18) {
                                    this.m_tabno[var18] = bytes2int(var15, var16);
                                    var16 += 4;
                                }

                                var16 += alignWordSize(var16);
                                this.m_group_count = bytes2int(var15, var16);
                                var16 += 4;
                                this.m_group_key_count = bytes2int(var15, var16);
                                var16 += 4;
                                this.m_group_size = new int[this.m_group_count];
                                this.m_group_key_val = new String[this.m_group_count][this.m_group_key_count];

                                for(var18 = 0; var18 < this.m_group_count; ++var18) {
                                    this.m_group_size[var18] = bytes2int(var15, var16);
                                    var16 += 4;
                                }

                                for(var18 = 0; var18 < this.m_group_count; ++var18) {
                                    for(var19 = 0; var19 < this.m_group_key_count; ++var19) {
                                        var23 = bytes2str(var15, var16);
                                        this.m_group_key_val[var18][var19] = new String(var15, var16, var23, this.m_char_enc);
                                        var16 += var23 + 1;
                                    }
                                }

                                var16 += alignWordSize(var16);
                            }

                            this.m_f_response_received = 1;
                            break;
                        }

                        for(var18 = 0; var18 < this.m_n_col; ++var18) {
                            var23 = bytes2str(var15, var16);
                            this.m_field_data[var17][var18] = new String(var15, var16, var23, this.m_char_enc);
                            var16 += var23 + 1;
                        }

                        var16 += alignWordSize(var16);

                        for(var18 = 0; var18 < this.m_n_col; ++var18) {
                            this.m_field_size[var17][var18] = bytes2int(var15, var16);
                            var16 += 4;
                        }

                        ++var17;
                    }
                }

                if (this.m_socket != null) {
                    this.m_socket.close();
                    this.m_socket = null;
                }
            } while(this.RetryOnErr(var20, var21, var4));

            this.ClearSearchCondition();
            return var20;
        }
    }

    public int Select(String var1, String var2, String var3, String var4, String var5, int var6, int var7, int var8, int var9) throws IOException {
        boolean var10 = false;
        boolean var11 = false;
        boolean var12 = false;
        this.ClearNetworkErrorStatistics();
        this.ClearSearchResult();
        ByteArrayOutputStream var19 = new ByteArrayOutputStream();
        BA_writeInteger(var19, 0);
        BA_writeInteger(var19, 0);
        BA_writeInteger(var19, 0);
        BA_writeInteger(var19, this.m_request_priority);
        BA_writeInteger(var19, this.m_max_n_cluster);
        BA_writeInteger(var19, this.m_max_n_clu_record);
        BA_writeInteger(var19, this.m_prev_title == null ? 0 : this.m_prev_title.length);
        BA_writeInteger(var19, this.m_clu_flag);
        BA_writeInteger(var19, var6);
        BA_writeInteger(var19, var7);
        BA_writeInteger(var19, this.m_max_n_related_term);
        BA_writeInteger(var19, var8);
        BA_writeInteger(var19, var9);
        if (this.m_auth_code == null) {
            this.m_auth_code = "";
        }

        if (this.m_log == null) {
            this.m_log = "";
        }

        BA_writeString(var19, this.m_auth_code, this.m_char_enc);
        BA_writeString(var19, this.m_log, this.m_char_enc);
        BA_writeString(var19, var2, this.m_char_enc);
        BA_writeString(var19, var3, this.m_char_enc);
        BA_writeString(var19, var4, this.m_char_enc);
        BA_writeString(var19, var5, this.m_char_enc);
        if (this.m_field_list == null) {
            this.m_field_list = "\u0000";
        }

        BA_writeString(var19, this.m_field_list);
        if (this.m_key_list == null) {
            this.m_key_list = "\u0000";
        }

        BA_writeString(var19, this.m_key_list);
        if (this.m_prev_title != null) {
            for(int var20 = 0; var20 < this.m_prev_title.length; ++var20) {
                BA_writeString(var19, this.m_prev_title[var20]);
            }
        }

        Object var36 = null;
        byte[] var37 = var19.toByteArray();
        int var21 = ALIGNED_SIZE(var37.length) - var37.length;

        int var22;
        for(var22 = 0; var22 < var21; ++var22) {
            BA_writeNull(var19);
        }

        BA_writeInteger(var19, this.m_cmd_expand_query);
        if (this.m_expand_query == null) {
            BA_writeString(var19, "\u0000");
        } else {
            BA_writeString(var19, this.m_expand_query, this.m_char_enc);
        }

        var37 = var19.toByteArray();
        var22 = var37.length;
        int var23 = var22 ^ -1446176383;
        int2bytes(var22, var37, 0);
        int2bytes(var23, var37, 4);
        int2bytes(1, var37, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var37, (byte[])null, 16);
        }

        int var34 = this.SetServiceAddr(var1);
        if (var34 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var13 = System.currentTimeMillis();
            IOPoll var26 = new IOPoll(var13, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var27 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var10 = false;
                    int var35 = 0;
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    var34 = this.SEND_REQUEST(this.m_addr, this.m_port, var37, this.m_socket);
                    if (var34 != 0) {
                        if (this.m_socket != null) {
                            this.m_socket.close();
                            this.m_socket = null;
                        }

                        if (var27-- > 0) {
                            continue;
                        }
                    } else {
                        this.m_f_request_sent = 1;
                        this.m_f_request_type = 1;
                        this.m_f_response_received = 0;
                        if (this.m_option_socket_async_request > 0) {
                            return 0;
                        }

                        int[] var28 = new int[3];
                        byte[] var29 = this.RECV_RESPONSE(var13, var26, var28, this.m_socket);
                        if (var29 == null) {
                            if (var27-- > 0) {
                                if (this.m_socket != null) {
                                    this.m_socket.close();
                                    this.m_socket = null;
                                }
                                continue;
                            }

                            var34 = var28[0];
                            var35 = var28[1];
                        } else {
                            var34 = var28[0];
                            var35 = var28[1];
                            int var30 = var28[2];
                            this.m_searchTime = bytes2int(var29, var30);
                            var30 += 4;
                            this.m_n_total = bytes2int(var29, var30);
                            var30 += 4;
                            this.m_n_row = bytes2int(var29, var30);
                            var30 += 4;
                            this.m_n_col = bytes2int(var29, var30);
                            var30 += 4;
                            this.m_record_no = new int[this.m_n_row];

                            int var31;
                            for(var31 = 0; var31 < this.m_n_row; ++var31) {
                                this.m_record_no[var31] = bytes2int(var29, var30);
                                var30 += 4;
                            }

                            this.m_score = new int[this.m_n_row];

                            for(var31 = 0; var31 < this.m_n_row; ++var31) {
                                this.m_score[var31] = bytes2int(var29, var30);
                                var30 += 4;
                            }

                            this.m_field_data = new String[this.m_n_row][this.m_n_col];
                            this.m_field_size = new int[this.m_n_row][this.m_n_col];

                            int var32;
                            for(var31 = 0; var31 < this.m_n_row; ++var31) {
                                for(var32 = 0; var32 < this.m_n_col; ++var32) {
                                    int var33 = bytes2str(var29, var30);
                                    this.m_field_data[var31][var32] = new String(var29, var30, var33, this.m_char_enc);
                                    var30 += var33 + 1;
                                }

                                var30 += alignWordSize(var30);

                                for(var32 = 0; var32 < this.m_n_col; ++var32) {
                                    this.m_field_size[var31][var32] = bytes2int(var29, var30);
                                    var30 += 4;
                                }
                            }

                            var30 += alignWordSize(var30);
                            this.m_n_out_cluster = bytes2int(var29, var30);
                            var30 += 4;
                            this.m_out_rec_cnt_per_class = new int[this.m_n_out_cluster];

                            for(var31 = 0; var31 < this.m_n_out_cluster; ++var31) {
                                this.m_out_rec_cnt_per_class[var31] = bytes2int(var29, var30);
                                var30 += 4;
                            }

                            this.m_out_class_record_no = new int[this.m_n_out_cluster][];

                            for(var31 = 0; var31 < this.m_n_out_cluster; ++var31) {
                                this.m_out_class_record_no[var31] = new int[this.m_out_rec_cnt_per_class[var31]];

                                for(var32 = 0; var32 < this.m_out_rec_cnt_per_class[var31]; ++var32) {
                                    this.m_out_class_record_no[var31][var32] = bytes2int(var29, var30);
                                    var30 += 4;
                                }
                            }

                            this.m_cluster_title = new String[this.m_n_out_cluster];

                            for(var31 = 0; var31 < this.m_n_out_cluster; ++var31) {
                                var32 = bytes2str(var29, var30);
                                this.m_cluster_title[var31] = new String(var29, var30, var32, this.m_char_enc);
                                var30 += var32 + 1;
                            }

                            var30 += alignWordSize(var30);
                            this.m_n_rec_term = bytes2int(var29, var30);
                            var30 += 4;
                            var31 = bytes2str(var29, var30);
                            this.m_rec_term = new String(var29, var30, var31, this.m_char_enc);
                            var30 += var31 + 1;
                            var21 = ALIGNED_SIZE(var30) - var30;
                            var30 += var21;
                            this.m_n_exp_k2k = bytes2int(var29, var30);
                            var30 += 4;
                            this.m_n_exp_k2e = bytes2int(var29, var30);
                            var30 += 4;
                            this.m_n_exp_e2e = bytes2int(var29, var30);
                            var30 += 4;
                            this.m_n_exp_e2k = bytes2int(var29, var30);
                            var30 += 4;
                            this.m_n_exp_trl = bytes2int(var29, var30);
                            var30 += 4;
                            this.m_n_exp_rcm = bytes2int(var29, var30);
                            var30 += 4;
                            var31 = bytes2str(var29, var30);
                            this.m_exp_k2k = new String(var29, var30, var31);
                            var30 += var31 + 1;
                            var31 = bytes2str(var29, var30);
                            this.m_exp_k2e = new String(var29, var30, var31);
                            var30 += var31 + 1;
                            var31 = bytes2str(var29, var30);
                            this.m_exp_e2e = new String(var29, var30, var31);
                            var30 += var31 + 1;
                            var31 = bytes2str(var29, var30);
                            this.m_exp_e2k = new String(var29, var30, var31);
                            var30 += var31 + 1;
                            var31 = bytes2str(var29, var30);
                            this.m_exp_trl = new String(var29, var30, var31);
                            var30 += var31 + 1;
                            var31 = bytes2str(var29, var30);
                            this.m_exp_rcm = new String(var29, var30, var31);
                            var30 += var31 + 1;
                            var30 += alignWordSize(var30);
                            this.m_col_name_count = bytes2int(var29, var30);
                            var30 += 4;
                            this.m_col_name = new String[this.m_col_name_count];

                            for(var32 = 0; var32 < this.m_col_name_count; ++var32) {
                                var31 = bytes2str(var29, var30);
                                this.m_col_name[var32] = new String(var29, var30, var31, this.m_char_enc);
                                var30 += var31 + 1;
                            }

                            this.m_f_response_received = 1;
                        }
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var34, var35, var13)) {
                        this.ClearSearchCondition();
                        return var34;
                    }
                }
            }
        }
    }

    public int Insert(String var1, String var2, String[] var3, String[] var4, int[] var5, int var6, int var7, int var8, int var9) throws IOException {
        boolean var11 = false;
        boolean var12 = false;
        boolean var13 = false;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var20 = new ByteArrayOutputStream();
        BA_writeInteger(var20, 0);
        BA_writeInteger(var20, 0);
        BA_writeInteger(var20, 0);
        BA_writeInteger(var20, this.m_request_priority);
        BA_writeInteger(var20, var7);
        BA_writeInteger(var20, var8);
        BA_writeInteger(var20, var6);

        int var10;
        for(var10 = 0; var10 < var6; ++var10) {
            BA_writeInteger(var20, var5[var10]);
        }

        BA_writeString(var20, this.m_auth_code);
        BA_writeString(var20, this.m_log);
        BA_writeString(var20, var2);

        for(var10 = 0; var10 < var6; ++var10) {
            BA_writeString(var20, var3[var10]);
            BA_writeString(var20, var4[var10], this.m_char_enc);
        }

        byte[] var21 = var20.toByteArray();
        int var22 = var21.length;
        int var23 = var22 ^ -1446176383;
        int2bytes(var22, var21, 0);
        int2bytes(var23, var21, 4);
        int2bytes(32, var21, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var21, (byte[])null, 16);
        }

        int var26 = this.SetServiceAddr(var1);
        if (var26 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var14 = System.currentTimeMillis();
            IOPoll var27 = new IOPoll(var14, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var28 = this.m_option_socket_retry_on_error;

            int var32;
            int var33;
            do {
                while(true) {
                    var11 = false;
                    var12 = false;
                    int[] var29 = new int[3];
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    byte[] var30 = this.SEND_AND_RECV(this.m_addr, this.m_port, var21, var14, var27, var29, this.m_socket);
                    if (var30 == null) {
                        if (this.m_socket != null) {
                            this.m_socket.close();
                            this.m_socket = null;
                        }

                        if (var28-- > 0) {
                            continue;
                        }

                        var32 = var29[0];
                        var33 = var29[1];
                        break;
                    }

                    var32 = var29[0];
                    var33 = var29[1];
                    int var31 = var29[2];
                    break;
                }

                if (this.m_socket != null) {
                    this.m_socket.close();
                    this.m_socket = null;
                }
            } while(this.RetryOnErr(var32, var33, var14));

            this.ClearSearchCondition();
            return var32;
        }
    }

    public int Delete(String var1, String var2, String var3, int var4, int var5, int var6) throws IOException {
        boolean var8 = false;
        boolean var9 = false;
        boolean var10 = false;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var17 = new ByteArrayOutputStream();
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, this.m_request_priority);
        BA_writeInteger(var17, var4);
        BA_writeInteger(var17, var5);
        BA_writeString(var17, this.m_auth_code);
        BA_writeString(var17, this.m_log);
        BA_writeString(var17, var2);
        BA_writeString(var17, var3, this.m_char_enc);
        byte[] var18 = var17.toByteArray();
        int var19 = var18.length;
        int var20 = var19 ^ -1446176383;
        int2bytes(var19, var18, 0);
        int2bytes(var20, var18, 4);
        int2bytes(33, var18, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var18, (byte[])null, 16);
        }

        int var23 = this.SetServiceAddr(var1);
        if (var23 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var11 = System.currentTimeMillis();
            IOPoll var24 = new IOPoll(var11, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var25 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var8 = false;
                    int var31 = 0;
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    int var30 = this.Connect(this.m_addr, this.m_port, this.m_socket);
                    if (var30 != 0) {
                        if (var25-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }
                    } else {
                        var30 = this.SendRequest(var18, this.m_socket);
                        if (var30 != 0) {
                            if (var25-- > 0) {
                                if (this.m_socket != null) {
                                    this.m_socket.close();
                                    this.m_socket = null;
                                }
                                continue;
                            }
                        } else {
                            byte[] var26 = this.RecvResult(var11, var24, this.m_socket);
                            if (var26 == null) {
                                if (var25-- > 0) {
                                    if (this.m_socket != null) {
                                        this.m_socket.close();
                                        this.m_socket = null;
                                    }
                                    continue;
                                }

                                var30 = -1;
                            } else {
                                int[] var27 = new int[3];
                                this.GetResultCodes(var26, var27);
                                var30 = var27[0];
                                var31 = var27[1];
                                if (var30 >= 0) {
                                    int var28 = var27[2];
                                    int var29 = bytes2int(var26, var28);
                                    var28 += 4;
                                    return var29;
                                }
                            }
                        }
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var30, var31, var11)) {
                        return var30;
                    }
                }
            }
        }
    }

    public int Update(String var1, String var2, String var3, String[] var4, String[] var5, int[] var6, int var7, int var8, int var9, int var10) throws IOException {
        boolean var12 = false;
        boolean var13 = false;
        boolean var14 = false;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var21 = new ByteArrayOutputStream();
        BA_writeInteger(var21, 0);
        BA_writeInteger(var21, 0);
        BA_writeInteger(var21, 0);
        BA_writeInteger(var21, this.m_request_priority);
        BA_writeInteger(var21, var8);
        BA_writeInteger(var21, var9);
        BA_writeInteger(var21, var7);

        int var11;
        for(var11 = 0; var11 < var7; ++var11) {
            BA_writeInteger(var21, var6[var11]);
        }

        BA_writeString(var21, this.m_auth_code);
        BA_writeString(var21, this.m_log);
        BA_writeString(var21, var2);
        BA_writeString(var21, var3, this.m_char_enc);

        for(var11 = 0; var11 < var7; ++var11) {
            BA_writeString(var21, var4[var11]);
            BA_writeString(var21, var5[var11], this.m_char_enc);
        }

        byte[] var22 = var21.toByteArray();
        int var23 = var22.length;
        int var24 = var23 ^ -1446176383;
        int2bytes(var23, var22, 0);
        int2bytes(var24, var22, 4);
        int2bytes(34, var22, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var22, (byte[])null, 16);
        }

        int var27 = this.SetServiceAddr(var1);
        if (var27 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var15 = System.currentTimeMillis();
            IOPoll var28 = new IOPoll(var15, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var29 = this.m_option_socket_retry_on_error;

            while(true) {
                var12 = false;
                var13 = false;
                int[] var30 = new int[3];
                if (this.m_socket == null) {
                    this.m_socket = new Socket();
                }

                byte[] var31 = this.SEND_AND_RECV(this.m_addr, this.m_port, var22, var15, var28, var30, this.m_socket);
                int var34;
                int var35;
                if (var31 != null) {
                    var34 = var30[0];
                    var35 = var30[1];
                    int var32 = var30[2];
                    int var33 = bytes2int(var31, var32);
                    var32 += 4;
                    return var33;
                }

                if (var29-- > 0) {
                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }
                } else {
                    var34 = var30[0];
                    var35 = var30[1];
                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var34, var35, var15)) {
                        return var34;
                    }
                }
            }
        }
    }

    public int GetTotalCount() throws IOException {
        boolean var1 = false;
        int var2 = this.GetResponse();
        return var2 != 0 ? var2 : this.m_n_total;
    }

    public int GetRowSize() throws IOException {
        boolean var1 = false;
        int var2 = this.GetResponse();
        return var2 != 0 ? var2 : this.m_n_row;
    }

    public int GetColumnSize() throws IOException {
        boolean var1 = false;
        int var2 = this.GetResponse();
        return var2 != 0 ? var2 : this.m_n_col;
    }

    public int GetColumnName(String[] var1, int var2) throws IOException {
        boolean var3 = false;
        int var4 = this.GetResponse();
        if (var4 != 0) {
            return var4;
        } else if (this.m_col_name_count == 0) {
            return 0;
        } else if (var1 == null) {
            this.m_msg = "GetColumnName: invalid null parameter";
            return -1;
        } else if (var1.length != this.m_col_name_count) {
            this.m_msg = "GetColumnName: array size mismatch";
            return -1;
        } else if (var2 != this.m_col_name_count) {
            this.m_msg = "GetColumnName: column size mismatch";
            return -1;
        } else {
            System.arraycopy(this.m_col_name, 0, var1, 0, this.m_col_name_count);
            return 0;
        }
    }

    public int GetSearchTime() throws IOException {
        boolean var1 = false;
        int var2 = this.GetResponse();
        return var2 != 0 ? var2 : this.m_searchTime;
    }

    public int GetRecordNoArray(int[] var1, int[] var2) throws IOException {
        boolean var3 = false;
        int var4 = this.GetResponse();
        if (var4 != 0) {
            return var4;
        } else if (this.m_n_row == 0) {
            return 0;
        } else if (var1 != null && var2 != null) {
            if (var1.length == this.m_n_row && var2.length == this.m_n_row) {
                System.arraycopy(this.m_record_no, 0, var1, 0, this.m_n_row);
                System.arraycopy(this.m_score, 0, var2, 0, this.m_n_row);
                return 0;
            } else {
                this.m_msg = "GetRecordNoArray: array size mismatch";
                return -1;
            }
        } else {
            this.m_msg = "GetRecordNoArray: invalid null parameter";
            return -1;
        }
    }

    public int GetRow(String[] var1, int var2) throws IOException {
        boolean var3 = false;
        int var4 = this.GetResponse();
        if (var4 != 0) {
            return var4;
        } else if (this.m_n_col == 0) {
            return 0;
        } else if (var2 >= 0 && var2 < this.m_n_row) {
            if (var1 == null) {
                this.m_msg = "GetRow: invalid null parameter";
                return -1;
            } else if (var1.length != this.m_n_col) {
                this.m_msg = "GetRow: array size mismatch";
                return -1;
            } else {
                System.arraycopy(this.m_field_data[var2], 0, var1, 0, this.m_n_col);
                return 0;
            }
        } else {
            this.m_msg = "GetRow: invalid row number";
            return -1;
        }
    }

    public int GetClusters(String[] var1, int[][] var2, int[] var3) throws IOException {
        boolean var4 = false;
        int var6 = this.GetResponse();
        if (var6 != 0) {
            return var6;
        } else if (this.m_n_out_cluster == 0) {
            return 0;
        } else {
            System.arraycopy(this.m_out_rec_cnt_per_class, 0, var3, 0, this.m_n_out_cluster);
            System.arraycopy(this.m_cluster_title, 0, var1, 0, this.m_n_out_cluster);

            for(int var5 = 0; var5 < this.m_n_out_cluster; ++var5) {
                var2[var5] = new int[this.m_out_rec_cnt_per_class[var5]];
                System.arraycopy(this.m_out_class_record_no[var5], 0, var2[var5], 0, this.m_out_rec_cnt_per_class[var5]);
            }

            return 0;
        }
    }

    public int GetClusterCount() throws IOException {
        boolean var1 = false;
        int var2 = this.GetResponse();
        return var2 != 0 ? var2 : this.m_n_out_cluster;
    }

    public String GetRelatedTerms() throws IOException {
        boolean var1 = false;
        int var2 = this.GetResponse();
        return var2 != 0 ? null : this.m_rec_term;
    }

    public int GetRelatedTermCount() throws IOException {
        boolean var1 = false;
        int var2 = this.GetResponse();
        return var2 != 0 ? var2 : this.m_n_rec_term;
    }

    public String GetExpandQuery(int var1) throws IOException {
        boolean var2 = false;
        int var3 = this.GetResponse();
        if (var3 != 0) {
            return null;
        } else if (var1 == 1) {
            return this.m_exp_k2k;
        } else if (var1 == 2) {
            return this.m_exp_k2e;
        } else if (var1 == 4) {
            return this.m_exp_e2e;
        } else if (var1 == 8) {
            return this.m_exp_e2k;
        } else if (var1 == 16) {
            return this.m_exp_trl;
        } else {
            return var1 == 32 ? this.m_exp_rcm : null;
        }
    }

    public int GetExpandQueryCount(int var1) throws IOException {
        boolean var2 = false;
        int var3 = this.GetResponse();
        if (var3 != 0) {
            return var3;
        } else if (var1 == 1) {
            return this.m_n_exp_k2k;
        } else if (var1 == 2) {
            return this.m_n_exp_k2e;
        } else if (var1 == 4) {
            return this.m_n_exp_e2e;
        } else if (var1 == 8) {
            return this.m_n_exp_e2k;
        } else if (var1 == 16) {
            return this.m_n_exp_trl;
        } else {
            return var1 == 32 ? this.m_n_exp_rcm : -1;
        }
    }

    public int GetQueryAttribute(String var1, String[] var2, int[] var3, String[] var4, int[] var5, String[] var6, String[] var7, String var8, int var9, int var10) throws IOException {
        boolean var11 = false;
        boolean var12 = false;
        boolean var13 = false;
        int var21 = 0;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var22 = new ByteArrayOutputStream();
        BA_writeInteger(var22, 0);
        BA_writeInteger(var22, 0);
        BA_writeInteger(var22, 0);
        BA_writeInteger(var22, this.m_request_priority);
        BA_writeInteger(var22, var9);
        BA_writeInteger(var22, var10);
        BA_writeString(var22, var8, this.m_char_enc);
        byte[] var23 = var22.toByteArray();
        int var24 = var23.length;
        int var25 = var24 ^ -1446176383;
        int2bytes(var24, var23, 0);
        int2bytes(var25, var23, 4);
        int2bytes(48, var23, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var23, (byte[])null, 16);
        }

        int var28 = this.SetServiceAddr(var1);
        if (var28 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var14 = System.currentTimeMillis();
            IOPoll var29 = new IOPoll(var14, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var30 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var11 = false;
                    var12 = false;
                    int[] var31 = new int[3];
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    byte[] var32 = this.SEND_AND_RECV(this.m_addr, this.m_port, var23, var14, var29, var31, this.m_socket);
                    int var35;
                    int var36;
                    if (var32 == null) {
                        if (var30-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }

                        var35 = var31[0];
                        var36 = var31[1];
                    } else {
                        var35 = var31[0];
                        var36 = var31[1];
                        int var33 = var31[2];
                        var21 = bytes2int(var32, var33);
                        var33 += 4;

                        int var20;
                        for(var20 = 0; var20 < var21; ++var20) {
                            var3[var20] = bytes2int(var32, var33);
                            var33 += 4;
                            var5[var20] = bytes2int(var32, var33);
                            var33 += 4;
                        }

                        boolean var34 = false;

                        int var37;
                        for(var20 = 0; var20 < var21; ++var20) {
                            var37 = bytes2str(var32, var33);
                            var2[var20] = new String(var32, var33, var37, this.m_char_enc);
                            var33 += var37 + 1;
                            var37 = bytes2str(var32, var33);
                            var4[var20] = new String(var32, var33, var37, this.m_char_enc);
                            var33 += var37 + 1;
                            var37 = bytes2str(var32, var33);
                            var6[var20] = new String(var32, var33, var37, this.m_char_enc);
                            var33 += var37 + 1;
                        }

                        var37 = bytes2str(var32, var33);
                        var7[0] = new String(var32, var33, var37, this.m_char_enc);
                        int var10000 = var33 + var37 + 1;
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var35, var36, var14)) {
                        if (var35 < 0) {
                            return var35;
                        }

                        return var21;
                    }
                }
            }
        }
    }

    public int SetAuthCode(String var1) {
        this.m_auth_code = var1;
        return 0;
    }

    public int SetLog(String var1) {
        this.m_log = var1;
        return 0;
    }

    public int CompleteKeyword(String var1, String[] var2, int[] var3, String[] var4, String[] var5, String[] var6, int var7, String var8, int var9, int var10) throws IOException {
        boolean var11 = false;
        boolean var12 = false;
        boolean var13 = false;
        int var20 = 0;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var21 = new ByteArrayOutputStream();
        BA_writeInteger(var21, 0);
        BA_writeInteger(var21, 0);
        BA_writeInteger(var21, 0);
        BA_writeInteger(var21, this.m_request_priority);
        BA_writeInteger(var21, var7);
        BA_writeInteger(var21, var9);
        BA_writeInteger(var21, var10);
        if (var8 == null) {
            var8 = "";
        }

        BA_writeString(var21, var8, this.m_char_enc);
        Object var22 = null;
        byte[] var36 = var21.toByteArray();
        int var23 = var36.length;
        int var24 = var23 ^ -1446176383;
        int2bytes(var23, var36, 0);
        int2bytes(var24, var36, 4);
        int2bytes(49, var36, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var36, (byte[])null, 16);
        }

        int var34 = this.SetServiceAddr(var1);
        if (var34 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var14 = System.currentTimeMillis();
            IOPoll var27 = new IOPoll(var14, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var28 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var11 = false;
                    var12 = false;
                    int[] var29 = new int[3];
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    byte[] var30 = this.SEND_AND_RECV(this.m_addr, this.m_port, var36, var14, var27, var29, this.m_socket);
                    int var35;
                    if (var30 == null) {
                        if (var28-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }

                        var34 = var29[0];
                        var35 = var29[1];
                    } else {
                        var34 = var29[0];
                        var35 = var29[1];
                        int var31 = var29[2];
                        boolean var32 = false;
                        var20 = bytes2int(var30, var31);
                        var31 += 4;

                        int var33;
                        int var37;
                        for(var33 = 0; var33 < var20; ++var33) {
                            var37 = bytes2str(var30, var31);
                            var2[var33] = new String(var30, var31, var37, this.m_char_enc);
                            var31 += var37 + 1;
                        }

                        var37 = bytes2str(var30, var31);
                        var6[0] = new String(var30, var31, var37, this.m_char_enc);
                        var31 += var37 + 1;
                        if (var30.length > var31) {
                            var31 += alignWordSize(var31);

                            for(var33 = 0; var33 < var20; ++var33) {
                                if (var3 != null) {
                                    var3[var33] = bytes2int(var30, var31);
                                }

                                var31 += 4;
                            }

                            for(var33 = 0; var33 < var20; ++var33) {
                                if (var4 != null) {
                                    var37 = bytes2str(var30, var31);
                                    var4[var33] = new String(var30, var31, var37, this.m_char_enc);
                                }

                                var31 += var37 + 1;
                            }

                            for(var33 = 0; var33 < var20; ++var33) {
                                if (var5 != null) {
                                    var37 = bytes2str(var30, var31);
                                    var5[var33] = new String(var30, var31, var37, this.m_char_enc);
                                }

                                var31 += var37 + 1;
                            }
                        } else {
                            for(var33 = 0; var33 < var20; ++var33) {
                                if (var3 != null) {
                                    var3[var33] = 0;
                                }
                            }

                            for(var33 = 0; var33 < var20; ++var33) {
                                if (var4 != null) {
                                    var4[var33] = "";
                                }
                            }

                            for(var33 = 0; var33 < var20; ++var33) {
                                if (var5 != null) {
                                    var5[var33] = "";
                                }
                            }
                        }

                        var31 += alignWordSize(var31);
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var34, var35, var14)) {
                        if (var34 < 0) {
                            return var34;
                        }

                        return var20;
                    }
                }
            }
        }
    }

    public int SpellCheck(String var1, String[] var2, int var3, String var4) throws IOException {
        boolean var5 = false;
        boolean var6 = false;
        boolean var7 = false;
        int var14 = 0;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var15 = new ByteArrayOutputStream();
        BA_writeInteger(var15, 0);
        BA_writeInteger(var15, 0);
        BA_writeInteger(var15, 0);
        BA_writeInteger(var15, this.m_request_priority);
        BA_writeInteger(var15, var3);
        if (var4 == null) {
            var4 = "";
        }

        BA_writeString(var15, var4, this.m_char_enc);
        Object var16 = null;
        byte[] var30 = var15.toByteArray();
        int var17 = var30.length;
        int var18 = var17 ^ -1446176383;
        int2bytes(var17, var30, 0);
        int2bytes(var18, var30, 4);
        int2bytes(50, var30, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var30, (byte[])null, 16);
        }

        int var28 = this.SetServiceAddr(var1);
        if (var28 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -1;
        } else {
            long var8 = System.currentTimeMillis();
            IOPoll var21 = new IOPoll(var8, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var22 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var5 = false;
                    var6 = false;
                    int[] var23 = new int[3];
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    byte[] var24 = this.SEND_AND_RECV(this.m_addr, this.m_port, var30, var8, var21, var23, this.m_socket);
                    int var29;
                    if (var24 == null) {
                        if (var22-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }

                        var28 = var23[0];
                        var29 = var23[1];
                    } else {
                        var28 = var23[0];
                        var29 = var23[1];
                        int var25 = var23[2];
                        var14 = bytes2int(var24, var25);
                        var25 += 4;

                        for(int var26 = 0; var26 < var14; ++var26) {
                            int var27 = bytes2str(var24, var25);
                            var2[var26] = new String(var24, var25, var27, this.m_char_enc);
                            var25 += var27 + 1;
                        }
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var28, var29, var8)) {
                        if (var28 < 0) {
                            return var28;
                        }

                        return var14;
                    }
                }
            }
        }
    }

    public int RecommendKeyword(String var1, String[] var2, int var3, String var4, int var5) throws IOException {
        boolean var6 = false;
        boolean var7 = false;
        boolean var8 = false;
        int var15 = 0;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var16 = new ByteArrayOutputStream();
        BA_writeInteger(var16, 0);
        BA_writeInteger(var16, 0);
        BA_writeInteger(var16, 0);
        BA_writeInteger(var16, this.m_request_priority);
        BA_writeInteger(var16, var3);
        BA_writeInteger(var16, var5);
        if (var4 == null) {
            var4 = "";
        }

        BA_writeString(var16, var4, this.m_char_enc);
        Object var17 = null;
        byte[] var31 = var16.toByteArray();
        int var18 = var31.length;
        int var19 = var18 ^ -1446176383;
        int2bytes(var18, var31, 0);
        int2bytes(var19, var31, 4);
        int2bytes(51, var31, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var31, (byte[])null, 16);
        }

        int var29 = this.SetServiceAddr(var1);
        if (var29 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var9 = System.currentTimeMillis();
            IOPoll var22 = new IOPoll(var9, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var23 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var6 = false;
                    var7 = false;
                    int[] var24 = new int[3];
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    byte[] var25 = this.SEND_AND_RECV(this.m_addr, this.m_port, var31, var9, var22, var24, this.m_socket);
                    int var30;
                    if (var25 == null) {
                        if (var23-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }

                        var29 = var24[0];
                        var30 = var24[1];
                    } else {
                        var29 = var24[0];
                        var30 = var24[1];
                        int var26 = var24[2];
                        boolean var27 = false;
                        var15 = bytes2int(var25, var26);
                        var26 += 4;

                        for(int var28 = 0; var28 < var15; ++var28) {
                            int var32 = bytes2str(var25, var26);
                            var2[var28] = new String(var25, var26, var32, this.m_char_enc);
                            var26 += var32 + 1;
                        }

                        var26 += alignWordSize(var26);
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var29, var30, var9)) {
                        if (var29 < 0) {
                            return var29;
                        }

                        return var15;
                    }
                }
            }
        }
    }

    public int GetPopularKeyword(String var1, String[] var2, String[] var3, int var4, int var5) throws IOException {
        boolean var6 = false;
        boolean var7 = false;
        boolean var8 = false;
        int var15 = 0;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var16 = new ByteArrayOutputStream();
        BA_writeInteger(var16, 0);
        BA_writeInteger(var16, 0);
        BA_writeInteger(var16, 0);
        BA_writeInteger(var16, this.m_request_priority);
        BA_writeInteger(var16, var4);
        BA_writeInteger(var16, var5);
        Object var17 = null;
        byte[] var31 = var16.toByteArray();
        int var18 = var31.length;
        int var19 = var18 ^ -1446176383;
        int2bytes(var18, var31, 0);
        int2bytes(var19, var31, 4);
        int2bytes(52, var31, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var31, (byte[])null, 16);
        }

        int var29 = this.SetServiceAddr(var1);
        if (var29 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var9 = System.currentTimeMillis();
            IOPoll var22 = new IOPoll(var9, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var23 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var6 = false;
                    var7 = false;
                    int[] var24 = new int[3];
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    byte[] var25 = this.SEND_AND_RECV(this.m_addr, this.m_port, var31, var9, var22, var24, this.m_socket);
                    int var30;
                    if (var25 == null) {
                        if (var23-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }

                        var29 = var24[0];
                        var30 = var24[1];
                    } else {
                        var29 = var24[0];
                        var30 = var24[1];
                        int var26 = var24[2];
                        int var27 = 0;
                        var15 = bytes2int(var25, var26);
                        var26 += 4;

                        int var28;
                        for(var28 = 0; var28 < var15; ++var28) {
                            var27 = bytes2str(var25, var26);
                            var2[var28] = new String(var25, var26, var27, this.m_char_enc);
                            var26 += var27 + 1;
                        }

                        if (var25.length > var26) {
                            for(var28 = 0; var28 < var15; ++var28) {
                                if (var3 != null) {
                                    var27 = bytes2str(var25, var26);
                                    var3[var28] = new String(var25, var26, var27, this.m_char_enc);
                                }

                                var26 += var27 + 1;
                            }
                        } else {
                            for(var28 = 0; var28 < var15; ++var28) {
                                if (var3 != null) {
                                    var3[var28] = "";
                                }
                            }
                        }

                        var26 += alignWordSize(var26);
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var29, var30, var9)) {
                        if (var29 < 0) {
                            return var29;
                        }

                        return var15;
                    }
                }
            }
        }
    }

    public int AnchorText(String var1, String[] var2, String var3, String var4, String var5, int var6) throws IOException {
        boolean var7 = false;
        boolean var8 = false;
        boolean var9 = false;
        byte var16 = 0;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var17 = new ByteArrayOutputStream();
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, this.m_request_priority);
        BA_writeInteger(var17, var6);
        if (var3 == null) {
            var3 = "";
        }

        BA_writeString(var17, var3, this.m_char_enc);
        if (var4 == null) {
            var4 = "";
        }

        BA_writeString(var17, var4, this.m_char_enc);
        if (var5 == null) {
            var5 = "";
        }

        BA_writeString(var17, var5);
        Object var18 = null;
        byte[] var31 = var17.toByteArray();
        int var19 = var31.length;
        int var20 = var19 ^ -1446176383;
        int2bytes(var19, var31, 0);
        int2bytes(var20, var31, 4);
        int2bytes(53, var31, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var31, (byte[])null, 16);
        }

        int var29 = this.SetServiceAddr(var1);
        if (var29 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var10 = System.currentTimeMillis();
            IOPoll var23 = new IOPoll(var10, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var24 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var7 = false;
                    var8 = false;
                    int[] var25 = new int[3];
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    byte[] var26 = this.SEND_AND_RECV(this.m_addr, this.m_port, var31, var10, var23, var25, this.m_socket);
                    int var30;
                    if (var26 == null) {
                        if (var24-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }

                        var29 = var25[0];
                        var30 = var25[1];
                    } else {
                        var29 = var25[0];
                        var30 = var25[1];
                        int var27 = var25[2];
                        boolean var28 = false;
                        int var32 = bytes2str(var26, var27);
                        var2[0] = new String(var26, var27, var32, this.m_char_enc);
                        var27 += var32 + 1;
                        var27 += alignWordSize(var27);
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var29, var30, var10)) {
                        if (var29 < 0) {
                            return var29;
                        }

                        return var16;
                    }
                }
            }
        }
    }

    public int GetSynonymList(String var1, int[] var2, String[][] var3, int var4, String var5, int var6, int var7, int var8, int var9, int var10, int var11) throws IOException {
        boolean var12 = false;
        boolean var13 = false;
        boolean var14 = false;
        int var21 = 0;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var22 = new ByteArrayOutputStream();
        BA_writeInteger(var22, 0);
        BA_writeInteger(var22, 0);
        BA_writeInteger(var22, 0);
        BA_writeInteger(var22, this.m_request_priority);
        BA_writeInteger(var22, var4);
        BA_writeInteger(var22, var6);
        BA_writeInteger(var22, var7);
        BA_writeInteger(var22, var8);
        BA_writeInteger(var22, var9);
        BA_writeInteger(var22, var10);
        BA_writeInteger(var22, var11);
        if (var5 == null) {
            var5 = "";
        }

        BA_writeString(var22, var5, this.m_char_enc);
        Object var23 = null;
        byte[] var38 = var22.toByteArray();
        int var24 = var38.length;
        int var25 = var24 ^ -1446176383;
        int2bytes(var24, var38, 0);
        int2bytes(var25, var38, 4);
        int2bytes(54, var38, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var38, (byte[])null, 16);
        }

        int var36 = this.SetServiceAddr(var1);
        if (var36 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var15 = System.currentTimeMillis();
            IOPoll var28 = new IOPoll(var15, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var29 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var12 = false;
                    var13 = false;
                    int[] var30 = new int[3];
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    byte[] var31 = this.SEND_AND_RECV(this.m_addr, this.m_port, var38, var15, var28, var30, this.m_socket);
                    int var37;
                    if (var31 == null) {
                        if (var29-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }

                        var36 = var30[0];
                        var37 = var30[1];
                    } else {
                        var36 = var30[0];
                        var37 = var30[1];
                        int var32 = var30[2];
                        boolean var33 = false;
                        var21 = bytes2int(var31, var32);
                        var32 += 4;

                        int var34;
                        for(var34 = 0; var34 < var21; ++var34) {
                            var2[var34] = bytes2int(var31, var32);
                            var32 += 4;
                        }

                        for(var34 = 0; var34 < var21; ++var34) {
                            if (var2[var34] > 0) {
                                var3[var34] = new String[var2[var34]];
                            }

                            for(int var35 = 0; var35 < var2[var34]; ++var35) {
                                int var39 = bytes2str(var31, var32);
                                var3[var34][var35] = new String(var31, var32, var39, this.m_char_enc);
                                var32 += var39 + 1;
                            }
                        }

                        var32 += alignWordSize(var32);
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var36, var37, var15)) {
                        if (var36 < 0) {
                            return var36;
                        }

                        return var21;
                    }
                }
            }
        }
    }

    public int ExtractKeyword(String var1, String[] var2, int var3, String var4, String var5, int var6, int var7, int var8, int var9) throws IOException {
        boolean var10 = false;
        boolean var11 = false;
        boolean var12 = false;
        int var19 = 0;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var20 = new ByteArrayOutputStream();
        BA_writeInteger(var20, 0);
        BA_writeInteger(var20, 0);
        BA_writeInteger(var20, 0);
        BA_writeInteger(var20, this.m_request_priority);
        BA_writeInteger(var20, var3);
        BA_writeInteger(var20, var6);
        BA_writeInteger(var20, var7);
        BA_writeInteger(var20, var8);
        BA_writeInteger(var20, var9);
        if (var4 == null) {
            var4 = "";
        }

        BA_writeString(var20, var4);
        if (var5 == null) {
            var5 = "";
        }

        BA_writeString(var20, var5, this.m_char_enc);
        Object var21 = null;
        byte[] var35 = var20.toByteArray();
        int var22 = var35.length;
        int var23 = var22 ^ -1446176383;
        int2bytes(var22, var35, 0);
        int2bytes(var23, var35, 4);
        int2bytes(27, var35, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var35, (byte[])null, 16);
        }

        int var33 = this.SetServiceAddr(var1);
        if (var33 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var13 = System.currentTimeMillis();
            IOPoll var26 = new IOPoll(var13, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var27 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var10 = false;
                    var11 = false;
                    int[] var28 = new int[3];
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    byte[] var29 = this.SEND_AND_RECV(this.m_addr, this.m_port, var35, var13, var26, var28, this.m_socket);
                    int var34;
                    if (var29 == null) {
                        if (var27-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }

                        var33 = var28[0];
                        var34 = var28[1];
                    } else {
                        var33 = var28[0];
                        var34 = var28[1];
                        int var30 = var28[2];
                        boolean var31 = false;
                        var19 = bytes2int(var29, var30);
                        var30 += 4;

                        for(int var32 = 0; var32 < var19; ++var32) {
                            int var36 = bytes2str(var29, var30);
                            var2[var32] = new String(var29, var30, var36, this.m_char_enc);
                            var30 += var36 + 1;
                        }

                        var30 += alignWordSize(var30);
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var33, var34, var13)) {
                        if (var33 < 0) {
                            return var33;
                        }

                        return var19;
                    }
                }
            }
        }
    }

    public int GetRealTimePopularKeyword(String var1, String[] var2, String[] var3, int var4, int var5, int var6) throws IOException {
        boolean var7 = false;
        boolean var8 = false;
        boolean var9 = false;
        int var16 = 0;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var17 = new ByteArrayOutputStream();
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, this.m_request_priority);
        BA_writeInteger(var17, var4);
        BA_writeInteger(var17, var5);
        BA_writeInteger(var17, var6);
        Object var18 = null;
        byte[] var32 = var17.toByteArray();
        int var19 = var32.length;
        int var20 = var19 ^ -1446176383;
        int2bytes(var19, var32, 0);
        int2bytes(var20, var32, 4);
        int2bytes(55, var32, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var32, (byte[])null, 16);
        }

        int var30 = this.SetServiceAddr(var1);
        if (var30 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var10 = System.currentTimeMillis();
            IOPoll var23 = new IOPoll(var10, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var24 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var7 = false;
                    var8 = false;
                    int[] var25 = new int[3];
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    byte[] var26 = this.SEND_AND_RECV(this.m_addr, this.m_port, var32, var10, var23, var25, this.m_socket);
                    int var31;
                    if (var26 == null) {
                        if (var24-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }

                        var30 = var25[0];
                        var31 = var25[1];
                    } else {
                        var30 = var25[0];
                        var31 = var25[1];
                        int var27 = var25[2];
                        int var28 = 0;
                        var16 = bytes2int(var26, var27);
                        var27 += 4;

                        int var29;
                        for(var29 = 0; var29 < var16; ++var29) {
                            var28 = bytes2str(var26, var27);
                            if (var29 < var4) {
                                var2[var29] = new String(var26, var27, var28, this.m_char_enc);
                            }

                            var27 += var28 + 1;
                        }

                        if (var26.length > var27) {
                            for(var29 = 0; var29 < var16; ++var29) {
                                if (var3 != null) {
                                    var28 = bytes2str(var26, var27);
                                    if (var29 < var4) {
                                        var3[var29] = new String(var26, var27, var28, this.m_char_enc);
                                    }
                                }

                                var27 += var28 + 1;
                            }
                        } else {
                            for(var29 = 0; var29 < var16; ++var29) {
                                if (var3 != null && var29 < var4) {
                                    var3[var29] = "";
                                }
                            }
                        }

                        var27 += alignWordSize(var27);
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var30, var31, var10)) {
                        int var33;
                        if (var4 < var16) {
                            var33 = var4;
                        } else {
                            var33 = var16;
                        }

                        if (var30 < 0) {
                            return var30;
                        }

                        return var33;
                    }
                }
            }
        }
    }

    public int GetResultROWID2(int[] var1, String[] var2, String[] var3, String[] var4, int[] var5, int[] var6, int var7) throws IOException {
        boolean var8 = false;
        boolean var11 = false;
        int var12 = this.GetResponse();
        if (var12 != 0) {
            return var12;
        } else if (this.m_tabno_count != this.m_n_row) {
            this.m_msg = "incorrect row count " + this.m_tabno_count + ": " + this.m_n_row + "(C218762)";
            byte var13 = -1;
            return var13;
        } else {
            int var9;
            for(var9 = 0; var9 < this.m_n_row && var9 < var7; ++var9) {
                int var10 = this.m_tabno[var9];
                if (this.m_tabno_count != 0 && this.m_union_table_count != 0 && var10 >= 0 && var10 < this.m_union_table_count) {
                    var2[var9] = this.m_union_table_name[var10][0];
                    var3[var9] = this.m_union_table_name[var10][1];
                    var4[var9] = this.m_union_table_name[var10][2];
                } else {
                    var2[var9] = "";
                    var3[var9] = "";
                    var4[var9] = "";
                }

                var1[var9] = this.m_tabno[var9];
                var5[var9] = this.m_record_no[var9];
                var6[var9] = this.m_score[var9];
            }

            return var9;
        }
    }

    public int GetResult_GroupBy(String[][] var1, int[] var2, int var3) throws IOException {
        boolean var4 = false;
        int var8 = this.GetResponse();
        if (var8 != 0) {
            return var8;
        } else {
            int var7;
            if (var3 < this.m_group_count) {
                var7 = var3;
            } else {
                var7 = this.m_group_count;
            }

            System.arraycopy(this.m_group_size, 0, var2, 0, var7);

            for(int var5 = 0; var5 < var7; ++var5) {
                for(int var6 = 0; var6 < this.m_group_key_count; ++var6) {
                    var1[var5][var6] = this.m_group_key_val[var5][var6];
                }
            }

            byte var9 = 0;
            return var9;
        }
    }

    public int GetResult_GroupBy_GroupCount() throws IOException {
        boolean var1 = false;
        int var2 = this.GetResponse();
        return var2 != 0 ? var2 : this.m_group_count;
    }

    public int GetResult_GroupBy_KeyCount() throws IOException {
        boolean var1 = false;
        int var2 = this.GetResponse();
        return var2 != 0 ? var2 : this.m_group_key_count;
    }

    public int CensorSearchWords(String var1, String[] var2, int var3, String var4, int var5) throws IOException {
        boolean var6 = false;
        boolean var7 = false;
        boolean var8 = false;
        int var15 = 0;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var16 = new ByteArrayOutputStream();
        BA_writeInteger(var16, 0);
        BA_writeInteger(var16, 0);
        BA_writeInteger(var16, 0);
        BA_writeInteger(var16, this.m_request_priority);
        BA_writeInteger(var16, var3);
        BA_writeInteger(var16, var5);
        if (var4 == null) {
            var4 = "";
        }

        BA_writeString(var16, var4, this.m_char_enc);
        Object var17 = null;
        byte[] var31 = var16.toByteArray();
        int var18 = var31.length;
        int var19 = var18 ^ -1446176383;
        int2bytes(var18, var31, 0);
        int2bytes(var19, var31, 4);
        int2bytes(56, var31, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var31, (byte[])null, 16);
        }

        int var29 = this.SetServiceAddr(var1);
        if (var29 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var9 = System.currentTimeMillis();
            IOPoll var22 = new IOPoll(var9, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var23 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var6 = false;
                    var7 = false;
                    int[] var24 = new int[3];
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    byte[] var25 = this.SEND_AND_RECV(this.m_addr, this.m_port, var31, var9, var22, var24, this.m_socket);
                    int var30;
                    if (var25 == null) {
                        if (var23-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }

                        var29 = var24[0];
                        var30 = var24[1];
                    } else {
                        var29 = var24[0];
                        var30 = var24[1];
                        int var26 = var24[2];
                        boolean var27 = false;
                        var15 = bytes2int(var25, var26);
                        var26 += 4;

                        for(int var28 = 0; var28 < var15; ++var28) {
                            int var33 = bytes2str(var25, var26);
                            if (var28 < var3) {
                                var2[var28] = new String(var25, var26, var33, this.m_char_enc);
                            }

                            var26 += var33 + 1;
                        }

                        var26 += alignWordSize(var26);
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var29, var30, var9)) {
                        int var32;
                        if (var3 < var15) {
                            var32 = var3;
                        } else {
                            var32 = var15;
                        }

                        if (var29 < 0) {
                            return var29;
                        }

                        return var32;
                    }
                }
            }
        }
    }

    public int Transliterate(String var1, String[] var2, int var3, String var4, int var5, int var6) throws IOException {
        boolean var7 = false;
        boolean var8 = false;
        boolean var9 = false;
        int var16 = 0;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var17 = new ByteArrayOutputStream();
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, this.m_request_priority);
        BA_writeInteger(var17, var3);
        BA_writeInteger(var17, var5);
        BA_writeInteger(var17, var6);
        if (var4 == null) {
            var4 = "";
        }

        BA_writeString(var17, var4, this.m_char_enc);
        Object var18 = null;
        byte[] var32 = var17.toByteArray();
        int var19 = var32.length;
        int var20 = var19 ^ -1446176383;
        int2bytes(var19, var32, 0);
        int2bytes(var20, var32, 4);
        int2bytes(57, var32, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var32, (byte[])null, 16);
        }

        int var30 = this.SetServiceAddr(var1);
        if (var30 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var10 = System.currentTimeMillis();
            IOPoll var23 = new IOPoll(var10, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var24 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var7 = false;
                    var8 = false;
                    int[] var25 = new int[3];
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    byte[] var26 = this.SEND_AND_RECV(this.m_addr, this.m_port, var32, var10, var23, var25, this.m_socket);
                    int var31;
                    if (var26 == null) {
                        if (var24-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }

                        var30 = var25[0];
                        var31 = var25[1];
                    } else {
                        var30 = var25[0];
                        var31 = var25[1];
                        int var27 = var25[2];
                        boolean var28 = false;
                        var16 = bytes2int(var26, var27);
                        var27 += 4;

                        for(int var29 = 0; var29 < var16; ++var29) {
                            int var34 = bytes2str(var26, var27);
                            if (var29 < var3) {
                                var2[var29] = new String(var26, var27, var34, this.m_char_enc);
                            }

                            var27 += var34 + 1;
                        }

                        var27 += alignWordSize(var27);
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var30, var31, var10)) {
                        int var33;
                        if (var3 < var16) {
                            var33 = var3;
                        } else {
                            var33 = var16;
                        }

                        if (var30 < 0) {
                            return var30;
                        }

                        return var33;
                    }
                }
            }
        }
    }

    public int PutCache(String var1, int var2, String var3, int var4, String var5, String var6, int var7) throws IOException {
        boolean var8 = false;
        boolean var9 = false;
        boolean var10 = false;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var17 = new ByteArrayOutputStream();
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, this.m_request_priority);
        BA_writeInteger(var17, var2);
        BA_writeInteger(var17, var4);
        BA_writeInteger(var17, var7);
        if (var3 == null) {
            var3 = "";
        }

        BA_writeString(var17, var3, this.m_char_enc);
        if (var5 == null) {
            var5 = "";
        }

        BA_writeString(var17, var5, this.m_char_enc);
        if (var6 == null) {
            var6 = "";
        }

        BA_writeString(var17, var6, this.m_char_enc);
        Object var18 = null;
        byte[] var31 = var17.toByteArray();
        int var19 = var31.length;
        int var20 = var19 ^ -1446176383;
        int2bytes(var19, var31, 0);
        int2bytes(var20, var31, 4);
        int2bytes(59, var31, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var31, (byte[])null, 16);
        }

        int var28 = this.SetServiceAddr(var1);
        if (var28 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var11 = System.currentTimeMillis();
            IOPoll var23 = new IOPoll(var11, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var24 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var8 = false;
                    var9 = false;
                    int[] var25 = new int[3];
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    byte[] var26 = this.SEND_AND_RECV(this.m_addr, this.m_port, var31, var11, var23, var25, this.m_socket);
                    int var29;
                    if (var26 == null) {
                        if (var24-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }

                        var28 = var25[0];
                        var29 = var25[1];
                    } else {
                        var28 = var25[0];
                        var29 = var25[1];
                        int var27 = var25[2];
                    }

                    byte var30 = 0;
                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var30, var29, var11)) {
                        return var30;
                    }
                }
            }
        }
    }

    public int GetCache(String var1, int var2, String var3, int var4) throws IOException {
        boolean var5 = false;
        boolean var6 = false;
        boolean var7 = false;
        this.ClearNetworkErrorStatistics();
        ByteArrayOutputStream var17 = new ByteArrayOutputStream();
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, 0);
        BA_writeInteger(var17, this.m_request_priority);
        BA_writeInteger(var17, var2);
        BA_writeInteger(var17, var4);
        if (var3 == null) {
            var3 = "";
        }

        BA_writeString(var17, var3, this.m_char_enc);
        Object var18 = null;
        byte[] var31 = var17.toByteArray();
        int var19 = var31.length;
        int var20 = var19 ^ -1446176383;
        int2bytes(var19, var31, 0);
        int2bytes(var20, var31, 4);
        int2bytes(60, var31, 8);
        if (this.m_rext == 1) {
            this.ENCRYPT_DATA_WITH_KEY(var31, (byte[])null, 16);
        }

        int var29 = this.SetServiceAddr(var1);
        if (var29 < 0) {
            this.m_msg = "serviceAddr string wrong";
            return -5;
        } else {
            long var8 = System.currentTimeMillis();
            IOPoll var23 = new IOPoll(var8, this.m_request_timeout_msec > 0 ? this.m_request_timeout_msec : this.m_request_timeout * 1000);
            int var24 = this.m_option_socket_retry_on_error;

            while(true) {
                while(true) {
                    var5 = false;
                    int var30 = 0;
                    if (this.m_socket == null) {
                        this.m_socket = new Socket();
                    }

                    var29 = this.SEND_REQUEST(this.m_addr, this.m_port, var31, this.m_socket);
                    if (var29 != 0) {
                        if (this.m_socket != null) {
                            this.m_socket.close();
                            this.m_socket = null;
                        }

                        if (var24-- > 0) {
                            if (this.m_socket != null) {
                                this.m_socket.close();
                                this.m_socket = null;
                            }
                            continue;
                        }
                    } else {
                        this.m_f_get_cache = 0;
                        int[] var25 = new int[3];
                        byte[] var26 = this.RECV_RESPONSE(var8, var23, var25, this.m_socket);
                        if (var26 == null) {
                            if (var24-- > 0) {
                                if (this.m_socket != null) {
                                    this.m_socket.close();
                                    this.m_socket = null;
                                }
                                continue;
                            }

                            var29 = var25[0];
                            var30 = var25[1];
                        } else {
                            var29 = var25[0];
                            var30 = var25[1];
                            int var27 = var25[2];
                            boolean var28 = false;
                            int var14 = bytes2int(var26, var27);
                            var27 += 4;
                            bytes2int(var26, var27);
                            var27 += 4;
                            String var16;
                            if (var26.length > var27) {
                                int var32 = bytes2str(var26, var27);
                                var16 = new String(var26, var27, var32, this.m_char_enc);
                                var27 += var32 + 1;
                            } else {
                                var16 = new String("");
                            }

                            var27 += alignWordSize(var27);
                            this.m_cache_hit_flag = var14;
                            this.m_cache_out_data = var16;
                            this.m_f_get_cache = 1;
                        }
                    }

                    if (this.m_socket != null) {
                        this.m_socket.close();
                        this.m_socket = null;
                    }

                    if (!this.RetryOnErr(var29, var30, var8)) {
                        return var29;
                    }
                }
            }
        }
    }

    public int GetCache_HitFlag() {
        if (this.m_f_get_cache != 1) {
            this.m_msg = "GetCache() function is not called yet";
            return -1;
        } else {
            return this.m_cache_hit_flag;
        }
    }

    public String GetCache_Data() {
        if (this.m_f_get_cache != 1) {
            this.m_msg = "GetCache() function is not called yet";
            return null;
        } else {
            return this.m_cache_out_data;
        }
    }

    private int socketRead(InputStream var1, byte[] var2, IOPoll var3) throws IOException {
        int var4 = 0;
        int var5 = var2.length;

        while(var5 > 0) {
            int var6 = var1.read(var2, var4, var5);
            if (var6 < 0) {
                break;
            }

            var4 += var6;
            var5 -= var6;
            if (var5 > 0 && var3 != null && var3.TimedOut()) {
                throw new InterruptedIOException();
            }
        }

        return var4;
    }

    private static int alignWordSize(int var0) {
        return (var0 - 1 & -8) + 8 - var0;
    }

    private static int ALIGNED_SIZE(int var0) {
        return (var0 - 1 & -8) + 8;
    }

    private static void BA_writeInteger(ByteArrayOutputStream var0, int var1) throws IOException {
        byte[] var2 = new byte[4];
        int2bytes(var1, var2, 0);
        var0.write(var2, 0, 4);
    }

    private static void BA_writeString(ByteArrayOutputStream var0, String var1) throws IOException {
        byte[] var2 = var1.getBytes();
        var0.write(var2, 0, var2.length);
        var0.write(0);
    }

    private static void BA_writeString(ByteArrayOutputStream var0, String var1, String var2) throws IOException {
        byte[] var3 = var1.getBytes(var2);
        var0.write(var3, 0, var3.length);
        var0.write(0);
    }

    private static void BA_writeNull(ByteArrayOutputStream var0) throws IOException {
        var0.write(0);
    }

    private int SetServiceAddr(String var1) {
        boolean var2 = false;
        boolean var3 = false;
        int var4 = 0;
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
    }

    private void parseAddrList(String[][] var1, int[][] var2, String var3) {
        boolean var4 = false;
        StringTokenizer var6 = new StringTokenizer(var3, " ,\t\n\r\f");
        int var9 = var6.countTokens();
        var1[0] = new String[var9];
        var2[0] = new int[var9];

        for(int var5 = 0; var5 < var9; ++var5) {
            String var7 = var6.nextToken();
            int var8 = var7.lastIndexOf(":");
            var1[0][var5] = var7.substring(0, var8);
            var2[0][var5] = Integer.parseInt(var7.substring(var8 + 1));
        }

    }

    private static void int2bytes(int var0, byte[] var1, int var2) {
        var1[var2 + 0] = (byte)((var0 & -16777216) / 16777216);
        var1[var2 + 1] = (byte)((var0 & 16711680) / 65536);
        var1[var2 + 2] = (byte)((var0 & '\uff00') / 256);
        var1[var2 + 3] = (byte)(var0 & 255);
    }

    private static int bytes2int(byte[] var0, int var1) {
        return (var0[var1 + 0] & 255) * 16777216 + (var0[var1 + 1] & 255) * 65536 + (var0[var1 + 2] & 255) * 256 + (var0[var1 + 3] & 255);
    }

    private static int bytes2str(byte[] var0, int var1) {
        if (var0.length <= var1) {
            return 0;
        } else {
            int var2;
            for(var2 = var1; var2 < var0.length && var0[var2] != 0; ++var2) {
            }

            return var2 - var1;
        }
    }

    private void DECRYPT_DATA_WITH_KEY(byte[] var1, byte[] var2, int var3) {
        this.ENCRYPT_DATA_WITH_KEY(var1, var2, var3);
    }

    private void ENCRYPT_DATA_WITH_KEY(byte[] var1, byte[] var2, int var3) {
        boolean var6 = false;
        Object var7 = null;
        if (var1 != null) {
            int var8;
            byte[] var9;
            if (var2 != null && var2.length != 0) {
                var8 = var2.length;
                var9 = var2;
            } else {
                var8 = g_default_encrypt_key.length;
                var9 = g_default_encrypt_key;
            }

            int var5 = 0;

            for(int var4 = var3; var4 < var1.length; ++var4) {
                if (var5 >= var8) {
                    var5 = 0;
                }

                var1[var4] ^= var9[var5];
                ++var5;
            }

        }
    }
}
