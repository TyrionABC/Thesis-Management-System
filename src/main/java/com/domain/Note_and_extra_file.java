package com.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("note_and_extra_file")
public class Note_and_extra_file {
    private String id;
    @TableField("publisher_id")
    private String publisherId;

    private String overview;

    private String note;
    @TableField("extra_file")
    private byte[] extraFile;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getExtraFile() {
        return extraFile;
    }

    public void setExtraFile(byte[] extraFile) {
        this.extraFile = extraFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }
}