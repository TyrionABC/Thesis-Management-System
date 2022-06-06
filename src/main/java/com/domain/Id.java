package com.domain;

public class Id {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Id{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
