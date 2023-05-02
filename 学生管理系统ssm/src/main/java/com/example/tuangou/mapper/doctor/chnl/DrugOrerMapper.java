package com.example.tuangou.mapper.doctor.chnl;

import com.example.tuangou.pojo.doctor.DrugMsg;
import com.example.tuangou.pojo.doctor.DrugOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/4/11 10:40 PM
 */
@Mapper
public interface DrugOrerMapper {

    @Select("select ord.*,msg.img from drug_order ord left join drug_msg msg on ord.name =msg.name where ord.order_id =#{order_id}")
    @Results(id = "getAllDrug", value = {
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "price", column = "price", javaType = String.class),
            @Result(property = "img", column = "img", javaType = String.class),
    })
    //查询开的药品
    public List<DrugOrder> getDrugOrder(int order_id);

    //开药
    @Insert("insert into drug_order (order_id,name,price,is_pay) values(#{order_id},#{name},#{price},0)")
    public int insert(DrugOrder drugOrder);


    //支付药品
    @Update("update drug_order set is_pay =1 where order_id =#{order_id}")
    public void pay(int order_id);
}
