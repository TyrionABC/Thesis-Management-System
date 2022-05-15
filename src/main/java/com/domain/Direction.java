package com.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("direction")
public class Direction {
    @TableId
    @TableField("direction_name")
    private String directionName;
    @TableField("parent_direction_name")
    private String parentDirectionName;

    private Integer level;

    private String path;

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public String getParentDirectionName() {
        return parentDirectionName;
    }

    public void setParentDirectionName(String parentDirectionName) {
        this.parentDirectionName = parentDirectionName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "directionName='" + directionName + '\'' +
                ", parentDirectionName='" + parentDirectionName + '\'' +
                ", level=" + level +
                ", path='" + path + '\'' +
                '}';
    }
}