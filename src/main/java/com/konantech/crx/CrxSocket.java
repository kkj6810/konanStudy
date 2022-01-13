package com.konantech.crx;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

class CrxSocket {
    private Socket m_socket = null;
    private int m_svr_rc;
    private byte[] m_recv = null;
    private byte[] m_send = null;
    private int m_time_out_sec;
    private int m_time_out_usec;
    private String m_msg = "";
    private static final int g_xor_key = -1446176383;
    private static final int SCK_ALIVE_SELF = 0;
    private static final int SCK_ALIVE_PEER = 1;
    private static final int SCK_ERROR_SHUTDOWN = -2;

    public CrxSocket() {
        this.m_socket = null;
        this.m_svr_rc = 0;
        this.m_recv = null;
        this.m_send = null;
        this.m_time_out_sec = 300;
        this.m_time_out_usec = 0;
    }

    public void setTimeOut(int var1, int var2) {
        this.m_time_out_sec = var1;
        this.m_time_out_usec = var2;
    }

    public int setLingerTimeOut(int var1) throws SocketException {
        if (this.m_socket == null) {
            this.m_msg = "cannot set linger option on socket : socket is null";
            return -1;
        } else {
            if (var1 > 0) {
                this.m_socket.setSoLinger(true, var1);
            } else {
                this.m_socket.setSoLinger(true, 0);
            }

            return 0;
        }
    }

    public int setTcpNoDelay(int var1) throws SocketException {
        if (this.m_socket == null) {
            this.m_msg = "cannot set linger option on socket : socket is null";
            return -1;
        } else {
            if (var1 > 0) {
                this.m_socket.setTcpNoDelay(true);
            } else {
                this.m_socket.setTcpNoDelay(false);
            }

            return 0;
        }
    }

    public int setReuseAddress(int var1) throws SocketException {
        if (this.m_socket == null) {
            this.m_msg = "cannot set linger option on socket : socket is null";
            return -1;
        } else {
            if (var1 > 0) {
                this.m_socket.setReuseAddress(true);
            } else {
                this.m_socket.setReuseAddress(false);
            }

            return 0;
        }
    }

    public int connect(String var1, int var2) throws SocketException {
        byte var3 = 0;
        int var4 = this.m_time_out_sec * 1000 + this.m_time_out_usec;

        try {
            InetSocketAddress var5 = new InetSocketAddress(var1, var2);
            this.m_socket = new Socket();
            this.m_socket.connect(var5, var4);
        } catch (SocketTimeoutException var6) {
            this.m_msg = "timeout expires before connecting (" + var1 + ":" + var2 + ")";
            var3 = -5;
        } catch (IOException var7) {
            this.m_msg = "cannot connect to server (" + var1 + ":" + var2 + ")";
            var3 = -5;
        }

        return var3;
    }

    public int disconnect() throws IOException {
        if (this.m_socket != null) {
            this.m_socket.close();
        }

        return 0;
    }

    public int send(int var1, byte[] var2) throws SocketException {
        byte var3 = 0;

        try {
            OutputStream var4 = this.m_socket.getOutputStream();
            var4.write(var2);
        } catch (IOException var6) {
            this.m_msg = "cannot send data to server";
            var3 = -1;
        } catch (NullPointerException var7) {
            this.m_msg = "cannot send data to server";
            var3 = -1;
        }

        return var3;
    }

    public int send(int var1, int var2, int var3, byte[] var4) throws SocketException {
        boolean var5 = false;
        boolean var8 = false;
        int var6 = 16 + var3;
        int var7 = var6 ^ -1446176383;
        this.m_send = new byte[var6];
        CrxUtil.int2bytes(var6, this.m_send, 0);
        CrxUtil.int2bytes(var7, this.m_send, 4);
        CrxUtil.int2bytes(var2, this.m_send, 8);
        CrxUtil.int2bytes(var1, this.m_send, 12);

        for(int var10 = 0; var10 < var3; ++var10) {
            this.m_send[var10 + 16] = var4[var10];
        }

        int var9 = this.send(var6, this.m_send);
        if (var9 < 0) {
            if (this.m_msg.length() == 0) {
                this.m_msg = "cannot send data to server";
            }

            return -1;
        } else {
            return 0;
        }
    }

