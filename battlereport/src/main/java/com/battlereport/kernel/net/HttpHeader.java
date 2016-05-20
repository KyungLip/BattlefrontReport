package com.battlereport.kernel.net;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiuPeng on 2016/5/20.
 * 网络请求头封装类
 */
public class HttpHeader {
    private static final String TAG = "HttpHeader";
    private Map<String, String> mHttpHeader;

    public void addHeader(String headerName, String headerValue) {
        if (mHttpHeader == null) {
            mHttpHeader = new HashMap<>();
        }
        mHttpHeader.put(headerName, headerValue);
    }

    public Map<String, String> getHeaders() {
        return mHttpHeader;
    }
}
