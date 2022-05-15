package com.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("reference")
public class Reference {
    private String id;
    @TableField("refer_paper_id")
    private String referPaperId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferPaperId() {
        return referPaperId;
    }

    public void setReferPaperId(String referPaperId) {
        this.referPaperId = referPaperId;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "id='" + id + '\'' +
                ", referPaperId='" + referPaperId + '\'' +
                '}';
    }
}