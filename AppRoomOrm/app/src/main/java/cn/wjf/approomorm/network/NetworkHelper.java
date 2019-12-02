package cn.wjf.approomorm.network;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import cn.wjf.approomorm.system.YMProduct;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;

import static cn.wjf.approomorm.network.HttpDataPackage.HEADER_AUTHORIZATION;
import static cn.wjf.approomorm.network.HttpDataPackage.HEADER_PROTOCOL_ID;
import static cn.wjf.approomorm.network.HttpDataPackage.HEADER_REQUEST_ID;

public class NetworkHelper {

    public static final String token = "4D4194BF0522D426A5FDC302D7048EB6";
    public static final String DEFAULT_BS_HOST = "http://ds.m.emoney.cn/";
    public static String bsHost = DEFAULT_BS_HOST;
    private static String appVersion;
    private static String appName;
    private static HttpCodeProcessInterface httpCodeProcessPlug = null;

    public static void init(String appName,String appVersion){
        NetworkHelper.appName = appName;
        NetworkHelper.appVersion = appVersion;
    }

    public static void setHttpCodeProcessPlug(HttpCodeProcessInterface plug) {
        httpCodeProcessPlug = plug;
    }

    public static Observable<BusinessPackage> requestBusiness(@NonNull final BusinessPackage reqData, final String httpTag) {
        String dstUrl = bsHost + reqData.getProtocolModule();
        //请求头构造
        Headers.Builder httpHeaders = new Headers.Builder();

        try {
            String protocolid = URLEncoder.encode(reqData.getProtocolId(), "UTF-8");
            httpHeaders.add(HEADER_PROTOCOL_ID, protocolid);
        } catch (UnsupportedEncodingException e) {
            httpHeaders.add(HEADER_PROTOCOL_ID, reqData.getProtocolId());
            e.printStackTrace();
        }
        httpHeaders.add(HEADER_REQUEST_ID, String.valueOf(reqData.getRequestTag()));
        if(!TextUtils.isEmpty(token)) {
            httpHeaders.add(HEADER_AUTHORIZATION, token);
        }
        httpHeaders.add("X-Product-Id", YMProduct.PRODUCT_ID);
        httpHeaders.add("X-Android-Agent:",String.format("%s/%s",appName,appVersion));
        return doRequest(dstUrl, httpHeaders.build(), reqData, httpTag);
    }

