package com.konantech.klbrk;

import java.io.IOException;
import java.util.Vector;

public class KlbrkClient {
    private String m_msg = "";
    public static final int TIMEOUT_REQUEST = 1;
    public static final int TIMEOUT_LINGER = 2;
    public static final int TCP_NODELAY = 3;
    public static final int SOCKET_REUSE_ADDRESS = 4;
    private Vector<KlbrkKern> vctKlbrk = new Vector();

    public KlbrkClient() {
    }

    public long KLBRK_CreateHandle() {
        int var1 = this.vctKlbrk.size();
        this.vctKlbrk.add(new KlbrkKern());
        KlbrkKern var2 = (KlbrkKern)this.vctKlbrk.get(var1);
        if (var2 == null) {
            this.m_msg = "invalid handle";
            return -1L;
        } else {
            return (long)var1;
        }
    }

    public int KLBRK_DestroyHandle(long var1) {
        KlbrkKern var3 = (KlbrkKern)this.vctKlbrk.get((int)var1);
        if (var3 == null) {
            this.m_msg = "invalid handle";
            return -1;
        } else {
            this.vctKlbrk.remove((int)var1);
            return 0;
        }
    }

    public int KLBRK_SetOption(long var1, int var3, int var4) throws IOException {
        boolean var5 = false;
        KlbrkKern var6 = (KlbrkKern)this.vctKlbrk.get((int)var1);
        if (var6 == null) {
            this.m_msg = "invalid handle";
            return -1;
        } else {
            int var7 = var6.SetOption(var3, var4);
            if (var7 < 0) {
                this.m_msg = var6.GetMessage();
            }

            return var7;
        }
    }

    public int KLBRK_ModuleQuery(long var1, String var3, int var4, KlbrkIO var5, KlbrkIO var6) throws IOException {
        boolean var7 = false;
        KlbrkKern var8 = (KlbrkKern)this.vctKlbrk.get((int)var1);
        if (var8 == null) {
            this.m_msg = "invalid handle";
            return -1;
        } else {
            int var9 = var8.ModuleQuery(var3, var4, var5, var6);
            if (var9 < 0) {
                this.m_msg = var8.GetMessage();
            }

            return var9;
        }
    }

    public String KLBRK_GetMessage() {
        return this.m_msg;
    }
}
