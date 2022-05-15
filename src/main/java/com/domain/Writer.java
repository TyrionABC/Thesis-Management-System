package com.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("writer")
public class Writer {
    private String id;
    @TableField("writer_id")
    private Integer writerId;
    @TableField("writer_name")
    private String writerName;
    @TableField("level")
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getWriterId() {
        return writerId;
    }

    public void setWriterId(Integer writerId) {
        this.writerId = writerId;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id='" + id + '\'' +
                ", writerId=" + writerId +
                ", writerName='" + writerName + '\'' +
                ", level=" + level +
                '}';
    }
}