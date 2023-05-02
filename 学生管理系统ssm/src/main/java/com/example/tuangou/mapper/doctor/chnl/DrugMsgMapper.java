package com.example.tuangou.mapper.doctor.chnl;

import com.example.tuangou.pojo.doctor.DrugMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/4/11 10:40 PM
 */
@Mapper
public interface DrugMsgMapper {

    @Select("select * from drug_msg")
    @Results(id = "getAllDrug", value = {
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "price", column = "price", javaType = Double.class),
    })
    public List<DrugMsg> getAllDrug();
}
