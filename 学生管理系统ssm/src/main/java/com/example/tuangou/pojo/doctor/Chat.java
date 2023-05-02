package com.example.tuangou.pojo.doctor;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/4/13 10:06 PM
 */
//聊天表
@Data
public class Chat {

    private int id;
    private int order_id;
    private String name;
    private String text;
    private String create_time;
}
