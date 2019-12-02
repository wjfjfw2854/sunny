package cn.wjf.approomorm.network;



public class NetErrorCode {
    public static final int ok = 0;
    public static final int error = -1;

    //非业务
    public static final int args_format = -10001;//请求格式错误

    //通用非业务
    public static final int http_url = -100001;
    public static final int http_network = -100002;
    public static final int http_unkonw_method = -100003;
    public static final int http_token_tickoff = -100004;//token被踢
    public static final int http_req = -100005;
    public static final int http_dataformat = -100006;
    public static final int http_dataempty = -100007;
    public static final int http_decode = -100008;
    public static final int http_token_overdue = -100009;//token失效,一般为过期
    public static final int http_no_access = -100010;//无访问权限

    public static final int business_dataformat = -200001;
    public static final int business_include_illegal_characters = -200002;//包含非法字符


    public static final int file_download_fail = -200003;
    public static final int file_upload_fail = -20004;

}
