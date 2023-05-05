package com.example.tuangou.pojo.teacher;

import lombok.Data;

import java.util.Date;

//请假单
@Data
public class AskLeave {
    private int id;
    private int sid;   // 学生id
    private String leave_reason; //请假原因
    private Date create_time;  // 提单时间
    private Date start_time;  //请假开始时间
    private Date end_time;   // 请假结束时间

    private String name;   // 学生姓名
    private String faculty;   // 院系
}
