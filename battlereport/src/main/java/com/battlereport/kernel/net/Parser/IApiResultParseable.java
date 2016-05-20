package com.battlereport.kernel.net.Parser;

/**
 * Created by LiuPeng on 2016/5/20.
 */
public interface IApiResultParseable<T> {
    T parse(String result);
}
