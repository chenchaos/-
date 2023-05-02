package com.example.tuangou.mapper.doctor.chnl;

import com.example.tuangou.pojo.chnl.ReMerchandise;
import com.example.tuangou.pojo.doctor.Chat;
import com.example.tuangou.pojo.doctor.Doctor;
import com.example.tuangou.pojo.mng.Img;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2022/12/10 8:24 PM
 */


@Component
@Mapper
//此处的@Mapper注解可以放在启动类上这样不用每个Mapper接口上都加
public interface ChatMapper {

    //聊天
    @Insert("insert into chat(name,order_id,text,create_time) values(#{name},#{order_id},#{text},now())")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addChat(Chat chat);


    /**
     * 查看订单聊天记录
     * @return
     */
    @Select("<script>  select name,order_id,text,DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%s') as create_time from chat where order_id =#{order_id} <when test='create_time !=null'  > and create_time >#{create_time}</when>   order by create_time asc </script> ")
    @Results(id = "getChatList", value = {
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "order_id", column = "order_id", javaType = int.class),
            @Result(property = "text", column = "text", javaType = String.class),
            @Result(property = "create_time", column = "create_time", javaType = String.class)
    })
    List<Chat> getChatList(String order_id,String create_time);

    /**
     * 查询商品分页 (管理端)
     * @return
     */
    @Select("<script> select  d.*,i2.img as desc_img  from doctor d left join img  i2 on i2.id =d.desc_img_id  <when test='name !=null'> where d.name like #{name} </when>   limit #{start},#{limit} </script> ")
    @Results(id = "getDoctorList", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "desc", column = "desc", javaType = String.class),
            @Result(property = "img_id", column = "img_id", javaType = String.class),
            @Result(property = "desc_img_id", column = "desc_img_id", javaType = String.class),
            @Result(property = "desc_img", column = "desc_img", javaType = String.class),
    })
    List<Doctor> getDoctorList(String name,int start, int limit);




    /**
     * 随机取一个医生
     * @return
     */
    @Select("SELECT * FROM doctor WHERE id >= (SELECT FLOOR( MAX(id) * RAND()) FROM doctor ) ORDER BY id LIMIT 1")
    @Results(id = "getRandDoctor", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "desc", column = "desc", javaType = String.class),
            @Result(property = "img_id", column = "img_id", javaType = String.class),
            @Result(property = "desc_img_id", column = "desc_img_id", javaType = String.class),
            @Result(property = "desc_img", column = "desc_img", javaType = String.class),
    })
    Doctor getRandDoctor();


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
