package com.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
@TableName("paper_publish")
public class Paper_publish {
    private String id;
    @TableField("publish_meeting")
    private String publishMeeting;
    @TableField("publish_time")
    private Date publishTime;
    @TableField("publisher_id")
    private String publisherId;

    private String publisher;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublishMeeting() {
        return publishMeeting;
    }

    public void setPublishMeeting(String publishMeeting) {
        this.publishMeeting = publishMeeting;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Paper_publish{" +
                "id='" + id + '\'' +
                ", publishMeeting='" + publishMeeting + '\'' +
                ", publishTime=" + publishTime +
                ", publisherId='" + publisherId + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }

    public Paper_publish() {
    }

    public Paper_publish(String id, String publishMeeting, Date publishTime, String publisherId, String publisher) {
        this.id = id;
        this.publishMeeting = publishMeeting;
        this.publishTime = publishTime;
        this.publisherId = publisherId;
        this.publisher = publisher;
    }
}