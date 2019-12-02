package cn.wjf.approomorm.network;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;


public class HttpsUtils {


    public static boolean addSSLToBuilder(OkHttpClient.Builder builder, InputStream certificate) {
        TrustManager[] trustManagers;
        SSLSocketFactory sslSocketFactory;
        trustManagers = getTrustManagers(certificate);
        if (trustManagers != null) {
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustManagers, new SecureRandom());
                sslSocketFactory = sslContext.getSocketFactory();

                if (trustManagers.length >= 1 && (trustManagers[0] instanceof X509TrustManager)) {
                    builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustManagers[0]);
                    return true;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    /**
     * 载入证书文件
     */
    private static TrustManager[] getTrustManagers(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null) {
                        certificate.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            return trustManagerFactory.getTrustManagers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
