package cn.wjf.approomorm.network;


import android.text.TextUtils;


import com.google.protobuf.nano.MessageNano;

import java.io.UnsupportedEncodingException;


public class BusinessPackage<T> {
    public static final String MSG_TYPE_PB3 = "application/x-protobuf-v3";
    public static final String MSG_TYPE_JSON = "application/json";
    public static final String MSG_TYPE_MSGPACK = "application/x-msgpack";
    public static final String MSG_TYPE_FORM = "application/x-www-form-urlencoded";

    public static final int POST = 0;
    public static final int GET = 1;

    private int method = POST;
    private String protocolModule = "";
    private String protocolId;
    private byte[] msgData;
    private int retCode = -1;
    private String retMsg;
    private String msgType = MSG_TYPE_PB3;
    private String requestTag;
    private T resEntity;
    private String cacheKey = null;
    private String token;

    public static BusinessPackage getPgk(ProtocolDef protocol, MessageNano msgData, String msgType) {
        BusinessPackage businessPackage = new BusinessPackage();
        businessPackage.setProtocolId(protocol);
        businessPackage.setMsgData(msgData);
        businessPackage.setMsgType(msgType);

        return businessPackage;
    }

    public static BusinessPackage getPgk(String sProtocolId, MessageNano msgData, String msgType) {
        BusinessPackage businessPackage = new BusinessPackage();
        businessPackage.setProtocolId(sProtocolId);
        businessPackage.setMsgData(msgData);
        businessPackage.setMsgType(msgType);

        return businessPackage;
    }

    public String getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(ProtocolDef protocol) {
        if (protocol != null) {
            this.protocolId = protocol.id;
            this.protocolModule = protocol.module;
        }
    }

    public void setProtocolId(String sProtocolId) {
        this.protocolId = sProtocolId;
    }


    public String getProtocolModule() {
        return protocolModule;
    }


    public byte[] getMsgData() {
        return msgData;
    }

    public void setMsgData(MessageNano msgData) {
        this.msgData = MessageNano.toByteArray(msgData);
        setMsgType(MSG_TYPE_PB3);
    }

    public void setMsgData(byte[] msgData) {
        this.msgData = msgData;
    }

    public void setMsgData(String jsonStr) {

        try {
            this.msgData = jsonStr.getBytes("UTF-8");
            setMsgType(MSG_TYPE_JSON);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }


    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getRequestTag() {
        return requestTag;
    }

    public void setRequestTag(String requestTag) {
        this.requestTag = requestTag;
    }


    public T getResEntity() {
        return resEntity;
    }

    public void setResEntity(T resEntity) {
        this.resEntity = resEntity;
    }


    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public boolean isFromCache(){
        return !TextUtils.isEmpty(cacheKey);
    }

    public void setHeadAuth(String token)
    {
        this.token = token;
    }

    public String getHeadAuth() {
        return token;
    }
}
