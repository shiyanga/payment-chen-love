package com.payment.entity.groupwormhole;

/**
 * Created by shi_y on 2017/2/22.
 */
public class TokenModel {
    //用户id
    private long userId;

    //随机生成的uuid
    private String token;

//    private User user;

    public TokenModel(long userId, String token) {
        this.userId = userId;
        this.token = token;
        //this.user = user;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
