package com.scut.backend.model;

import java.io.Serializable;

public class TokenSession implements Serializable {
    private String token;
    private Integer uid;

    public TokenSession(String token, Integer uid) {
        this.token = token;
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
