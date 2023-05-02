package com.example.tuangou.pojo.mng;

/**
 * 商品分类
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/1/5 7:10 AM
 */
public class Classify {

    private String id;
    private String name;
    private String img_id;
    private String order_num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
