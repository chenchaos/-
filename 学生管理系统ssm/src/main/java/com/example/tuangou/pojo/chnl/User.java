package com.example.tuangou.pojo.chnl;

import java.io.Serializable;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/1/5 2:31 AM
 * 会员表
 */
public class User implements Serializable {
    private int id;

    private String phone;

    private String pass_work;

    private int img_id;

    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass_work() {
        return pass_work;
    }

    public void setPass_work(String pass_work) {
        this.pass_work = pass_work;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }
}
