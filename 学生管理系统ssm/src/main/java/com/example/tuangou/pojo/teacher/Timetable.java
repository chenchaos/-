package com.example.tuangou.pojo.teacher;

import lombok.Data;

// 功课管理
@Data
public class Timetable {
    private int id;
    private String am8; // 上午8-9点
    private String am10; // 上午10-11点
    private String pm2; // 下午2-3点
    private String pm4; // 下午4-5点
    private String faculty;// 院系
    private String weekday;//周工作日(星期一至星期五的任何一天)

}
