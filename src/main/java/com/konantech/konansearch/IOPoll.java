package com.konantech.konansearch;

class IOPoll {
    public long m_start_msec = 0L;
    public int m_timeout_msec = 0;

    public IOPoll(long var1, int var3) {
        this.m_start_msec = var1;
        this.m_timeout_msec = var3;
    }

    public boolean TimedOut() {
        if (this.m_timeout_msec <= 0) {
            return false;
        } else {
            return System.currentTimeMillis() - this.m_start_msec >= (long)this.m_timeout_msec;
        }
    }
}
