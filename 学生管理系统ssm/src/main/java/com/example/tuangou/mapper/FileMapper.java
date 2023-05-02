package com.example.tuangou.mapper;

import com.example.tuangou.pojo.mng.Img;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;


/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2022/12/10 8:24 PM
 */


@Component
@Mapper
//此处的@Mapper注解可以放在启动类上这样不用每个Mapper接口上都加
public interface FileMapper {

    //上传
    @Insert("insert into img(img,create_time) values(#{img},now())")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addComment(Img img);


}
