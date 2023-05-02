package com.example.tuangou.pojo.doctor;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2022/12/10 8:25 PM
 */

@Data
public class Order implements Serializable {

    private int id;
    private String fall_time;
    private String is_see;
    private String detail;
    private int doctor_id;
    private int user_id;
    private String create_time;

    // TODO 省略了getter和setter方法
}