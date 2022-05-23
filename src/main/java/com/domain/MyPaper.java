package com.domain;

public class MyPaper {
    private int likes;
    private String direction;
    private int num;

    @Override
    public String toString() {
        return "MyPaper{" +
                "likes=" + likes +
                ", direction='" + direction + '\'' +
                ", num=" + num +
                '}';
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
