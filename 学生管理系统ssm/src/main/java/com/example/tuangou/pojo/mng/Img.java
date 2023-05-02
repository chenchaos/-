package com.example.tuangou.pojo.mng;

import java.io.Serializable;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/1/5 2:31 AM
 */
public class Img  implements Serializable {
    private int id;
    private String img;

    private String create_time;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
