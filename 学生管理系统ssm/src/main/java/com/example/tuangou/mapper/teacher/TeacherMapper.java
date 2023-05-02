package com.example.tuangou.mapper.teacher;

import com.example.tuangou.pojo.doctor.Doctor;
import com.example.tuangou.pojo.teacher.Teacher;
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
public interface TeacherMapper {

    //新增老师
    @Insert("insert into teacher(faculty,education,name,tno,post,passwork,img_id,desc_img_id,descz) values(#{faculty},#{education},#{name},#{tno},#{post},#{passwork},#{img_id},#{desc_img_id},#{desc})")
    @Options(useGeneratedKeys = true,keyProperty = "tid",keyColumn = "tid")
    void addTeacher(Teacher teacher);

    /**
     * 查询分页 (管理端)
     * @return
     */
    @Select("<script> select  d.*,i2.img as desc_img  from teacher d left join img  i2 on i2.id =d.desc_img_id  <when test='name !=null'> where d.name like #{name} </when>   limit #{start},#{limit} </script> ")
    @Results(id = "getTeacherList", value = {
            @Result(property = "tid", column = "tid", javaType = Integer.class),
            @Result(property = "faculty", column = "faculty", javaType = String.class),
            @Result(property = "education", column = "education", javaType = String.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "tno", column = "tno", javaType = String.class),
            @Result(property = "desc", column = "descz", javaType = String.class),
            @Result(property = "post", column = "post", javaType = String.class),
            @Result(property = "passwork", column = "passwork", javaType = String.class),
            @Result(property = "img_id", column = "img_id", javaType = String.class),
            @Result(property = "desc_img_id", column = "desc_img_id", javaType = String.class),
            @Result(property = "desc_img", column = "desc_img", javaType = String.class),
    })
    List<Teacher> getTeacherList(String name, int start, int limit);

    /**
     * 查询总数
     * @return
     */
    @Select("<script> select count(1) as count from teacher  <when test='name !=null'> where name like #{name}</when> </script> ")
    @Results(id = "getTeacherCount", value = {
            @Result(property = "count", column = "count", javaType = Integer.class)
    })
    int getTeacherCount(String name);

}
