package com.bw.gouwu.api;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * description: lvxinxin
 * author: 吕新新
 * date: 2019/12/5 14:28
 * update: $date$
 */
public interface ApiService {
    @GET
    Observable<ResponseBody> getInfoNoParams(@Url String url);

    @GET
    Observable<ResponseBody> getInfoParams(@Url String url, @QueryMap Map<String, Object> map);

    @GET
    Observable<ResponseBody> getHeadrInfo(@Url String url, @HeaderMap Map<String, Object> map);

}
