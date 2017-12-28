package com.lantu.andorid.mvp_wml.api;

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

public interface ILantuApi {
    /**
     * @param route     请求内容
     * @return
     */
    @Headers(AVOID_HTTP403_FORBIDDEN)
    @POST("/getPatient.php")
    Observable<ResponseBody> postLantu(@Body RequestBody route);
}
