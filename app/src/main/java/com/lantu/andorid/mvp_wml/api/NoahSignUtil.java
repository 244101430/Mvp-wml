package com.lantu.andorid.mvp_wml.api;

import com.lantu.andorid.mvp_wml.utils.MD5Util;

import java.util.HashMap;

/**
 * Created by wml on 2017/12/7.
 */

public class NoahSignUtil {

    public static HashMap<String, String> sign(String postdata) {
        String sid = "c9da1b8a00ee4d68aa579c7ca7e09224";
        String body = postdata;
        String t = Long.toString(System.currentTimeMillis() / 1000);

        String oriStr = "king-ifa@" + body + "@" + sid + "@" + t;
        String md5Str = MD5Util.md5(oriStr);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("X-KGW-SID", sid);
        map.put("X-KGW-T", t);
        map.put("X-KGW-SIGN", md5Str);
        map.put("X-KGW-AGENT", "android/1.0");
        map.put("User-Agent", "IFA/1.0(com.ifa.app.std; 999999999; ios; v9nklnmi; 999; play-webui; )");
        return map;
    }
}
