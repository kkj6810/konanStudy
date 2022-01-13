package com.konantech.klbrk;

import java.io.IOException;

class KlbrkKern {
    private String m_msg = "";
    private KlbrkSocket m_socket = null;
    private int m_timeout_request = 15;
    private int m_timeout_linger = 0;
    private int m_tcp_nodelay = 0;
    private int m_socket_reuse_address = 0;
    private String m_char_encoding = null;
    private static final int KLBRK_XOR_KEY = -709282654;
    private static final int REQUEST_MODULE_QUERY = 99;
    public static final int TIMEOUT_REQUEST = 1;
    public static final int TIMEOUT_LINGER = 2;
    public static final int TCP_NODELAY = 3;
    public static final int SOCKET_REUSE_ADDRESS = 4;

    public KlbrkKern() {
        this.m_socket = new KlbrkSocket();
        this.m_char_encoding = System.getProperty("file.encoding");
    }

    public int SetOption(int var1, int var2) throws IOException {
        switch(var1) {
            case 1:
                this.m_timeout_request = var2;
                break;
            case 2:
                this.m_timeout_linger = var2;
                break;
            case 3:
                this.m_tcp_nodelay = var2;
                break;
            case 4:
                this.m_socket_reuse_address = var2;
                break;
            default:
                this.m_msg = "unknown option " + var1;
        }

        return 0;
    }

    public int ModuleQuery(String var1, int var2, KlbrkIO var3, KlbrkIO var4) throws IOException {
        boolean var5 = false;
        Object var6 = null;
        boolean var7 = false;
        Object var8 = null;
        String var9 = null;
        boolean var10 = false;
        byte[] var12 = var3.GetRequestPacket();
        if (var12 == null) {
            this.m_msg = var3.GetMessage();
            return -1;
        } else {
            if (this.m_timeout_request > 0) {
                this.m_socket.setTimeOut(this.m_timeout_request, 0);
            }

            int var11 = this.m_socket.connect(var1, var2);
            if (var11 < 0) {
                this.m_msg = this.m_socket.getMessage();
            } else {
                label53: {
                    this.m_socket.setLingerTimeOut(this.m_timeout_linger);
                    if (this.m_tcp_nodelay > 0) {
                        var11 = this.m_socket.setTcpNoDelay(this.m_tcp_nodelay);
                        if (var11 < 0) {
                            this.m_msg = this.m_socket.getMessage();
                            break label53;
                        }
                    }

                    if (this.m_socket_reuse_address > 0) {
                        var11 = this.m_socket.setReuseAddress(this.m_socket_reuse_address);
                        if (var11 < 0) {
                            this.m_msg = this.m_socket.getMessage();
                            break label53;
                        }
                    }

                    var11 = this.m_socket.send(0, 99, var12.length, var12);
                    if (var11 < 0) {
                        this.m_msg = this.m_socket.getMessage();
                    } else {
                        var11 = 0;
                    }
                }
            }

            if (var11 == 0) {
                var11 = this.m_socket.recv();
                if (var11 != 0) {
                    this.m_msg = this.m_socket.getMessage();
                } else {
                    int var13 = this.m_socket.getRecv_svr_rc();
                    byte[] var14 = this.m_socket.getRecv_data();
                    if (var13 != 0) {
                        int var15 = KlbrkUtil.bytes2str(var14, 0);
                        var9 = new String(var14, 0, var15, this.m_char_encoding);
                        this.m_msg = var9;
                    } else {
                        var11 = var4.SetResponsePacket(var14);
                        if (var11 < 0) {
                            this.m_msg = var4.GetMessage();
                        } else {
                            this.m_socket.disconnect();
                            var11 = 0;
                        }
                    }
                }
            }

            if (var11 < 0) {
                this.m_socket.disconnect();
            }

            return var11;
        }
    }

    public String GetMessage() {
        return this.m_msg;
    }
}
