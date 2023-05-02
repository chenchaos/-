package com.example.tuangou.pojo.chnl;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/1/5 2:31 AM
 * 商品表
 */
public class ReMerchandise implements Serializable {
    private int id;
    //商品名称
    private String name;
    //商品分类id
    private int type_id;
    //商品分类名称
    private String type_name;
    //成团数
    private int group_num;
    //价格
    private double price;
    //图片id 多个,分割
    private String img_id;
    //推荐星级
    private int recommend_num;
    //商品详情图
    private int details_img_id;

    private List<String> img;

    private String dimg;


    private String details;


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    private String create_time;


    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public String getDimg() {
        return dimg;
    }

    public void setDimg(String dimg) {
        this.dimg = dimg;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getDetails_img_id() {
        return details_img_id;
    }

    public void setDetails_img_id(int details_img_id) {
        this.details_img_id = details_img_id;
    }

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

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getGroup_num() {
        return group_num;
    }

    public void setGroup_num(int group_num) {
        this.group_num = group_num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public int getRecommend_num() {
        return recommend_num;
    }

    public void setRecommend_num(int recommend_num) {
        this.recommend_num = recommend_num;
    }
}
