package com.konantech.konansearch;

import java.io.IOException;
import java.util.Vector;

public class KSEARCH {
    private Vector<KSClient> vKS = new Vector();
    public static final int LC_DEFAULT = 0;
    public static final int LC_KOREAN = 1;
    public static final int LC_CHINESE = 2;
    public static final int LC_JAPANESE = 3;
    public static final int LC_ENGLISH = 4;
    public static final int LC_UNIVERSAL = 5;
    public static final int LC_USER0 = 6;
    public static final int LC_USER1 = 7;
    public static final int LC_USER2 = 8;
    public static final int LC_USER3 = 9;
    public static final int LC_USER4 = 10;
    public static final int LC_USER5 = 11;
    public static final int LC_USER6 = 12;
    public static final int LC_USER7 = 13;
    public static final int LC_USER8 = 14;
    public static final int LC_USER9 = 15;
    public static final int LC_DANISH = 16;
    public static final int LC_DUTCH = 17;
    public static final int LC_FINNISH = 18;
    public static final int LC_FRENCH = 19;
    public static final int LC_GERMAN = 20;
    public static final int LC_ITALIAN = 21;
    public static final int LC_NORWEGIAN = 22;
    public static final int LC_PORTUGUESE = 23;
    public static final int LC_SPANISH = 24;
    public static final int LC_SWEDISH = 25;
    public static final int LC_RUSSIAN = 26;
    public static final int LC_ARABIC = 27;
    public static final int LC_TURKISH = 28;
    public static final int CS_DEFAULT = 0;
    public static final int CS_EUCKR = 1;
    public static final int CS_EUCCN = 2;
    public static final int CS_EUCJP = 3;
    public static final int CS_UTF8 = 4;
    public static final int CS_USASCII = 5;
    public static final int CS_BIG5 = 6;
    public static final int CS_SJIS = 7;
    public static final int CS_USER0 = 8;
    public static final int CS_USER1 = 9;
    public static final int CS_USER2 = 10;
    public static final int CS_USER3 = 11;
    public static final int CS_USER4 = 12;
    public static final int CS_USER5 = 13;
    public static final int CS_USER6 = 14;
    public static final int CS_USER7 = 15;
    public static final int CS_USER8 = 16;
    public static final int CS_USER9 = 17;
    public static final int CS_LATIN1 = 18;
    public static final int CS_LATIN2 = 19;
    public static final int CS_LATIN3 = 20;
    public static final int CS_LATIN4 = 21;
    public static final int CS_LATIN5 = 22;
    public static final int CS_LATIN6 = 23;
    public static final int CS_LATIN7 = 24;
    public static final int CS_LATIN8 = 25;
    public static final int CS_LATIN9 = 26;
    public static final int CS_CYRILLIC = 27;
    public static final int CS_ARABIC = 28;
    public static final int CS_GREEK = 29;
    public static final int CS_HEBREW = 30;
    public static final int CS_THAI = 31;
    public static final int CS_ENGLISH = 5;
    public static final int CS_DANISH = 18;
    public static final int CS_DUTCH = 18;
    public static final int CS_FINNISH = 18;
    public static final int CS_FRENCH = 18;
    public static final int CS_GERMAN = 18;
    public static final int CS_ITALIAN = 18;
    public static final int CS_NORWEGIAN = 18;
    public static final int CS_PORTUGUESE = 18;
    public static final int CS_SPANISH = 18;
    public static final int CS_SWEDISH = 18;
    public static final int CS_RUSSIAN = 27;
    public static final int CS_TURKISH = 22;
    public static final int CS_NORDIC = 23;
    public static final int QEXP_K2K = 1;
    public static final int QEXP_K2E = 2;
    public static final int QEXP_E2E = 4;
    public static final int QEXP_E2K = 8;
    public static final int QEXP_TRL = 16;
    public static final int QEXP_RCM = 32;
    public static final int QPP_OP_NONE = 0;
    public static final int QPP_OP_EQ = 1;
    public static final int QPP_OP_LT = 2;
    public static final int QPP_OP_LE = 3;
    public static final int QPP_OP_GT = 4;
    public static final int QPP_OP_GE = 5;
    public static final int QPP_DOMAIN_NONE = 0;
    public static final int QPP_DOMAIN_CAR = 1;
    public static final int OPTION_SOCKET_TIMEOUT_REQUEST = 1;
    public static final int OPTION_SOCKET_TIMEOUT_LINGER = 2;
    public static final int OPTION_SOCKET_3WAY_MODE = 3;
    public static final int OPTION_SOCKET_ASYNC_REQUEST = 4;
    public static final int OPTION_SOCKET_CONNECTION_TIMEOUT_MSEC = 6;
    public static final int OPTION_SOCKET_REQUEST_TIMEOUT_MSEC = 7;
    public static final int OPTION_REQUEST_PRIORITY = 10;
    public static final int OPTION_REQUEST_CHARSET_UTF8 = 11;
    public static final int OPTION_RETRY_ON_NETWORK_ERROR = 20;
    public static final int OPTION_REPORT_CLIENT_ERROR = 30;
    public static final int OPTION_SOCKET_RETRY_ON_ERROR = 40;
    public static final int TRL_LANGUAGE_ENGLISH = 1;
    public static final int TRL_LANGUAGE_KOREAN = 2;
    public static final int TRL_LANGUAGE_MIX = 3;
    public static final int FILE_TYPE_AUTO = 0;
    public static final int FILE_TYPE_HTML = 1;
    public static final int FLT_F2F_MODE = 0;
    public static final int FLT_B2B_MODE = 1;
    public static final int FLT_F2B_MODE = 2;
    public static final int FLT_B2F_MODE = 3;

