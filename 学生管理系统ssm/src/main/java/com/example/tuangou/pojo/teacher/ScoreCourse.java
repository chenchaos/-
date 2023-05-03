package com.example.tuangou.pojo.teacher;

import lombok.Data;

import java.util.Date;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/5/2 2:59 PM
 *  课程评分对象
 */
@Data
public class ScoreCourse {
    private int id;

    private int kid;
    private Date create_time;
    private Date end_time;
    private String name;
    private String tid;
    private String weeks;
    private String faculty;
    // 学生id, 学生分数
    private int sid;
    private String score;

}
