package com.example.tuangou.mapper;

import com.example.tuangou.pojo.chnl.Merchandise;
import com.example.tuangou.pojo.chnl.ReMerchandise;
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
public interface Merchandiseapper {

    //添加商品
    @Insert("insert into merchandise(name,type_id,group_num,price,img_id,recommend_num,create_time,details_img_id,details) values(#{name},#{type_id},#{group_num},#{price},#{img_id},#{recommend_num},now(),#{details_img_id},#{details})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addMerchandise(Merchandise merchandise);

    /**
     * 编辑商品
     * @param merchandise
     */
    @Update("<script> update merchandise set details=#{details},name=#{name},type_id=#{type_id},group_num=#{group_num},recommend_num=#{recommend_num},create_time =now() <when test=\"img_id!=null and img_id !='' \"> ,img_id = #{img_id}</when> <when test='details_img_id !=0'> ,details_img_id = #{details_img_id}</when> where id =#{id}  </script>")
    void updateMerchandise(Merchandise merchandise);


    /**
     * 查询商品总数
     * @return
     */
    @Select("<script> select count(1) as count from merchandise  <when test='name !=null'> where name like #{name}</when> </script> ")
    @Results(id = "getMcdCount", value = {
            @Result(property = "count", column = "count", javaType = Integer.class)
    })
    int getMcdCount(String name);

    /**
     * 查询商品分页 (管理端)
     * @return
     */
    @Select("<script> select c.name as type_name,mcd.*, dimg.img as dimg ,c.name as type_name   from merchandise mcd  left join img dimg on mcd.details_img_id=dimg.id  left join classify c on mcd.type_id=c.id <when test='name !=null'> where mcd.name like #{name} </when> order by create_time desc  limit #{start},#{limit} </script> ")
    @Results(id = "getMcdPage", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "type_id", column = "type_id", javaType = Integer.class),
            @Result(property = "group_num", column = "group_num", javaType = Integer.class),
            @Result(property = "price", column = "price", javaType = Double.class),
            @Result(property = "img_id", column = "img_id", javaType = String.class),
            @Result(property = "recommend_num", column = "recommend_num", javaType = Integer.class),
            @Result(property = "type_name", column = "type_name", javaType = String.class),
            @Result(property = "dimg", column = "dimg", javaType = String.class)
    })
    List<ReMerchandise> getMcdPage(String name,int start, int limit);


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
