package com.example.tuangou.pojo.doctor;

import lombok.Data;

import java.util.List;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/4/11 10:40 PM
 */
@Data
public class DrugOrder {

    private int order_id;
    private String name;
    private String price;
    private int is_pay;//是否支付;0:未支付 1:已支付
    private String img;
}
