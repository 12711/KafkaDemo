package com.lsm.controller;

import com.alibaba.fastjson.JSONArray;
import com.lsm.controller.utils.WeChatCommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Desription
 * @auther 吕绍名
 * @create 2019-01-30-5:08 PM
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/music")
    public String showTodayMusic(){
        return "Music";
    }

    @GetMapping("/toDayPalyList")
    @ResponseBody
    public JSONArray toDayPalyList(){
        return WeChatCommonUtils.getTodayPlayList();
    }

}
