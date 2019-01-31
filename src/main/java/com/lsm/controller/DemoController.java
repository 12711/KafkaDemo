package com.lsm.controller;

import com.alibaba.fastjson.JSON;
import com.lsm.controller.domain.WechatContent;
import com.lsm.controller.domain.XmlNode;
import com.lsm.controller.utils.WeChatCommonUtils;
import com.lsm.controller.utils.WechatJsonUtils;
import com.lsm.controller.utils.XmlUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

/**
 * @Desription
 * @auther roman
 * @create 2019-01-11-下午5:42
 */

@RestController
@RequestMapping(value = "/wecaht")
public class DemoController {

    @Value("${wechat.token:123456}")
    private String token;

    @GetMapping("/accessToken")
    public void test(String signature, String timestamp, String nonce, String echostr, HttpServletResponse response,HttpServletRequest request){
        System.out.println("signature : " + signature);
        System.out.println("timestamp : " + timestamp);
        System.out.println("nonce : " + nonce);
        System.out.println("echostr : " + echostr);

        System.out.println("request : " + request.getParameter("signature"));

        String[] params = new String[] { token, timestamp, nonce };
        Arrays.sort(params);
        // 将三个参数字符串拼接成一个字符串进行sha1加密
        String clearText = params[0] + params[1] + params[2];
        String algorithm = "SHA-1";
        String sign = null;
        try {
            sign = new String(Hex.encodeHex(MessageDigest.getInstance(algorithm).digest((clearText).getBytes()), true));

            System.out.println("sign is : " + sign);
            // 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
            if (signature.equals(sign)) {
                response.getWriter().print(echostr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/accessToken")
    public void testPost(HttpServletResponse response,HttpServletRequest request){
        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=UTF-8");
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            try{
                WechatContent wechatContent = XmlUtils.parseXml(request.getInputStream(), WechatContent.class);
                System.out.println("wechatContent ： " + JSON.toJSON(wechatContent));

                WechatContent wechatContentToUser = new WechatContent();
                BeanUtils.copyProperties(wechatContent,wechatContentToUser);
                wechatContentToUser.setToUserName(wechatContent.getFromUserName());
                wechatContentToUser.setFromUserName(wechatContent.getToUserName());

                String contentParamStr = null;

                if("text".equals(wechatContent.getMsgType())) {
                    XmlNode xmlNode = new XmlNode("xml",WechatJsonUtils.object2Map(wechatContentToUser));
                    contentParamStr = xmlNode.buildXmlString();
                    System.out.println("the reply content is : " + contentParamStr);
                }else if("image".equals(wechatContent.getMsgType())){
                    contentParamStr = "<xml>\n" +
                            " <ToUserName><![CDATA[%s]]></ToUserName>\n" +
                            " <FromUserName><![CDATA[%s]]></FromUserName>\n" +
                            " <CreateTime>%s</CreateTime>\n" +
                            " <MsgType><![CDATA[%s]]></MsgType>\n" +
                            " <Image>\n" +
                            " <MediaId><![CDATA[%s]]></MediaId>\n" +
                            " </Image>\n" +
                            " </xml>";
                    contentParamStr = String.format(contentParamStr,wechatContent.getFromUserName()
                            ,wechatContent.getToUserName(),new Date(),wechatContent.getMsgType(),wechatContent.getMediaId());
                }else if("event".equals(wechatContent.getMsgType())){
                    if("V1001_TODAY_MUSIC".equals(wechatContent.getEventKey())){
                        contentParamStr =
                                "<xml> " +
                                " <ToUserName><![CDATA[%s]]></ToUserName>\n" +
                                " <FromUserName><![CDATA[%s]]></FromUserName>\n" +
                                " <CreateTime>%s</CreateTime>\n" +
                                " <MsgType><![CDATA[%s]]></MsgType>\n" +
                                " <Content><![CDATA[%s]]></Content> \n" +
                                "</xml>";

                        contentParamStr = String.format(contentParamStr,wechatContent.getFromUserName(),wechatContent.getToUserName(),String.valueOf(new Date().getTime()),"text","期待中");
                    }

                }
                if(StringUtils.isBlank(contentParamStr)){
                    response.getOutputStream().write("success".getBytes());
                }else {
                    response.getOutputStream().write(contentParamStr.getBytes());
                }


            } catch (IOException e){
                e.printStackTrace();
            } finally {
                try{
                    if (null != reader){ reader.close();}
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
