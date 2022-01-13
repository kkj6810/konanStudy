package com.konantech.klbrk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class KlbrkModBrkSearch {
    private static final int MAX_INT_VALUE = 2147483647;
    private static final int INT_REQ_INIT_VALUE = -1;
    private static final int LC_INIT_VALUE = -1;
    private static final int LC_MIN_VALUE = 0;
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
    private static final int LC_MAX_VALUE = 27;
    private static final int CS_INIT_VALUE = -1;
    private static final int CS_MIN_VALUE = 0;
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
    private static final int CS_MAX_VALUE = 31;
    private String m_char_encoding = null;
    private static final String PROTOCOL_VERSION = "brk-000";
    private static final String MODBRK_VERSION = "001";
    private static final int TYPE_INT = 10;
    private static final int TYPE_STRING = 30;
    private static final int KLBRK_XOR_KEY = -709282654;
    private static final int REQUEST_MODULE_QUERY = 99;
    private KlbrkSocket m_socket = null;
    private int m_timeout_request = 15;
    private int m_timeout_linger = 0;
    private int m_tcp_nodelay = 0;
    private int m_socket_reuse_address = 0;
    private String m_msg = "";
    private String m_req_module_name = "";
    private String m_req_function_name = "";
    private String m_opt_user_log = "";
    private String m_req_config_id = "";
    private String m_req_scenario = "";
    private String m_req_where_clause = "";
    private String m_opt_sort_clause = "";
    private String m_opt_rank_clause = "";
    private String m_opt_search_words = "";
    private String m_opt_extra_log = "";
    private int m_opt_need_ctscore = 0;
    private int m_opt_need_wdscore = 0;
    private int m_req_start_offset = -1;
    private int m_req_fetch_count = -1;
    private int m_req_language = -1;
    private int m_req_charset = -1;
    private int m_res_total_count = 0;
    private int m_res_row_count = 0;
    private int m_res_table_no = 0;
    private String m_res_volume_name = null;
    private String m_res_table_name = null;
    private String m_res_cooked_text = null;
    private int m_res_column_count = 0;
    private String[] m_res_column_name = null;
    private int[] m_res_rowids = null;
    private int[] m_res_scores = null;
    private int[] m_res_ctscores = null;
    private int[] m_res_wdscores = null;
    private String[] m_res_part_infos = null;
    private String[][] m_res_record_values = (String[][])null;
    private int m_res_group_count = 0;
    private String[] m_res_group_keys = null;
    private int[] m_res_group_sizes = null;

    public KlbrkModBrkSearch() {
        this.m_socket = new KlbrkSocket();
        this.m_char_encoding = System.getProperty("file.encoding");
        this.clearRequest();
        this.clearResponse();
    }

    public void clearRequest() {
        this.m_msg = "";
        this.m_req_module_name = "";
        this.m_req_function_name = "";
        this.m_opt_user_log = "";
        this.m_req_config_id = "";
        this.m_req_scenario = "";
        this.m_req_where_clause = "";
        this.m_opt_sort_clause = "";
        this.m_opt_rank_clause = "";
        this.m_opt_search_words = "";
        this.m_opt_extra_log = "";
        this.m_opt_need_ctscore = 0;
        this.m_opt_need_wdscore = 0;
        this.m_req_start_offset = -1;
        this.m_req_fetch_count = -1;
        this.m_req_language = -1;
        this.m_req_charset = -1;
    }

    public void clearResponse() {
        this.m_res_rowids = null;
        this.m_res_scores = null;
        this.m_res_ctscores = null;
        this.m_res_wdscores = null;
        this.m_res_part_infos = null;
        this.m_res_record_values = (String[][])null;
        this.m_res_group_keys = null;
        this.m_res_group_sizes = null;
        this.m_res_total_count = 0;
        this.m_res_row_count = 0;
        this.m_res_table_no = 0;
        this.m_res_volume_name = null;
        this.m_res_table_name = null;
        this.m_res_cooked_text = null;
        this.m_res_column_count = 0;
        this.m_res_column_name = null;
        this.m_res_group_count = 0;
    }

    public void setEncoding(String var1) {
        this.m_char_encoding = var1;
    }

    public void setModuleName(String var1) {
        this.m_req_module_name = var1;
    }

    public void setFunctionName(String var1) {
        this.m_req_function_name = var1;
    }

    public void setUserLog(String var1) {
        this.m_opt_user_log = var1;
    }

    public void setConfigID(String var1) {
        this.m_req_config_id = var1;
    }

    public void setScenario(String var1) {
        this.m_req_scenario = var1;
    }

    public void setWhereClause(String var1) {
        this.m_req_where_clause = var1;
    }

    public void setSortClause(String var1) {
        this.m_opt_sort_clause = var1;
    }

    public void setRankClause(String var1) {
        this.m_opt_rank_clause = var1;
    }

    public void setSearchWords(String var1) {
        this.m_opt_search_words = var1;
    }

    public void setExtraLog(String var1) {
        this.m_opt_extra_log = var1;
    }

    public void setNeedCTScore(int var1) {
        this.m_opt_need_ctscore = var1;
    }

    public void setNeedWDScore(int var1) {
        this.m_opt_need_wdscore = var1;
    }

    public void setStartOffset(int var1) {
        this.m_req_start_offset = var1;
    }

    public void setFetchCount(int var1) {
        this.m_req_fetch_count = var1;
    }

    public void setLanguage(int var1) {
        this.m_req_language = var1;
    }

    public void setCharset(int var1) {
        this.m_req_charset = var1;
    }

    public int getTotalCount() {
        return this.m_res_total_count;
    }

    public int getRowCount() {
        return this.m_res_row_count;
    }

    public String getVolumeName() {
        return this.m_res_volume_name;
    }

    public String getTableName() {
        return this.m_res_table_name;
    }

    public int getTableNo() {
        return this.m_res_table_no;
    }

    public String getCookedText() {
        return this.m_res_cooked_text;
    }

    public int getColumnCount() {
        return this.m_res_column_count;
    }

    public String getColumnName(int var1) {
        return this.checkCol(var1) < 0 ? null : this.m_res_column_name[var1];
    }

    public String[] getColumnNames() {
        return this.m_res_column_name;
    }

    public int getRowid(int var1) {
        return this.checkRow(var1) < 0 ? -1 : this.m_res_rowids[var1];
    }

    public int[] getRowids() {
        return this.m_res_rowids;
    }

    public int getScore(int var1) {
        return this.checkRow(var1) < 0 ? -1 : this.m_res_scores[var1];
    }

    public int[] getScores() {
        return this.m_res_scores;
    }

    public int getCTScore(int var1) {
        return this.checkRow(var1) < 0 ? -1 : this.m_res_ctscores[var1];
    }

    public int[] getCTScores() {
        return this.m_res_ctscores;
    }

    public int getWDScore(int var1) {
        return this.checkRow(var1) < 0 ? -1 : this.m_res_wdscores[var1];
    }

    public int[] getWDScores() {
        return this.m_res_wdscores;
    }

    public String getPartitionInfo(int var1) {
        return this.checkRow(var1) < 0 ? null : this.m_res_part_infos[var1];
    }

    public String[] getPartitionInfos() {
        return this.m_res_part_infos;
    }

    public String getColumnValue(int var1, int var2) {
        if (this.checkRow(var1) < 0) {
            return null;
        } else {
            return this.checkCol(var2) < 0 ? null : this.m_res_record_values[var1][var2];
        }
    }

    public String[][] getColumnValues() {
        return this.m_res_record_values;
    }

    public int getGroupCount() {
        return this.m_res_group_count;
    }

    public String getGroupKey(int var1) {
        return this.checkGroupRow(var1) < 0 ? null : this.m_res_group_keys[var1];
    }

    public String[] getGroupKeys() {
        return this.m_res_group_keys;
    }

    public int getGroupSize(int var1) {
        return this.checkGroupRow(var1) < 0 ? -1 : this.m_res_group_sizes[var1];
    }

    public int[] getGroupSizes() {
        return this.m_res_group_sizes;
    }

    private int checkRow(int var1) {
        if (var1 < 0 && var1 >= this.m_res_row_count) {
            this.m_msg = String.format("out of range (row %d,%d)", var1, this.m_res_row_count);
            return -1;
        } else {
            return 0;
        }
    }

    private int checkCol(int var1) {
        if (var1 < 0 && var1 >= this.m_res_column_count) {
            this.m_msg = String.format("out of range (col %d,%d)", var1, this.m_res_column_count);
            return -1;
        } else {
            return 0;
        }
    }

    private int checkGroupRow(int var1) {
        if (var1 < 0 && var1 >= this.m_res_group_count) {
            this.m_msg = String.format("out of range (group row %d,%d)", var1, this.m_res_group_count);
            return -1;
        } else {
            return 0;
        }
    }

    public void setRequestTimeout(int var1) {
        this.m_timeout_request = var1;
    }

    public void setLingerTimeout(int var1) {
        this.m_timeout_linger = var1;
    }

    public void setTCPNodelay(int var1) {
        this.m_tcp_nodelay = var1;
    }

    public void setSocketReuseAddress(int var1) {
        this.m_socket_reuse_address = var1;
    }

    public int executeQuery(String var1, int var2) throws IOException {
        boolean var3 = false;
        Object var4 = null;
        boolean var5 = false;
        Object var6 = null;
        String var7 = null;
        boolean var8 = false;
        int var9 = this.checkQuery();
        if (var9 < 0) {
            return var9;
        } else {
            byte[] var10 = this.getRequestPacket();
            if (var10 == null) {
                this.m_msg = String.format("cannot make request packet.");
                return -1;
            } else {
                if (this.m_timeout_request > 0) {
                    this.m_socket.setTimeOut(this.m_timeout_request, 0);
                }

                var9 = this.m_socket.connect(var1, var2);
                if (var9 < 0) {
                    this.m_msg = this.m_socket.getMessage();
                } else {
                    label57: {
                        this.m_socket.setLingerTimeOut(this.m_timeout_linger);
                        if (this.m_tcp_nodelay > 0) {
                            var9 = this.m_socket.setTcpNoDelay(this.m_tcp_nodelay);
                            if (var9 < 0) {
                                this.m_msg = this.m_socket.getMessage();
                                break label57;
                            }
                        }

                        if (this.m_socket_reuse_address > 0) {
                            var9 = this.m_socket.setReuseAddress(this.m_socket_reuse_address);
                            if (var9 < 0) {
                                this.m_msg = this.m_socket.getMessage();
                                break label57;
                            }
                        }

                        var9 = this.m_socket.send(0, 99, var10.length, var10);
                        if (var9 < 0) {
                            this.m_msg = this.m_socket.getMessage();
                        } else {
                            var9 = 0;
                        }
                    }
                }

                if (var9 == 0) {
                    var9 = this.m_socket.recv();
                    if (var9 != 0) {
                        this.m_msg = this.m_socket.getMessage();
                    } else {
                        int var11 = this.m_socket.getRecv_svr_rc();
                        byte[] var12 = this.m_socket.getRecv_data();
                        if (var11 != 0) {
                            int var13 = KlbrkUtil.bytes2str(var12, 0);
                            var7 = new String(var12, 0, var13, this.m_char_encoding);
                            this.m_msg = var7;
                        } else {
                            var9 = this.setResponsePacket(var12);
                            if (var9 >= 0) {
                                this.m_socket.disconnect();
                                var9 = 0;
                            }
                        }
                    }
                }

                if (var9 < 0) {
                    this.m_socket.disconnect();
                }

                return var9;
            }
        }
    }

    public String getErrorMessage() {
        return this.m_msg;
    }

    private void WRITE_INT_PARAM(ByteArrayOutputStream var1, int var2) throws IOException {
        KlbrkUtil.BA_writeInteger(var1, 10);
        KlbrkUtil.BA_writeInteger(var1, var2);
        KlbrkUtil.BA_writeAlignSize(var1);
    }

    private void WRITE_STRING_PARAM(ByteArrayOutputStream var1, String var2) throws IOException {
        KlbrkUtil.BA_writeInteger(var1, 30);
        KlbrkUtil.BA_writeInteger(var1, var2.getBytes(this.m_char_encoding).length);
        KlbrkUtil.BA_writeString(var1, var2, this.m_char_encoding);
        KlbrkUtil.BA_writeAlignSize(var1);
    }

    private byte[] getRequestPacket() throws IOException {
        boolean var1 = false;
        boolean var2 = false;
        boolean var3 = false;
        boolean var4 = false;
        boolean var5 = false;
        Object var6 = null;
        ByteArrayOutputStream var7 = null;
        var7 = new ByteArrayOutputStream();
        byte var8 = 0;
        int var10 = KlbrkUtil.ALIGNED_SIZE("brk-000".length() + 1);
        KlbrkUtil.BA_writeInteger(var7, var8);
        KlbrkUtil.BA_writeInteger(var7, var10);
        int var9 = var8 + var10;
        var10 = KlbrkUtil.ALIGNED_SIZE(this.m_req_module_name.length() + 1);
        KlbrkUtil.BA_writeInteger(var7, var9);
        KlbrkUtil.BA_writeInteger(var7, var10);
        var9 += var10;
        var10 = KlbrkUtil.ALIGNED_SIZE(this.m_req_function_name.length() + 1);
        KlbrkUtil.BA_writeInteger(var7, var9);
        KlbrkUtil.BA_writeInteger(var7, var10);
        var9 += var10;
        var10 = KlbrkUtil.ALIGNED_SIZE(this.m_opt_user_log.getBytes(this.m_char_encoding).length + 1);
        KlbrkUtil.BA_writeInteger(var7, var9);
        KlbrkUtil.BA_writeInteger(var7, var10);
        var9 += var10;
        var10 = KlbrkUtil.ALIGNED_SIZE(this.m_req_config_id.length() + 1);
        KlbrkUtil.BA_writeInteger(var7, var9);
        KlbrkUtil.BA_writeInteger(var7, var10);
        KlbrkUtil.BA_writeString(var7, "brk-000");
        KlbrkUtil.BA_writeAlignSize(var7);
        KlbrkUtil.BA_writeString(var7, this.m_req_module_name);
        KlbrkUtil.BA_writeAlignSize(var7);
        KlbrkUtil.BA_writeString(var7, this.m_req_function_name);
        KlbrkUtil.BA_writeAlignSize(var7);
        KlbrkUtil.BA_writeString(var7, this.m_opt_user_log, this.m_char_encoding);
        KlbrkUtil.BA_writeAlignSize(var7);
        KlbrkUtil.BA_writeString(var7, this.m_req_config_id);
        KlbrkUtil.BA_writeAlignSize(var7);
        KlbrkUtil.BA_writeInteger(var7, 13);
        KlbrkUtil.BA_writeAlignSize(var7);
        this.WRITE_STRING_PARAM(var7, "001");
        this.WRITE_STRING_PARAM(var7, this.m_req_scenario);
        this.WRITE_STRING_PARAM(var7, this.m_req_where_clause);
        this.WRITE_STRING_PARAM(var7, this.m_opt_sort_clause);
        this.WRITE_STRING_PARAM(var7, this.m_opt_rank_clause);
        this.WRITE_STRING_PARAM(var7, this.m_opt_search_words);
        this.WRITE_STRING_PARAM(var7, this.m_opt_extra_log);
        this.WRITE_INT_PARAM(var7, this.m_opt_need_ctscore);
        this.WRITE_INT_PARAM(var7, this.m_opt_need_wdscore);
        this.WRITE_INT_PARAM(var7, this.m_req_start_offset);
        this.WRITE_INT_PARAM(var7, this.m_req_fetch_count);
        this.WRITE_INT_PARAM(var7, this.m_req_language);
        this.WRITE_INT_PARAM(var7, this.m_req_charset);
        byte[] var11 = var7.toByteArray();
        return var11;
    }

    private int setResponsePacket(byte[] var1) throws IOException {
        boolean var2 = false;
        boolean var3 = false;
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        byte var7 = 0;
        String var8 = null;
        int var15 = KlbrkUtil.bytes2int(var1, var7) + 40;
        int var16 = KlbrkUtil.bytes2str(var1, var15);
        var8 = new String(var1, var15, var16, this.m_char_encoding);
        if (var8.compareTo("brk-000") != 0) {
            this.m_msg = "incompatible protocol version";
            return -1;
        } else {
            int var17 = var7 + 32;
            var15 = KlbrkUtil.bytes2int(var1, var17) + 40;
            var16 = KlbrkUtil.bytes2str(var1, var15);
            var17 = var15 + KlbrkUtil.ALIGNED_SIZE(var16 + 1);
            KlbrkUtil.bytes2int(var1, var17);
            var17 += 4;
            var17 = KlbrkUtil.ALIGNED_SIZE(var17);
            int var10 = KlbrkUtil.bytes2int(var1, var17 + 4);
            var17 += 8;
            var16 = KlbrkUtil.bytes2str(var1, var17);
            var8 = new String(var1, var17, var16, this.m_char_encoding);
            var17 += KlbrkUtil.ALIGNED_SIZE(var10 + 1);
            if (var8.compareTo("001") != 0) {
                this.m_msg = "incompatible modbrk protocol version";
                return -1;
            } else {
                int var11 = KlbrkUtil.bytes2int(var1, var17 + 4);
                var17 += 8;
                var10 = KlbrkUtil.bytes2int(var1, var17 + 4);
                var17 += 8;
                var16 = KlbrkUtil.bytes2str(var1, var17);
                this.m_msg = new String(var1, var17, var16, this.m_char_encoding);
                var17 += KlbrkUtil.ALIGNED_SIZE(var10 + 1);
                if (var11 != 0) {
                    return -1;
                } else {
                    this.m_res_total_count = KlbrkUtil.bytes2int(var1, var17 + 4);
                    var17 += 8;
                    this.m_res_row_count = KlbrkUtil.bytes2int(var1, var17 + 4);
                    var17 += 8;
                    this.m_res_table_no = KlbrkUtil.bytes2int(var1, var17 + 4);
                    var17 += 8;
                    var10 = KlbrkUtil.bytes2int(var1, var17 + 4);
                    var17 += 8;
                    var16 = KlbrkUtil.bytes2str(var1, var17);
                    this.m_res_volume_name = new String(var1, var17, var16, this.m_char_encoding);
                    var17 += KlbrkUtil.ALIGNED_SIZE(var10 + 1);
                    var10 = KlbrkUtil.bytes2int(var1, var17 + 4);
                    var17 += 8;
                    var16 = KlbrkUtil.bytes2str(var1, var17);
                    this.m_res_table_name = new String(var1, var17, var16, this.m_char_encoding);
                    var17 += KlbrkUtil.ALIGNED_SIZE(var10 + 1);
                    var10 = KlbrkUtil.bytes2int(var1, var17 + 4);
                    var17 += 8;
                    var16 = KlbrkUtil.bytes2str(var1, var17);
                    this.m_res_cooked_text = new String(var1, var17, var16, this.m_char_encoding);
                    var17 += KlbrkUtil.ALIGNED_SIZE(var10 + 1);
                    this.m_res_column_count = KlbrkUtil.bytes2int(var1, var17 + 4);
                    var17 += 8;
                    int var13;
                    if (this.m_res_column_count > 0) {
                        this.m_res_column_name = new String[this.m_res_column_count];

                        for(var13 = 0; var13 < this.m_res_column_count; ++var13) {
                            var10 = KlbrkUtil.bytes2int(var1, var17 + 4);
                            var17 += 8;
                            var16 = KlbrkUtil.bytes2str(var1, var17);
                            this.m_res_column_name[var13] = new String(var1, var17, var16, this.m_char_encoding);
                            var17 += KlbrkUtil.ALIGNED_SIZE(var10 + 1);
                        }
                    }

                    if (this.m_res_row_count > 0) {
                        this.m_res_rowids = new int[this.m_res_row_count];
                        this.m_res_scores = new int[this.m_res_row_count];
                        this.m_res_ctscores = new int[this.m_res_row_count];
                        this.m_res_wdscores = new int[this.m_res_row_count];
                        this.m_res_part_infos = new String[this.m_res_row_count];
                        this.m_res_record_values = new String[this.m_res_row_count][];

                        for(var13 = 0; var13 < this.m_res_row_count; ++var13) {
                            this.m_res_rowids[var13] = KlbrkUtil.bytes2int(var1, var17 + 4);
                            var17 += 8;
                            this.m_res_scores[var13] = KlbrkUtil.bytes2int(var1, var17 + 4);
                            var17 += 8;
                            this.m_res_ctscores[var13] = KlbrkUtil.bytes2int(var1, var17 + 4);
                            var17 += 8;
                            this.m_res_wdscores[var13] = KlbrkUtil.bytes2int(var1, var17 + 4);
                            var17 += 8;
                            var10 = KlbrkUtil.bytes2int(var1, var17 + 4);
                            var17 += 8;
                            var16 = KlbrkUtil.bytes2str(var1, var17);
                            this.m_res_part_infos[var13] = new String(var1, var17, var16, this.m_char_encoding);
                            var17 += KlbrkUtil.ALIGNED_SIZE(var10 + 1);
                            this.m_res_record_values[var13] = new String[this.m_res_column_count];

                            for(int var14 = 0; var14 < this.m_res_column_count; ++var14) {
                                var10 = KlbrkUtil.bytes2int(var1, var17 + 4);
                                var17 += 8;
                                var16 = KlbrkUtil.bytes2str(var1, var17);
                                this.m_res_record_values[var13][var14] = new String(var1, var17, var16, this.m_char_encoding);
                                var17 += KlbrkUtil.ALIGNED_SIZE(var10 + 1);
                            }
                        }
                    }

                    this.m_res_group_count = KlbrkUtil.bytes2int(var1, var17 + 4);
                    var17 += 8;
                    if (this.m_res_group_count > 0) {
                        this.m_res_group_keys = new String[this.m_res_group_count];
                        this.m_res_group_sizes = new int[this.m_res_group_count];

                        for(var13 = 0; var13 < this.m_res_group_count; ++var13) {
                            var10 = KlbrkUtil.bytes2int(var1, var17 + 4);
                            var17 += 8;
                            var16 = KlbrkUtil.bytes2str(var1, var17);
                            this.m_res_group_keys[var13] = new String(var1, var17, var16, this.m_char_encoding);
                            var17 += KlbrkUtil.ALIGNED_SIZE(var10 + 1);
                            this.m_res_group_sizes[var13] = KlbrkUtil.bytes2int(var1, var17 + 4);
                            var17 += 8;
                        }
                    }

                    return 0;
                }
            }
        }
    }

    private int checkQuery() {
        boolean var1 = false;
        int var2;
        if ((var2 = this.checkRequiredString(this.m_req_module_name, "module name")) >= 0 && (var2 = this.checkRequiredString(this.m_req_function_name, "function name")) >= 0 && (var2 = this.checkRequiredString(this.m_req_config_id, "config id")) >= 0 && (var2 = this.checkRequiredString(this.m_req_scenario, "scenario")) >= 0 && (var2 = this.checkRequiredString(this.m_req_where_clause, "where clause")) >= 0 && (var2 = this.checkRequiredInt(this.m_req_start_offset, 0, 2147483647, "start offset")) >= 0 && (var2 = this.checkRequiredInt(this.m_req_fetch_count, 0, 2147483647, "fetch count")) >= 0 && (var2 = this.checkRequiredInt(this.m_req_language, 0, 27, "language")) >= 0 && (var2 = this.checkRequiredInt(this.m_req_charset, 0, 31, "charset")) < 0) {
        }

        return var2;
    }

    private int checkRequiredString(String var1, String var2) {
        if (var1 != null && var1.length() != 0) {
            return 0;
        } else {
            this.m_msg = String.format("'%s' is required.", var2);
            return -1;
        }
    }

    private int checkRequiredInt(int var1, int var2, int var3, String var4) {
        if (var1 == -1) {
            this.m_msg = String.format("'%s' is required.", var4);
            return -1;
        } else if (var1 >= var2 && var1 <= var3) {
            return 0;
        } else {
            this.m_msg = String.format("'%s' is out of range (%d, %d~%d).", var4, var1, var2, var3);
            return -1;
        }
    }
}
