package com.example.tuangou.pojo.teacher;

import lombok.Data;

import java.util.List;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/5/2 1:14 PM
 */
@Data
public class Teacher {

    private int tid;
    private String faculty;
    private String education;
    private String name;
    private String tno;
    private String post;
    private String passwork;
    private String img_id;
    private String desc_img_id;
    private String desc;

    private String desc_img;
    private List<String> img;
}
