package com.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("belong")
public class Belong {
    @TableField("direction_name")
    private String directionName;

    private String id;

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Belong{" +
                "directionName='" + directionName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}