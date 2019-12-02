package cn.wjf.approomorm.network;


public class RequestRet {
    public RequestRet(int retCode, String retMsg, String protocolId, Object resData) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.protocolId = protocolId;
        this.resData = resData;
    }

    public RequestRet() {
    }
    public RequestRet(int retCode){
        this.retCode = retCode;
    }

    public int retCode = 0;
    public String retMsg;
    public String protocolId;
    public Object resData;
}
