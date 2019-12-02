package cn.wjf.approomorm.network;


public interface HttpCodeProcessInterface {
    void processOK(int code, String protocolId);

    RxException processErr(int code, String protocolId);
}
