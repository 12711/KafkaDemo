package com.lsm.controller.domain;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desription
 * @auther 吕绍名
 * @create 2019-01-30-2:10 PM
 */
public class XmlNode {
    private String rootNode;
    private Map<String,Object> nodeMaps;

    public XmlNode(){}

    public XmlNode(String rootNode,Map<String,Object> nodeMaps){
        this.rootNode = rootNode;
        this.nodeMaps = nodeMaps;
    }

    public String warpNode(String nodeName,String type){
        if("prefix".equals(type)){
            return "<" + nodeName + ">";
        }else {
            return "</" + nodeName + ">";
        }
    }


    public String buildXmlString(){
        if(CollectionUtils.isEmpty(this.nodeMaps)){
            return "";
        }
        StringBuilder xmlSb = new StringBuilder();
        xmlSb.append(warpNode(rootNode,"prefix"));
        for(Map.Entry<String,Object> it : nodeMaps.entrySet()){
            String upperKey = (it.getKey().charAt(0) + "").toUpperCase() + it.getKey().substring(1);
            xmlSb.append(warpNode(upperKey,"prefix"));
            xmlSb.append("<![CDATA[" + it.getValue() + "]]>");
            xmlSb.append(warpNode(upperKey,"subfix"));
        }

        xmlSb.append(warpNode(rootNode,"subfix"));
        return xmlSb.toString();
    }

    public static void main(String[] args) {
        Map<String,Object> param = new HashMap<>();
        param.put("roman","1");
        param.put("pp","2");

        XmlNode xmlNode = new XmlNode("Xml",param);
        String str = xmlNode.buildXmlString();
        System.out.println(str);
    }
}
