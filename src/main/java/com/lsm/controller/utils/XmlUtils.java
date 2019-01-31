package com.lsm.controller.utils;

import com.lsm.controller.domain.WechatContent;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Desription
 * @auther 吕绍名
 * @create 2019-01-30-10:51 AM
 */
public class XmlUtils {

    public static<T> T parseXml(InputStream inputStream,Class<? extends T> tClass){
        // 解析books.xml文件
        // 创建SAXReader的对象reader
        SAXReader reader = new SAXReader();
        T wechatContent = null;
        try {
            // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
            Document document = reader.read(inputStream);
            // 通过document对象获取根节点bookstore
            Element contentRoot = document.getRootElement();

            List<Element> nodeContentList = contentRoot.elements();
            if(!CollectionUtils.isEmpty(nodeContentList)){
                Map<String,List<Element>> nodeMap = nodeContentList.stream().collect(Collectors.groupingBy(Element::getName));

                Map<String,String> finalNodeMap = new HashMap<>();
                for(Map.Entry<String,List<Element>> it : nodeMap.entrySet() ){
                    finalNodeMap.put(it.getKey(),it.getValue().get(0).getText());
                }
                wechatContent = WechatJsonUtils.<T>mapToObject(finalNodeMap, tClass);


            }
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return wechatContent;
    }



}
