package cn.wjf.approomorm.network;

public class HttpDataPackage {
    public static final String HEADER_PROTOCOL_ID = "X-Protocol-Id";
    public static final String HEADER_REQUEST_ID = "X-Request-Id";
    public static final String HEADER_CUSTOM_AGENT = "X-Android-Agent";
    public static final String HEADER_AUTHORIZATION = "Authorization";


    public static final int RET_OK = 200;
    public static final int RET_OK_CACHE = 290;
    public static final int RET_SERVER_ERR = 500;
    public static final int RET_CLIENT_ERR = 400;
    public static final int RET_TOKEN_OVERDUE = 401;
    public static final int RET_TOKEN_KICK_OFF = 403;
    public static final int RET_NO_ACCESS = 406;

    private RequestRet errCode = new RequestRet();

    private byte[] body;
    private int httpcode = RET_OK;
    private String httpMsg;
    private String protocolId;
    private String requestTag = "";
    private String contentType;

    private String extraTag;

    public HttpDataPackage() {

    }

    public HttpDataPackage(byte[] bsBody, int httpcode, String httpMsg, String protocolId, String contentType, String requestTag) {
        this.body = bsBody;
        this.httpcode = httpcode;
        this.httpMsg = httpMsg;
        this.protocolId = protocolId;
        this.requestTag = requestTag;
        this.contentType = contentType;
    }

    public byte[] getBody() {
        return body;
    }


    public void setBody(byte[] body) {
        this.body = body;
    }


    public String getHttpMsg() {
        return httpMsg;
    }

    public void setHttpMsg(String httpMsg) {
        this.httpMsg = httpMsg;
    }

    public int getHttpcode() {
        return httpcode;
    }

    public void setHttpcode(int httpcode) {
        this.httpcode = httpcode;
    }

    public String getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId;
    }

    public void setProtocolId(int protocolId) {
        this.protocolId = protocolId + "";
    }

    public String getRequestTag() {
        return requestTag;
    }

    public void setRequestTag(String requestTag) {
        this.requestTag = requestTag;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getExtraTag() {
        return extraTag;
    }

    public void setExtraTag(String extraTag) {
        this.extraTag = extraTag;
    }


    public RequestRet getErrCode() {
        return errCode;
    }

    public void setErrCode(RequestRet errCode) {
        this.errCode = errCode;
    }
}
