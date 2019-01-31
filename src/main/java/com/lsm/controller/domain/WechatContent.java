package com.lsm.controller.domain;

import java.util.Date;

/**
 * @Desription
 * @auther 吕绍名
 * @create 2019-01-30-1:49 PM
 */

public class WechatContent {
    private String toUserName;
    private String fromUserName;
    private Date createTime;
    private String msgType;
    private String content;
    private String msgId;
    private String picUrl;
    private String mediaId;
    private String format;
    private String thumbMediaId;
    private String location_X;
    private String location_Y;
    private String scale;
    private String label;
    private String eventKey;

    public WechatContent setEventKey(String eventKey) {
        this.eventKey = eventKey;
        return this;
    }

    public String getEventKey() {
        return eventKey;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public String getFormat() {
        return format;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public String getLocation_X() {
        return location_X;
    }

    public String getLocation_Y() {
        return location_Y;
    }

    public String getScale() {
        return scale;
    }

    public String getLabel() {
        return label;
    }

    public WechatContent setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public WechatContent setMediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public WechatContent setFormat(String format) {
        this.format = format;
        return this;
    }

    public WechatContent setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
        return this;
    }

    public WechatContent setLocation_X(String location_X) {
        this.location_X = location_X;
        return this;
    }

    public WechatContent setLocation_Y(String location_Y) {
        this.location_Y = location_Y;
        return this;
    }

    public WechatContent setScale(String scale) {
        this.scale = scale;
        return this;
    }

    public WechatContent setLabel(String label) {
        this.label = label;
        return this;
    }

    public WechatContent setToUserName(String toUserName) {
        this.toUserName = toUserName;
        return this;
    }

    public WechatContent setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
        return this;
    }

    public WechatContent setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public WechatContent setMsgType(String msgType) {
        this.msgType = msgType;
        return this;
    }

    public WechatContent setContent(String content) {
        this.content = content;
        return this;
    }

    public WechatContent setMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public String getToUserName() {
        return toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public String getContent() {
        return content;
    }

    public String getMsgId() {
        return msgId;
    }
}
