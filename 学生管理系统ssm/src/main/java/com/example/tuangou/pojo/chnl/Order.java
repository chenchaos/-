package com.example.tuangou.pojo.chnl;

import java.io.Serializable;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/1/5 2:31 AM
 * 订单表
 */
public class Order implements Serializable {
    private int id;

    /**
     * 用户id
     */
    private String user_phone;

    /**
     * 商品id
     */
    private int mcd_id;

    /**
     * 状态 0：拼团中  1：拼团成功
     */
    private int status;

    /**
     * 创建时间
     */
    private String create_time;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public int getMcd_id() {
        return mcd_id;
    }

    public void setMcd_id(int mcd_id) {
        this.mcd_id = mcd_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
