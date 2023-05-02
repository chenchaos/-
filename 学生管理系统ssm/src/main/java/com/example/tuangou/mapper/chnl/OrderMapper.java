package com.example.tuangou.mapper.chnl;

import com.example.tuangou.pojo.chnl.Order;
import com.example.tuangou.pojo.chnl.ReOrder;
import com.example.tuangou.pojo.chnl.User;
import com.example.tuangou.pojo.mng.Img;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2022/12/10 8:24 PM
 */


@Component
@Mapper
//此处的@Mapper注解可以放在启动类上这样不用每个Mapper接口上都加
public interface OrderMapper {

    //生成订单
    @Insert("insert into pm_order(user_phone,mcd_id,status,create_time,adress) values(#{user_phone},#{mcd_id},#{status},now(),#{adress})")
//    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int  createOrder(String user_phone,int mcd_id,int status,String adress);

    //查询商品 团购中的订单(人头图片)
    @Select("select i.img,o.* from pm_order o  left join User u on o.user_phone =u.phone left join img i on i.id =u.img_id where o.mcd_id =#{mcd_id} and o.status =0 order by o.create_time desc")
    @Results(id = "getMcdOrder", value = {
            @Result(property = "img", column = "img", javaType = String.class),
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "user_phone", column = "user_phone", javaType = String.class),
            @Result(property = "mcd_id", column = "mcd_id", javaType = Integer.class),
            @Result(property = "status", column = "status", javaType = Integer.class),
            @Result(property = "create_time", column = "create_time", javaType = String.class),
    })
    List<ReOrder> getMcdOrder(int mcd_id);



    //查询所有订单(管理端)
    @Select("<script> select o.*,u.name,u.price,u.img_id from pm_order o  left join merchandise u on o.mcd_id =u.id  <when test='phone!=null'> where o.user_phone like #{phone} </when>  order by o.create_time desc limit #{start},#{limit}</script> ")
    @Results(id = "getOrderList", value = {
            @Result(property = "img", column = "img", javaType = String.class),
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "user_phone", column = "user_phone", javaType = String.class),
            @Result(property = "mcd_id", column = "mcd_id", javaType = Integer.class),
            @Result(property = "status", column = "status", javaType = Integer.class),
            @Result(property = "create_time", column = "create_time", javaType = String.class),
    })
    List<Map<String,Object>> getOrderList(String phone,int start, int limit);

    /**
     * 查询商品拼团数量
     */
    @Select("select count(1) as count  from  pm_order  ")
    @Results(id = "getOrderCount", value = {
            @Result(property = "count", column = "count", javaType = Integer.class)
    })
    int getOrderCount();

    //会员查询自己的订单
    @Select("select o.*,u.name,u.price,u.img_id from pm_order o  left join merchandise u on o.mcd_id =u.id where o.user_phone =#{phone}   order by o.create_time desc")
    @Results(id = "getMyOrderList", value = {
            @Result(property = "img", column = "img", javaType = String.class),
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "user_phone", column = "user_phone", javaType = String.class),
            @Result(property = "mcd_id", column = "mcd_id", javaType = Integer.class),
            @Result(property = "status", column = "status", javaType = Integer.class),
            @Result(property = "create_time", column = "create_time", javaType = String.class),
    })
    List<Map> getMyOrderList(String phone);

    /**
     * 修改订单状态（是否拼团成功）
     * @param status
     * @param mcd_id
     * @return
     */
    @Update("update pm_order set status =#{status} where mcd_id =#{mcd_id}")
    int updateOrderStatus(int status,int mcd_id);


    /**
     * 查询商品拼团数量
     */
    @Select("select count(1) as count  from  pm_order where mcd_id =#{mcd_id} and status =0 ")
    @Results(id = "getMcdOrderCoount", value = {
            @Result(property = "count", column = "count", javaType = Integer.class)
    })
    int getMcdOrderCount(int mcd_id);


}