    public static Observable<BusinessPackage> doRequest(@NonNull String dstUrl, @NonNull Headers httpHeaders, @NonNull final BusinessPackage reqData, final String httpTag) {
        //参数check
        if (TextUtils.isEmpty(dstUrl) || reqData == null) {
            Log.e("wjf>>>>", "[ERR] doRequest -> protocolid:" + reqData.getProtocolId() + " Invalid URL :" + dstUrl);
            return Observable.error(new RxException(NetErrorCode.args_format, "Err:Arg format error"));
        }


        return requestBinaDataWithHeader(dstUrl, reqData.getMethod(), reqData.getMsgData(), reqData.getMsgType(), httpHeaders, httpTag, reqData.getCacheKey())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (httpCodeProcessPlug != null && throwable != null && throwable instanceof RxException) {
                            RxException tException = (RxException) throwable;
                            httpCodeProcessPlug.processErr(tException.getErrcode(), tException.getProtocolId());
                        }
                    }
                })
                .flatMap(new Function<HttpDataPackage, Observable<BusinessPackage>>(){
                            @Override
                            public Observable<BusinessPackage> apply(HttpDataPackage httpDataPackage) throws Exception {
                            String tProtocolId = httpDataPackage.getProtocolId();
                        if (TextUtils.isEmpty(tProtocolId)) {
                            Log.e("wjf>>>>", "[WARNING] -> protocolid:" + tProtocolId + " ,ExTag:" + httpDataPackage.getExtraTag());
                        } else {
                            try {
                                tProtocolId = URLDecoder.decode(tProtocolId, "UTF-8");
                            } catch (Exception e) {
                                Log.e("wjf>>>>", "[WARNING] -> protocolid:" + tProtocolId + " ,ExTag:" + httpDataPackage.getExtraTag());

                                e.printStackTrace();
                            }
                        }


                        if (httpDataPackage.getHttpcode() == HttpDataPackage.RET_OK || httpDataPackage.getHttpcode() == HttpDataPackage.RET_OK_CACHE) {

                            if (httpCodeProcessPlug != null) {
                                httpCodeProcessPlug.processOK(httpDataPackage.getHttpcode(), tProtocolId);
                            }

                            //抽象业务pkg组包
                            BusinessPackage businessPackage = new BusinessPackage();
                            businessPackage.setProtocolId(tProtocolId);
                            businessPackage.setRetCode(NetErrorCode.ok);
                            businessPackage.setMsgData(httpDataPackage.getBody());
                            businessPackage.setMsgType(httpDataPackage.getContentType());
                            businessPackage.setRequestTag(httpDataPackage.getRequestTag());
                            if (httpDataPackage.getHttpcode() == HttpDataPackage.RET_OK_CACHE) {
                                businessPackage.setCacheKey(reqData.getCacheKey());
                            }

                            return Observable.just(businessPackage);
                        } else {

                            RxException rxException = new RxException(NetErrorCode.http_req, httpDataPackage.getHttpcode() + "", tProtocolId);

                            if (httpCodeProcessPlug != null) {
                                String tPID = tProtocolId;
                                if (TextUtils.isEmpty(tPID)) {
                                    tPID = httpDataPackage.getExtraTag();
                                }
                                rxException = httpCodeProcessPlug.processErr(httpDataPackage.getHttpcode(), tPID);

                            }
                            return Observable.error(rxException);
                        }
                    }

                });
    }
    
    private static Observable<HttpDataPackage> requestBinaDataWithHeader(final String reqUrl, final int method, final byte[] bytesReqBody, final String contentType, final Headers headers, final String tag, final String cacheKey) {
        ObservableOnSubscribe observableOnSubscribe = new ObservableOnSubscribe() {
            @Override
            public void subscribe(final ObservableEmitter subscriber) throws Exception {
                if (TextUtils.isEmpty(reqUrl)) {
                    Log.e("wjf>>>>", "[ERR] http req arg err URL:" + reqUrl + "BODY:" + bytesReqBody);
                    subscriber.onError(new RxException(NetErrorCode.http_url, "invalid _url"));
                    return;
                }
                Request.Builder reqBuilder = new Request.Builder();
                reqBuilder.url(reqUrl);
                if (tag != null) {
                    reqBuilder.tag(tag);
                }


                if (headers != null) {
                    reqBuilder.headers(headers);
                }

                if (method == BusinessPackage.POST) {
                    RequestBody reqBody = RequestBody.create(MediaType.parse(contentType), bytesReqBody != null ? bytesReqBody : new byte[0]);
                    reqBuilder.post(reqBody);
                } else {
                    reqBuilder.get();
                }

                OkHttpUtil.enqueue(reqBuilder.build(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        String sProtocolid = "";
                        try {
                            sProtocolid = URLDecoder.decode(headers.get(HEADER_PROTOCOL_ID), "UTF-8");
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                        Log.e("wjf>>>>", "[ERR] httpErr(NetWork) -> protocoid:" + sProtocolid + ", err:" + e.getMessage());

                        subscriber.onError(new RxException(NetErrorCode.http_network, e.getMessage(), sProtocolid));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String sProtocolid = "";
                        try {
                            sProtocolid = URLDecoder.decode(headers.get(HEADER_PROTOCOL_ID), "UTF-8");
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }


                        try {
                            byte[] resData = inputStream2Bytes(response.body().source());
                            response.body().close();
                            HttpDataPackage httpPkg = new HttpDataPackage(resData, response.code(), response.message(), response.headers().get(HEADER_PROTOCOL_ID), contentType, response.headers().get(HEADER_REQUEST_ID));
                            httpPkg.setExtraTag(sProtocolid);
                            //正常结束
                            subscriber.onNext(httpPkg);
                            subscriber.onComplete();
                        } catch (Exception e) {
                            Log.e("wjf>>>>", "[ERR] httpErr(Response) -> protocoid:" + sProtocolid + ", err:" + e.getMessage());

                            //异常
                            subscriber.onError(new RxException(NetErrorCode.http_req, e.getMessage(), sProtocolid));
                        }
                    }

                });
            }
        };
        return Observable.create(observableOnSubscribe)
                .subscribeOn(Schedulers.io());
    }

    private static byte[] inputStream2Bytes(BufferedSource bufferedSource) throws IOException {
        if (bufferedSource == null) {
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Sink sink = Okio.sink(outputStream);
        BufferedSink bufferedSink = Okio.buffer(sink);
        Buffer sinkBuffer = bufferedSink.buffer();

        int readSize = 200 * 1024; //200kb
        while (bufferedSource.read(sinkBuffer, readSize) != -1) {
            bufferedSink.emit();
        }
        bufferedSink.flush();
        bufferedSource.close();
        bufferedSink.close();

        return outputStream.toByteArray();


    }
}
