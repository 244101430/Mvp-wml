package com.lantu.andorid.mvp_wml.api;

import com.lantu.andorid.mvp_wml.api.bean.NoahBean;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

import static com.lantu.andorid.mvp_wml.api.RetrofitService.AVOID_HTTP403_FORBIDDEN;

/**
 * Created by wml on 2017/12/7.
 */

public interface INoahApi {

    /**
     * @param headers   请求头信息
     * @param route     请求内容
     * @return
     */
    @Headers(AVOID_HTTP403_FORBIDDEN)
    @POST("/")
    Observable<ResponseBody> postNoah(@HeaderMap Map<String, String> headers, @Body RequestBody route);
}
