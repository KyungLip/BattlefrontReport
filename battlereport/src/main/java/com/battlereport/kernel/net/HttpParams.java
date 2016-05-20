package com.battlereport.kernel.net;

import android.net.Uri;

/**
 * Created by LiuPeng on 2016/5/20.
 * 请求参数拼装类
 */
public class HttpParams {
    private static final String TAG = "HttpParams";
    private String mParams;

    public void addParams(String key, String value) {
        value = Uri.encode(value);//Utf-8编码
        if (mParams == null) {
            mParams = key + "=" + value;
        } else {
            mParams = "&" + key + "=" + value;
        }
    }

    @Override
    public String toString() {
        return mParams;
    }

    public boolean contains(String name) {
        return mParams != null && mParams.contains("name=");
    }
}
