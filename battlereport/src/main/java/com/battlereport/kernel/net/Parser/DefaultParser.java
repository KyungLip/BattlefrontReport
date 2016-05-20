package com.battlereport.kernel.net.Parser;

import com.battlereport.kernel.net.HttpResponse;

/**
 * Created by gumpcome on 2016/5/20.
 */
public class DefaultParser implements  IApiResultParseable<HttpResponse> {
    @Override
    public HttpResponse parse(String result) {
        return null;
    }
}
