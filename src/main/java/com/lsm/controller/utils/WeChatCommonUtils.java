package com.lsm.controller.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hscf.common.http.EasyHttpBuilder;
import com.hscf.common.http.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Desription
 * @auther 吕绍名
 * @create 2019-01-30-2:54 PM
 */
public class WeChatCommonUtils {
    private static String accessToken;
    private static String url;
    static {
        url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
        url = String.format(url,"wx72d014bef82679f1","0347a4500b154ac5facc3a805293cd56");
    }
    /**
     * 获取Wechat Token
     */
    public static  String  getAccessToken(){

        if(StringUtils.isBlank(accessToken)) {
           Response response = EasyHttpBuilder.build().contentTypeWithFormUrlEncoded().post(url);
           if(response.is200()){
               return response.json().getString("access_token");
           }else {
               throw new RuntimeException("get access_token error : ");
           }
        }
        return accessToken;
    }

    public static  String  refreshAccessToken(){
        accessToken = HttpClientUtils.sendPost(url,null, new Function() {
            @Override
            public Object apply(Object o) {
                return null;
            }
        }, new Consumer() {
            @Override
            public void accept(Object o) {

            }
        });

        return accessToken;
    }


    public static JSONArray getTodayPlayList(){
        String url = "http://music.163.com/api/search/get/web?csrf_token=hlpretag=&hlposttag=&s=l&type=1000&offset=0&total=true&limit=10";
        Response response = EasyHttpBuilder.build().head("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36").get(url);
        return response.json().getJSONObject("result").getJSONArray("playlists");
    }

}
