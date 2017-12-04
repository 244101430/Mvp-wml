package com.lantu.andorid.mvp_wml.api;

import com.lantu.andorid.mvp_wml.api.bean.NewsInfo;
import com.lantu.andorid.mvp_wml.api.bean.NoahBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

import static com.lantu.andorid.mvp_wml.api.RetrofitService.CACHE_CONTROL_NETWORK;


/**
 * Created by wml8743 on 2017/9/1.
 */

public interface ITestApi {

    @POST("getPatient.php")
    Observable<NoahBean> postNoah(@Body RequestBody route);

    /**
     * 获取新闻列表
     * eg: http://c.m.163.com/nc/article/headline/T1348647909107/60-20.html
     *      http://c.m.163.com/nc/article/list/T1348647909107/60-20.html
     *
     * @param type 新闻类型
     * @param id 新闻ID
     * @param startPage 起始页码
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsInfo>>> getNewsList(@Path("type") String type, @Path("id") String id,
                                                        @Path("startPage") int startPage);

}
