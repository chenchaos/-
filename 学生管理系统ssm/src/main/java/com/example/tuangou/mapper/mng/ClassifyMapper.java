package com.example.tuangou.mapper.mng;

import com.example.tuangou.pojo.mng.AdminUser;
import com.example.tuangou.pojo.mng.Classify;
import com.example.tuangou.pojo.mng.Img;
import com.example.tuangou.pojo.mng.ReClassify;
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
public interface ClassifyMapper {

    //添加分类
    @Insert("insert into classify(name,img_id,order_num) values(#{name},#{img_id},#{order_num})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addClassify(Classify classify);


    /**
     * 查询分类
     * @return
     */
    @Select("select c.id,c.name,c.order_num,i.img from classify c left join img i on c.img_id =i.id  where c.name like #{name}   order by order_num asc limit #{start},#{limit} ")
    @Results(id = "getReClassify", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "order_num", column = "order_num", javaType = Integer.class),
            @Result(property = "img", column = "img", javaType = String.class)
    })
    List<ReClassify> getReClassify(String name, int start,int limit);

    /**
     * 查询分类
     * @return
     */
    @Select("select c.id,c.name,c.order_num,i.img from classify c left join img i on c.img_id =i.id    order by order_num asc  limit #{start},#{limit}")
    @Results(id = "getReClassify2", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "order_num", column = "order_num", javaType = Integer.class),
            @Result(property = "img", column = "img", javaType = String.class)
    })
    List<ReClassify> getReClassify2(int start, int limit);

    /**
     * 查询分类总数
     * @return
     */
    @Select("select count(1) as count  from classify c left join img i on c.img_id =i.id  where c.name like #{name} ")
    @Results(id = "getReClassifyCount", value = {
            @Result(property = "count", column = "count", javaType = Integer.class)
    })
    int getReClassifyCount(String name);

    /**
     * 查询分类总数
     * @return
     */
    @Select("select count(1) as count from classify c left join img i on c.img_id =i.id ")
    @Results(id = "getReClassifyCount2", value = {
            @Result(property = "count", column = "count", javaType = Integer.class)
    })
    int getReClassifyCount2();

    /**
     * 编辑分类
     * @param order_num
     * @param name
     * @param img_id
     * @param id
     * @return
     */
    @Update("<script> update classify set order_num=#{order_num},name=#{name} <when test='img_id !=null'> ,img_id = #{img_id}</when> where id =#{id}  </script>")
    void updateClassify(String order_num,String name,String img_id,String id);
}
