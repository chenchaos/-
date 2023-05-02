package com.example.tuangou.pojo;

import java.io.Serializable;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2022/12/10 8:25 PM
 */
public class Comment implements Serializable {

    private String tiebaid;
    private String useriid;
    private String create_time;
    private String content;
    private String username;
    private String userimg;

    public String getTiebaid() {
        return tiebaid;
    }

    public void setTiebaid(String tiebaid) {
        this.tiebaid = tiebaid;
    }

    public String getUseriid() {
        return useriid;
    }

    public void setUseriid(String useriid) {
        this.useriid = useriid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    // TODO 省略了getter和setter方法
}