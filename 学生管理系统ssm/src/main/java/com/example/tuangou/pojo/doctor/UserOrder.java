package com.example.tuangou.pojo.doctor;

import lombok.Data;
import org.apache.ibatis.annotations.Result;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/1/5 2:31 AM
 * 会员表
 */
@Data
public class UserOrder implements Serializable {
  //user
    private String phone;


    //order
    private int order_id;
    private String fall_time;
    private String is_see;
    private String detail;
    private int doctor_id;
    private int user_id;
    private String create_time;
      List<DrugOrder> drugOrderList;

    //doctor
    private String doctor_name;
    private String doctor_detail;
    private String doctor_desc_img_id;
    private String doctor_img_id;
    private List<String> doctorImgList;
    private String doctorTitImg;
}
