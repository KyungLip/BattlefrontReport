package com.battlereport.kernel.net;

import android.text.TextUtils;

import com.battlereport.kernel.net.Constants;
import com.battlereport.kernel.net.HttpHeader;
import com.battlereport.kernel.net.HttpRequest;
import com.battlereport.kernel.net.HttpResponse;
import com.battlereport.kernel.net.Parser.IApiResultParseable;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by LiuPeng on 2016/5/20.
 * 网络请求任务类
 */
public class NetworkTask<T> {
    private static final String TAG = "NetworkTask";

    /**
     * 发送网络请求并解析
     *
     * @param httpRequest 网络请求对象
     * @param parser      解析器对象
     * @return T 解析结果对象
     */

    public T send(HttpRequest httpRequest, IApiResultParseable<T> parser) throws IOException {
        if (httpRequest == null) {
            return null;
        }
        HttpURLConnection httpURLConnection = buildHttpUrlConnection(httpRequest);
        if (httpURLConnection == null) {
            throw new IOException("HttpURLConnection is null");
        }
        int statusCode = httpURLConnection.getResponseCode();
        if (statusCode != HttpURLConnection.HTTP_OK) {

        }
        HttpResponse httpResponse = new HttpResponse(httpURLConnection);
        String content = httpResponse.getContent();
        T result = null;
        if (parser != null) {
            parser.parse(content);
        } else {
            httpResponse.closeConnection();
        }
        return result;
    }

    private HttpURLConnection buildHttpUrlConnection(HttpRequest httpRequest) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(httpRequest.getHttpUrl());
            urlConnection = (HttpURLConnection) url.openConnection();
            registerHttps();
            urlConnection.setRequestMethod(httpRequest.getHttpMethod());//设置请求方法
            urlConnection.setConnectTimeout(Constants.CONNECTION_TIME_OUT);//设置连接超时时间
            urlConnection.setReadTimeout(Constants.READ_TIME_OUT);//设置读取超时时间
            urlConnection.setRequestProperty("Accept-Encoding", Constants.ENCONDING_GZIP);//设置支持的压缩类型
            urlConnection.setRequestProperty("Content-Type", Constants.CONTENTTYPE_APPXWFURLENCODED);//表单的数据类型,说明会使用Url编码
            //判断是否要添加请求头
            HttpHeader httpHeader = httpRequest.getHeader();
            if (httpHeader != null && httpHeader.getHeaders() != null && httpHeader.getHeaders().size() > 0) {
                Set<Map.Entry<String, String>> headerSet = httpHeader.getHeaders().entrySet();
                for (Map.Entry<String, String> me : headerSet) {
                    urlConnection.setRequestProperty(me.getKey(), me.getValue());
                }
            }
            String httpParms = httpRequest.getHttpParmsStr();
            if (!TextUtils.isEmpty(httpParms)) {
                urlConnection.setDoOutput(true);
                urlConnection.getOutputStream().write(httpParms.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlConnection;
    }

    /**
     * 添加https支持
     *
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws UnrecoverableKeyException
     * @throws KeyStoreException
     */
    private void registerHttps()
            throws NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException, KeyStoreException {
        final X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String authType) throws CertificateException {
                if (xcs == null || xcs.length <= 0 || TextUtils.isEmpty(authType)) {
                    throw new CertificateException("not trusted");
                }
                for (X509Certificate xc : xcs) {
                    xc.checkValidity();
                }
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        HostnameVerifier hnv = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        final SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, new TrustManager[]{tm}, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(hnv);
    }
}
