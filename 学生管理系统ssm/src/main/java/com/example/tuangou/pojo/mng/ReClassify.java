package com.example.tuangou.pojo.mng;

/**
 * 商品分类
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/1/5 7:10 AM
 */
public class ReClassify {

    private int id;
    private String name;

    private String img;
    private int order_num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getOrder_num() {
        return order_num;
    }

    public void setOrder_num(int order_num) {
        this.order_num = order_num;
    }
}
