package com.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
@TableName("paper_basic_info")
public class Paper_Basic_info {
    @TableId
    private String id;

    private String title;
    @TableField("thesis_type")
    private String thesisType;
    @TableField("thesis_date")
    private Date thesisDate;
    @TableField("literature_link")
    private String literatureLink;
    @TableField("publisher_id")
    private String publisherId;
    @TableField("likes")
    private Integer like;
    @TableField("flag")
    private Integer flag;
    @TableField("text")
    private String text;

    @Override
    public String toString() {
        return "Paper_Basic_info{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", thesisType='" + thesisType + '\'' +
                ", thesisDate=" + thesisDate +
                ", literatureLink='" + literatureLink + '\'' +
                ", publisherId='" + publisherId + '\'' +
                ", text='" + text + '\'' +
                ", flag=" + flag +
                ", like=" + like +
                '}';
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setThesisDate(Date thesisDate) {
        this.thesisDate = thesisDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThesisType() {
        return thesisType;
    }

    public void setThesisType(String thesisType) {
        this.thesisType = thesisType;
    }

    public String getLiteratureLink() {
        return literatureLink;
    }

    public void setLiteratureLink(String literatureLink) {
        this.literatureLink = literatureLink;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public Date getThesisDate() {
        return thesisDate;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

}