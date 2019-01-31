package com.lsm.controller.utils;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @Desription
 * @auther 吕绍名
 * @create 2019-01-30-1:55 PM
 */
public class WechatJsonUtils {

    public static<T> T mapToObject(Map map,Class<? extends T> tClass){
       T t = JSON.toJavaObject((JSON) JSON.toJSON(map),tClass);
       return t;
    }


    public static<T> Map<String,Object> object2Map(T t){
        String str = JSON.toJSONString(t);
        return JSON.parseObject(str,Map.class);
    }
}
