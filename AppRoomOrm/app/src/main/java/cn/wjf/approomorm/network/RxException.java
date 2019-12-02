package cn.wjf.approomorm.network;


public class RxException extends Exception {
    private int errcode = 0;
    private String errMsg;
    private String protocolId;

    public RxException(int errCode, String errMsg) {
        this(errCode, errMsg, "0");
    }

    public RxException(int errcode) {
        this(errcode, "error");
    }

    public RxException(int errCode, String errMsg, String protcolId) {
        super(errMsg);
        this.errcode = errCode;
        this.errMsg = errMsg;
        this.protocolId = protcolId;
    }

    public RxException(int errCode, String errMsg, int protcolId) {
        this(errCode, errMsg, protcolId + "");
    }

    public int getErrcode() {
        return errcode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getProtocolId() {
        return protocolId;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId;
    }
}
