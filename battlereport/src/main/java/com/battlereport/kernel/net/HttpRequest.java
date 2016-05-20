package com.battlereport.kernel.net;

/**
 * Created by LiuPneg on 2016/5/20.
 * 网络请求封装
 */
public class HttpRequest {
    private static final String TAG = "HttpRequest";
    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";
    private String mHttpUrl;
    private String mHttpMethod;
    private HttpParams mHttpParams;
    private String mCookie;
    private HttpHeader mHeader;

    public HttpRequest(String mHttpUrl) {
        this.mHttpUrl = mHttpUrl;
    }

    public String getHttpMethod() {
        return mHttpMethod;
    }

    public void setHttpMethod(String mHttpMethod) {
        this.mHttpMethod = mHttpMethod;
    }

    public HttpParams getHttpParams() {
        return mHttpParams;
    }

    public String getHttpParmsStr() {
        if (mHttpParams != null) {
            return mHttpParams.toString();
        }
        return "";
    }

    public void setHttpParams(HttpParams mHttpParams) {
        this.mHttpParams = mHttpParams;
    }

    public String getCookie() {
        return mCookie;
    }

    public void setCookie(String mCookie) {
        this.mCookie = mCookie;
    }

    public HttpHeader getHeader() {
        return mHeader;
    }

    public void setHeader(HttpHeader mHeader) {
        this.mHeader = mHeader;
    }

    public String getHttpUrl() {
        return mHttpUrl;
    }

    public void setHttpUrl(String mHttpUrl) {
        this.mHttpUrl = mHttpUrl;
    }
}
