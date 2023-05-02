package com.example.tuangou.pojo.doctor;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2022/12/10 8:25 PM
 */

@Data
public class Doctor implements Serializable {

    private int id;
    private String name;
    private String detail;
    private String img_id;
    private String desc_img_id;

    private String tit_img;

    private String password;
    private String desc_img;
    private List<String> img;



    // TODO 省略了getter和setter方法
}