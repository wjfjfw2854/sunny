package cn.wjf.approomorm.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Source;


public class OkHttpUtil {
    private static OkHttpClient mOkHttpClient = null;
    private static Context appContext;
    private static String sslCer;
    private static boolean enableCheckCer = false;
    private static Cache cachePool;


    public static void doInit(Context context, String sslFileName, boolean checkCer, long cacheSizeMB) {
        enableCheckCer = checkCer;
        appContext = context;
        sslCer = sslFileName;

        if (cachePool != null && !cachePool.isClosed()){
            try {
                cachePool.close();
                cachePool.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                cachePool = null;
            }
        }
        cachePool = new Cache(appContext.getCacheDir(), cacheSizeMB * 1024 * 1024);

        getShareOkHttpClient();

    }

    public static OkHttpClient.Builder createOkHttpBuilder(int timeOutSeconds) {
        if (appContext != null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.writeTimeout(timeOutSeconds, TimeUnit.SECONDS);
            builder.connectTimeout(timeOutSeconds, TimeUnit.SECONDS);
            builder.readTimeout(timeOutSeconds, TimeUnit.SECONDS);
            if (cachePool != null) {
                builder.cache(cachePool);
            }

            return builder;
        }
        return null;
    }


    private static OkHttpClient getShareOkHttpClient() {
        if (mOkHttpClient == null) {
            if (appContext != null) {
                OkHttpClient.Builder builder = createOkHttpBuilder(10);

                if (enableCheckCer && !TextUtils.isEmpty(sslCer)) {
                    try {
                        InputStream inputStream = appContext.getAssets().open(sslCer);
                        boolean bAddSSlOk = HttpsUtils.addSSLToBuilder(builder, inputStream);

                        if (bAddSSlOk) {
                            Log.e("wjf>>>>","sky https ssl load OK");
                        } else {
                            Log.e("wjf>>>>","sky https ssl load Err");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("wjf>>>>","sky https ssl load Err:" + e.getMessage());
                    }
                }
                mOkHttpClient = builder.build();
            }
        }

        return mOkHttpClient;
    }

    private static OkHttpClient createOkHttpClient(int timeOutSeconds) {
        OkHttpClient client = null;
        if (appContext != null) {
            OkHttpClient.Builder builder = createOkHttpBuilder(timeOutSeconds);
            client = builder.build();
        }
        return client;
    }


    public static void cancelRequest(String tag) {
        if (getShareOkHttpClient() == null) {
            return;
        }
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag))
                call.cancel();
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag))
                call.cancel();
        }
    }

    /**
     * 该方法不会开启异步线程。
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return execute(request, 10);
    }

    public static Response execute(Request request, int timeOutSeconds) throws IOException {
        OkHttpClient okHttpClient = createOkHttpClient(timeOutSeconds);
        if (okHttpClient != null) {
            return okHttpClient.newCall(request).execute();
        }
        return null;
    }


    public static Response executeWithGZip(Request request) throws IOException {
        OkHttpClient.Builder builder = createOkHttpBuilder(10);
        builder.addInterceptor(new GzipRequestInterceptor());
        OkHttpClient okHttpClient = builder.build();
        if (okHttpClient != null) {
            return okHttpClient.newCall(request).execute();
        }
        return null;
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback) {
        if (getShareOkHttpClient() != null) {
            mOkHttpClient.newCall(request).enqueue(responseCallback);
        }
    }


    /**
     * 根据文件名获取文件的MIME类型
     *
     * @param fileName
     * @return
     */
    public static String getMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimeType = fileNameMap.getContentTypeFor(fileName);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        return mimeType;
    }


    public static byte[] unGZip(Source source) throws IOException {
        GzipSource gzipSource = new GzipSource(source);
        BufferedSource gzipBufferSource = Okio.buffer(gzipSource);
        return gzipBufferSource.readByteArray();
    }

}
