package com.example.tuangou.pojo.mng;

import java.io.Serializable;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2022/12/10 8:25 PM
 */
public class AdminUser implements Serializable {

    private String user_name;
    private String passwork;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPasswork() {
        return passwork;
    }

    public void setPasswork(String passwork) {
        this.passwork = passwork;
    }


    // TODO 省略了getter和setter方法
}