    public KSEARCH() {
    }

    public long CreateHandle() {
        int var1 = this.vKS.size();
        this.vKS.add(new KSClient());
        return (long)var1;
    }

    public int DestroyHandle(long var1) {
        KSClient var3 = (KSClient)this.vKS.get((int)var1);
        if (var3 == null) {
            return -1;
        } else {
            var3.Cleanup();
            this.vKS.set((int)var1, (KSClient) null);
            return 0;
        }
    }

    public int SetOption(long var1, int var3, int var4) {
        KSClient var5 = (KSClient)this.vKS.get((int)var1);
        if (var5 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var5.SetOption(var3, var4);
        }
    }

    public int SetSearchOption_Timeout(long var1, int var3) {
        KSClient var4 = (KSClient)this.vKS.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var4.SetTimeOut(var3);
        }
    }

    public String GetErrorMessage(long var1) {
        KSClient var3 = (KSClient)this.vKS.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return "";
        } else {
            return var3.GetMessage();
        }
    }

    public int SetSearchOption_Cluster(long var1, int var3, int var4, String var5, String[] var6, int var7, String var8, int var9) {
        KSClient var10 = (KSClient)this.vKS.get((int)var1);
        if (var10 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var10.SetClusterCondition(var3, var4, var5, var6, var8, var9);
        }
    }

    public int SetSearchOption_RelatedTerm(long var1, int var3) {
        KSClient var4 = (KSClient)this.vKS.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var4.SetRelatedTermCondition(var3);
        }
    }

    public int SetSearchOption_ExpandQuery(long var1, String var3, int var4) {
        KSClient var5 = (KSClient)this.vKS.get((int)var1);
        if (var5 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var5.SetExpandQueryCondition(var3, var4);
        }
    }

    public int SubmitQuery(long var1, String var3, int var4, String var5, String var6, String var7, String var8, String var9, String var10, int var11, int var12, int var13, int var14) throws IOException {
        KSClient var15 = (KSClient)this.vKS.get((int)var1);
        if (var15 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var15.SubmitQuery(var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14);
        }
    }

    public int Select(long var1, String var3, String var4, String var5, String var6, String var7, int var8, int var9, int var10, int var11) throws IOException {
        KSClient var12 = (KSClient)this.vKS.get((int)var1);
        if (var12 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var12.Select(var3, var4, var5, var6, var7, var8, var9, var10, var11);
        }
    }

    public int Insert(long var1, String var3, String var4, String[] var5, String[] var6, int[] var7, int var8, int var9, int var10, int var11) throws IOException {
        KSClient var12 = (KSClient)this.vKS.get((int)var1);
        if (var12 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var12.Insert(var3, var4, var5, var6, var7, var8, var9, var10, var11);
        }
    }

    public int Insert(long var1, String var3, String var4, String[] var5, String[] var6, int var7, int var8, int var9, int var10) throws IOException {
        String var11 = "";
        int[] var12 = new int[var7];
        boolean var13 = false;
        KSClient var14 = (KSClient)this.vKS.get((int)var1);
        if (var14 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            var11 = var14.GetCharacterEncoding();

            for(int var15 = 0; var15 < var7; ++var15) {
                var12[var15] = var6[var15].getBytes(var11).length;
            }

            return var14.Insert(var3, var4, var5, var6, var12, var7, var8, var9, var10);
        }
    }

    public int Delete(long var1, String var3, String var4, String var5, int var6, int var7, int var8) throws IOException {
        KSClient var9 = (KSClient)this.vKS.get((int)var1);
        if (var9 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var9.Delete(var3, var4, var5, var6, var7, var8);
        }
    }

    public int Update(long var1, String var3, String var4, String var5, String[] var6, String[] var7, int[] var8, int var9, int var10, int var11, int var12) throws IOException {
        KSClient var13 = (KSClient)this.vKS.get((int)var1);
        if (var13 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var13.Update(var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
        }
    }

    public int Update(long var1, String var3, String var4, String var5, String[] var6, String[] var7, int var8, int var9, int var10, int var11) throws IOException {
        String var12 = "";
        int[] var13 = new int[var8];
        boolean var14 = false;
        KSClient var15 = (KSClient)this.vKS.get((int)var1);
        if (var15 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            var12 = var15.GetCharacterEncoding();

            for(int var16 = 0; var16 < var8; ++var16) {
                var13[var16] = var7[var16].getBytes(var12).length;
            }

            return var15.Update(var3, var4, var5, var6, var7, var13, var8, var9, var10, var11);
        }
    }

    public int GetResult_TotalCount(long var1) throws IOException {
        KSClient var3 = (KSClient)this.vKS.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.GetTotalCount();
        }
    }

    public int GetResult_RowSize(long var1) throws IOException {
        KSClient var3 = (KSClient)this.vKS.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.GetRowSize();
        }
    }

    public int GetResult_ColumnSize(long var1) throws IOException {
        KSClient var3 = (KSClient)this.vKS.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.GetColumnSize();
        }
    }

    public int GetResult_ColumnName(long var1, String[] var3, int var4) throws IOException {
        KSClient var5 = (KSClient)this.vKS.get((int)var1);
        if (var5 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var5.GetColumnName(var3, var4);
        }
    }

    public int GetResult_Row(long var1, String[] var3, int var4) throws IOException {
        KSClient var5 = (KSClient)this.vKS.get((int)var1);
        if (var5 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var5.GetRow(var3, var4);
        }
    }

    public int GetResult_ROWID(long var1, int[] var3, int[] var4) throws IOException {
        KSClient var5 = (KSClient)this.vKS.get((int)var1);
        if (var5 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var5.GetRecordNoArray(var3, var4);
        }
    }

    public int GetResult_Cluster(long var1, String[] var3, int[][] var4, int[] var5) throws IOException {
        KSClient var6 = (KSClient)this.vKS.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var6.GetClusters(var3, var4, var5);
        }
    }

    public int GetResult_Cluster_Count(long var1) throws IOException {
        KSClient var3 = (KSClient)this.vKS.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.GetClusterCount();
        }
    }

    public String GetResult_RelatedTerm(long var1) throws IOException {
        KSClient var3 = (KSClient)this.vKS.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            return var3.GetRelatedTerms();
        }
    }

    public int GetResult_RelatedTerm_Count(long var1) throws IOException {
        KSClient var3 = (KSClient)this.vKS.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.GetRelatedTermCount();
        }
    }

    public int GetResult_SearchTime(long var1) throws IOException {
        KSClient var3 = (KSClient)this.vKS.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.GetSearchTime();
        }
    }

    public String GetResult_ExpandQuery(long var1, int var3) throws IOException {
        KSClient var4 = (KSClient)this.vKS.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            return var4.GetExpandQuery(var3);
        }
    }

    public int GetResult_ExpandQuery_Count(long var1, int var3) throws IOException {
        KSClient var4 = (KSClient)this.vKS.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var4.GetExpandQueryCount(var3);
        }
    }

    public int GetQueryAttribute(long var1, String var3, String[] var4, int[] var5, String[] var6, int[] var7, String[] var8, String[] var9, String var10, int var11, int var12) throws IOException {
        KSClient var13 = (KSClient)this.vKS.get((int)var1);
        if (var13 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var13.GetQueryAttribute(var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
        }
    }

    public int SetAuthCode(long var1, String var3) {
        KSClient var4 = (KSClient)this.vKS.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var4.SetAuthCode(var3);
        }
    }

    public int SetLog(long var1, String var3) {
        KSClient var4 = (KSClient)this.vKS.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var4.SetLog(var3);
        }
    }

    public int SetCharacterEncoding(long var1, String var3) {
        KSClient var4 = (KSClient)this.vKS.get((int)var1);
        if (var4 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var4.SetCharacterEncoding(var3);
        }
    }

    public String GetCharacterEncoding(long var1) {
        KSClient var3 = (KSClient)this.vKS.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            return var3.GetCharacterEncoding();
        }
    }

    public int CompleteKeyword(long var1, String var3, String[] var4, String[] var5, int var6, String var7, int var8, int var9) throws IOException {
        KSClient var10 = (KSClient)this.vKS.get((int)var1);
        if (var10 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var10.CompleteKeyword(var3, var4, (int[])null, (String[])null, (String[])null, var5, var6, var7, var8, var9);
        }
    }

    public int CompleteKeyword2(long var1, String var3, String[] var4, int[] var5, String[] var6, String[] var7, String[] var8, int var9, String var10, int var11, int var12) throws IOException {
        KSClient var13 = (KSClient)this.vKS.get((int)var1);
        if (var13 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var13.CompleteKeyword(var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
        }
    }

    public int SpellCheck(long var1, String var3, String[] var4, int var5, String var6) throws IOException {
        KSClient var7 = (KSClient)this.vKS.get((int)var1);
        if (var7 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var7.SpellCheck(var3, var4, var5, var6);
        }
    }

    public int RecommendKeyword(long var1, String var3, String[] var4, int var5, String var6, int var7) throws IOException {
        KSClient var8 = (KSClient)this.vKS.get((int)var1);
        if (var8 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var8.RecommendKeyword(var3, var4, var5, var6, var7);
        }
    }

    public int GetPopularKeyword(long var1, String var3, String[] var4, int var5, int var6) throws IOException {
        KSClient var7 = (KSClient)this.vKS.get((int)var1);
        if (var7 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var7.GetPopularKeyword(var3, var4, (String[])null, var5, var6);
        }
    }

    public int GetPopularKeyword2(long var1, String var3, String[] var4, String[] var5, int var6, int var7) throws IOException {
        KSClient var8 = (KSClient)this.vKS.get((int)var1);
        if (var8 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var8.GetPopularKeyword(var3, var4, var5, var6, var7);
        }
    }

    public int AnchorText(long var1, String var3, String[] var4, String var5, String var6, String var7, int var8) throws IOException {
        KSClient var9 = (KSClient)this.vKS.get((int)var1);
        if (var9 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var9.AnchorText(var3, var4, var5, var6, var7, var8);
        }
    }

    public int GetSynonymList(long var1, String var3, int[] var4, String[][] var5, int var6, String var7, int var8, int var9, int var10, int var11, int var12, int var13) throws IOException {
        KSClient var14 = (KSClient)this.vKS.get((int)var1);
        if (var14 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var14.GetSynonymList(var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
        }
    }

    public int ExtractKeyword(long var1, String var3, String[] var4, int var5, String var6, String var7, int var8, int var9, int var10, int var11) throws IOException {
        KSClient var12 = (KSClient)this.vKS.get((int)var1);
        if (var12 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var12.ExtractKeyword(var3, var4, var5, var6, var7, var8, var9, var10, var11);
        }
    }

    public int Hilite(long var1, String var3, String var4, String var5, int var6, int[] var7, String[] var8, String var9, String var10, String var11, String var12, int[] var13, int[] var14, String var15, int var16, int var17) throws IOException {
        KSClient var18 = (KSClient)this.vKS.get((int)var1);
        if (var18 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var18.Hilite(var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17);
        }
    }

    public int IndexSearch(long var1, int[] var3, int[] var4, String[] var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, int[] var14, int[] var15, String var16, int var17, int var18, int var19, int var20) throws IOException {
        KSClient var21 = (KSClient)this.vKS.get((int)var1);
        if (var21 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var21.IndexSearch(var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20);
        }
    }

    public int Search(long var1, String var3, String var4, String var5, String var6, String var7, String var8, int var9, int var10, int var11, int var12) throws IOException {
        KSClient var13 = (KSClient)this.vKS.get((int)var1);
        if (var13 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var13.Search(var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
        }
    }

    public int GetRealTimePopularKeyword(long var1, String var3, String[] var4, String[] var5, int var6, int var7, int var8) throws IOException {
        KSClient var9 = (KSClient)this.vKS.get((int)var1);
        if (var9 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var9.GetRealTimePopularKeyword(var3, var4, var5, var6, var7, var8);
        }
    }

    public int GetResult_GroupBy_GroupCount(long var1) throws IOException {
        KSClient var3 = (KSClient)this.vKS.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.GetResult_GroupBy_GroupCount();
        }
    }

    public int GetResult_GroupBy_KeyCount(long var1) throws IOException {
        KSClient var3 = (KSClient)this.vKS.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.GetResult_GroupBy_KeyCount();
        }
    }

    public int GetResult_GroupBy(long var1, String[][] var3, int[] var4, int var5) throws IOException {
        KSClient var6 = (KSClient)this.vKS.get((int)var1);
        if (var6 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var6.GetResult_GroupBy(var3, var4, var5);
        }
    }

    public int GetResult_ROWID2(long var1, int[] var3, String[] var4, String[] var5, String[] var6, int[] var7, int[] var8, int var9) throws IOException {
        KSClient var10 = (KSClient)this.vKS.get((int)var1);
        if (var10 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var10.GetResultROWID2(var3, var4, var5, var6, var7, var8, var9);
        }
    }

    public int CensorSearchWords(long var1, String var3, String[] var4, int var5, String var6, int var7) throws IOException {
        KSClient var8 = (KSClient)this.vKS.get((int)var1);
        if (var8 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var8.CensorSearchWords(var3, var4, var5, var6, var7);
        }
    }

    public int Transliterate(long var1, String var3, String[] var4, int var5, String var6, int var7, int var8) throws IOException {
        KSClient var9 = (KSClient)this.vKS.get((int)var1);
        if (var9 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var9.Transliterate(var3, var4, var5, var6, var7, var8);
        }
    }

    public int PutCache(long var1, String var3, int var4, String var5, int var6, String var7, String var8, int var9) throws IOException {
        KSClient var10 = (KSClient)this.vKS.get((int)var1);
        if (var10 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var10.PutCache(var3, var4, var5, var6, var7, var8, var9);
        }
    }

    public int GetCache(long var1, String var3, int var4, String var5, int var6) throws IOException {
        KSClient var7 = (KSClient)this.vKS.get((int)var1);
        if (var7 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var7.GetCache(var3, var4, var5, var6);
        }
    }

    public int GetCache_HitFlag(long var1) throws IOException {
        KSClient var3 = (KSClient)this.vKS.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return -1;
        } else {
            return var3.GetCache_HitFlag();
        }
    }

    public String GetCache_Data(long var1) throws IOException {
        KSClient var3 = (KSClient)this.vKS.get((int)var1);
        if (var3 == null) {
            System.err.println("invalid handle");
            return null;
        } else {
            return var3.GetCache_Data();
        }
    }
}
