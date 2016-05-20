package com.battlereport.kernel.net;

/**
 * Created by LiuPeng on 2016/5/20.
 * 完成Http请求的构建
 */
public abstract class BaseApi {
    private static final String TAG = "BaseApi";


    protected HttpRequest buildGetRequest(String url, HttpParams httpParams, boolean isBasicParams, HttpHeader httpHeader) {
        url = buildHttpUrl(url, httpParams, isBasicParams);
        HttpRequest httpRequest = new HttpRequest(url);
        httpRequest.setHttpMethod(HttpRequest.HTTP_GET);
        if (httpHeader != null)
            httpRequest.setHeader(httpHeader);
        return httpRequest;
    }

    protected HttpRequest buildPostRequest(String url, HttpParams httpParams, boolean isBasicParams, HttpHeader httpHeader) {
        url = buildHttpUrl(url, null, isBasicParams);
        HttpRequest httpRequest = new HttpRequest(url);
        httpRequest.setHttpMethod(HttpRequest.HTTP_POST);
        if (httpParams != null)
            httpRequest.setHttpParams(httpParams);

        if (httpHeader != null)
            httpRequest.setHeader(httpHeader);
        return httpRequest;
    }

    public abstract String buildHttpUrl(String httpUrl, HttpParams httpParams, boolean isBasicParams);
}
