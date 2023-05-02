package com.example.tuangou.mapper.doctor.chnl;

import com.example.tuangou.pojo.chnl.ReMerchandise;
import com.example.tuangou.pojo.doctor.Doctor;
import com.example.tuangou.pojo.doctor.Order;
import com.example.tuangou.pojo.doctor.UserOrder;
import com.example.tuangou.pojo.mng.Img;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2022/12/10 8:24 PM
 */


@Component
@Mapper
//此处的@Mapper注解可以放在启动类上这样不用每个Mapper接口上都加
public interface OrdeMapper {



    //就症下单
    @Insert("insert into fall_order(fall_time,is_see,detail,doctor_id,user_id,create_time) values(#{fall_time},#{is_see},#{detail},#{doctor_id},#{user_id},now())")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void insert(Order order);

    /**
     * 查询我的订单
     *
     * @return
     */
    @Select("select  ord.*,u.phone,d.name as doctor_name,d.detail as doctor_detail,d.desc_img_id as doctor_desc_img_id,d.img_id as doctor_img_id from fall_order ord left join doctor d on ord.doctor_id =d.id  left join User  u on u.id =ord.user_id  where ord.user_id =#{userId} order by create_time desc")
    @Results(id = "getMyOrder", value = {
            @Result(property = "phone", column = "phone", javaType = String.class),
            @Result(property = "order_id", column = "id", javaType = Integer.class),
            @Result(property = "is_see", column = "is_see", javaType = String.class),
            @Result(property = "detail", column = "detail", javaType = String.class),
            @Result(property = "doctor_id", column = "doctor_id", javaType = Integer.class),
            @Result(property = "user_id", column = "user_id", javaType = Integer.class),
            @Result(property = "create_time", column = "create_time", javaType = String.class),
            @Result(property = "doctor_name", column = "doctor_name", javaType = String.class),
            @Result(property = "doctor_detail", column = "doctor_detail", javaType = String.class),
            @Result(property = "doctor_desc_img_id", column = "doctor_desc_img_id", javaType = String.class),
            @Result(property = "doctor_img_id", column = "doctor_img_id", javaType = String.class),
    })
    List<UserOrder> getMyOrder(String userId);




    /**
     * 医生获取病症列表 (管理端)
     * @return
     */
    @Select("<script> select  ord.*,u.phone  from fall_order ord  left join User  u on u.id =ord.user_id where ord.doctor_id=#{doctor_id} <when test='phone !=null'> and u.phone like #{phone} </when>   limit #{start},#{limit} </script> ")
    @Results(id = "getUserFallList", value = {
            @Result(property = "phone", column = "phone", javaType = String.class),
            @Result(property = "order_id", column = "id", javaType = Integer.class),
            @Result(property = "is_see", column = "is_see", javaType = String.class),
            @Result(property = "detail", column = "detail", javaType = String.class),
            @Result(property = "doctor_id", column = "doctor_id", javaType = Integer.class),
            @Result(property = "user_id", column = "user_id", javaType = Integer.class),
            @Result(property = "create_time", column = "create_time", javaType = String.class),
    })
    List<UserOrder> getUserFallList(String doctor_id, String phone,int start, int limit);


    /**
     * 医生获取病症列表总数 (管理端)
     * @return
     */
    @Select("<script> select  count(1) as count from fall_order ord  left join User  u on u.id =ord.user_id where ord.doctor_id=#{doctor_id} <when test='phone !=null'> and u.phone like #{phone} </when>   </script> ")

    int getUserFallCount(String doctor_id);


    /**
     * 查询图片
     * @param img
     * @return
     */
    @Select("select * from img where id=#{id} ")
    @Results(id = "getImgById", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "img", column = "img", javaType = String.class),
            @Result(property = "create_time", column = "create_time", javaType = String.class)
    })
    Img getImgById(String img);

    /**
     * 查询商品详情
     *
     * @return
     */
    @Select(" select mcd.*,i.img,dimg.img as dimg  from merchandise mcd left join img i on mcd.img_id=i.id left join img dimg on mcd.details_img_id=dimg.id   where mcd.id =#{id} ")
    @Results(id = "getMcdDetails", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "create_time", column = "create_time", javaType = String.class),
            @Result(property = "type_id", column = "type_id", javaType = Integer.class),
            @Result(property = "group_num", column = "group_num", javaType = Integer.class),
            @Result(property = "price", column = "price", javaType = Double.class),
            @Result(property = "img_id", column = "img_id", javaType = String.class),
            @Result(property = "recommend_num", column = "recommend_num", javaType = Integer.class)
    })
    ReMerchandise getMcdDetails(int id);




    /**
     * 通过分类查商品
     * @return
     */
    @Select("<script> select mcd.*,i.img  from merchandise mcd left join img i on mcd.img_id=i.id <when test='type_id !=-1'>  where mcd.type_id =#{type_id}</when> <when test='name !=null'>  where mcd.name like #{name}</when> </script> ")
    @Results(id = "getMcdByType", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "create_time", column = "create_time", javaType = String.class),
            @Result(property = "type_id", column = "type_id", javaType = Integer.class),
            @Result(property = "group_num", column = "group_num", javaType = Integer.class),
            @Result(property = "price", column = "price", javaType = Double.class),
            @Result(property = "img_id", column = "img_id", javaType = String.class),
            @Result(property = "recommend_num", column = "recommend_num", javaType = Integer.class)
    })
    List<ReMerchandise> getMcdByType(int type_id,String name);




    /**
     * 查询推荐商品
     * @return
     */
    @Select(" select mcd.*,i.img  from merchandise mcd left join img i on mcd.img_id=i.id  where recommend_num>=4 ")
    @Results(id = "getMcdByCommend", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "create_time", column = "create_time", javaType = String.class),
            @Result(property = "type_id", column = "type_id", javaType = Integer.class),
            @Result(property = "group_num", column = "group_num", javaType = Integer.class),
            @Result(property = "price", column = "price", javaType = Double.class),
            @Result(property = "img_id", column = "img_id", javaType = String.class),
            @Result(property = "recommend_num", column = "recommend_num", javaType = Integer.class)
    })
    List<ReMerchandise> getMcdByCommend();



    /**
     * 查询上新商品
     * @return
     */
    @Select(" select mcd.*,i.img  from merchandise mcd left join img i on mcd.img_id=i.id  order by create_time desc  ")
    @Results(id = "getMcdByTime", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "create_time", column = "create_time", javaType = String.class),
            @Result(property = "type_id", column = "type_id", javaType = Integer.class),
            @Result(property = "group_num", column = "group_num", javaType = Integer.class),
            @Result(property = "price", column = "price", javaType = Double.class),
            @Result(property = "img_id", column = "img_id", javaType = String.class),
            @Result(property = "recommend_num", column = "recommend_num", javaType = Integer.class)
    })
    List<ReMerchandise> getMcdByTime();

}
