package com.example.tuangou.pojo.teacher;

import lombok.Data;

import java.util.List;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/5/2 2:59 PM
 */
@Data
public class Student {
    private String name;
    private int sid;
    private String sex;
    private String sno;
    private String faculty;
    private String img_id;
    private String passwork;
    // base64
    private String img;
    private String teacher_id;
}
