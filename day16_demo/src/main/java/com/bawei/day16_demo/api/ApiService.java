package com.bawei.day16_demo.api;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiService {
    @GET
    Observable<ResponseBody> getInfoNoParams(@Url String url);

    @GET
    Observable<ResponseBody> getInfoParams(@Url String url, @QueryMap Map<String, Object> map);

    @POST
    Observable<ResponseBody> postInfoParams(@Url String url, @QueryMap Map<String, Object> map);


}