    public int recv() throws IOException {
        Object var1 = null;
        boolean var2 = false;
        boolean var3 = false;
        byte[] var4 = this.receive();
        if (var4 == null) {
            return -1;
        } else if (var4.length < 16) {
            this.m_msg = "cannot receive response header " + var4.length + "/16 (C218463)";
            return -1;
        } else {
            this.m_svr_rc = CrxUtil.bytes2int(var4, 8);
            int var5 = var4.length - 16;
            this.m_recv = new byte[var5];

            for(int var6 = 0; var6 < var5; ++var6) {
                this.m_recv[var6] = var4[var6 + 16];
            }

            return 0;
        }
    }

    public int getRecv_svr_rc() {
        return this.m_svr_rc;
    }

    public byte[] getRecv_data() {
        return this.m_recv;
    }

    public String getMessage() {
        return this.m_msg;
    }

    private byte[] receive() throws IOException {
        boolean var1 = false;
        byte[] var5 = new byte[8];
        int var7 = this.receiveData(var5, 8);
        if (var7 < 0) {
            if (this.m_msg.length() == 0) {
                this.m_msg = "cannot receive packet header (C6831)";
            }

            return null;
        } else {
            int var2 = CrxUtil.bytes2int(var5, 0);
            int var4 = CrxUtil.bytes2int(var5, 4);
            var4 ^= -1446176383;
            if (var2 != var4) {
                this.m_msg = "incompatible client version (" + var2 + ", " + var4 + ", " + -1446176383 + ")";
                return null;
            } else {
                int var3 = var2 - 8;
                byte[] var6 = new byte[var2];
                var7 = this.receiveData(var6, var3);
                if (var7 < 0) {
                    if (this.m_msg.length() == 0) {
                        this.m_msg = "cannot receive packet header (C6831)";
                    }

                    return null;
                } else {
                    System.arraycopy(var6, 0, var6, 8, var3);
                    System.arraycopy(var5, 0, var6, 0, 8);
                    return var6;
                }
            }
        }
    }

    private int receiveData(byte[] var1, int var2) throws IOException {
        int var3 = 0;
        int var4 = var2;
        if (this.m_time_out_sec > 0) {
            this.m_socket.setSoTimeout(this.m_time_out_sec * 1000 + this.m_time_out_usec);
        }

        int var6;
        for(InputStream var5 = this.m_socket.getInputStream(); var4 > 0; var4 -= var6) {
            try {
                var6 = var5.read(var1, var3, var4);
            } catch (SocketException var8) {
                this.m_msg = "Socket Error : " + var8.getMessage();
                return -1;
            } catch (SocketTimeoutException var9) {
                this.m_msg = "Socket Error : " + var9.getMessage();
                return -1;
            }

            if (var6 < 0) {
                break;
            }

            var3 += var6;
        }

        if (var4 != 0) {
            this.m_msg = "cannot receive " + var4 + " bytes (C6833)";
            return -1;
        } else {
            return var3;
        }
    }

    public String getIPv4SocketAddress() {
        String var1 = null;

        try {
            InetAddress var2 = InetAddress.getLocalHost();
            byte[] var3 = var2.getAddress();
            int[] var4 = new int[]{var3[0] < 0 ? 256 + var3[0] : var3[0], var3[1] < 0 ? 256 + var3[1] : var3[1], var3[2] < 0 ? 256 + var3[2] : var3[2], var3[3] < 0 ? 256 + var3[3] : var3[3]};
            var1 = var4[0] + "." + var4[1] + "." + var4[2] + "." + var4[3];
        } catch (UnknownHostException var5) {
            this.m_msg = "unknown host exception : " + var5.toString() + " (C6834)";
            var1 = "0.0.0.0";
            return var1;
        }

        if (var1.length() == 0) {
            var1 = "0.0.0.0";
        }

        return var1;
    }
}
