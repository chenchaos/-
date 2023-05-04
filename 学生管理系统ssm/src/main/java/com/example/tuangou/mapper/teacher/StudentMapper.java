package com.example.tuangou.mapper.teacher;

import com.example.tuangou.pojo.teacher.Course;
import com.example.tuangou.pojo.teacher.Kscore;
import com.example.tuangou.pojo.teacher.Student;
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
public interface StudentMapper {

    //新增学生
    @Insert("insert into student(sid, sex, name,sno,faculty,passwork,img_id,teacher_id) " +
            "values(#{sid},#{sex},#{name},#{sno},#{faculty},#{passwork},#{img_id},#{teacher_id})")
    @Options(useGeneratedKeys = true,keyProperty = "sid",keyColumn = "sid")
    void addStudent(Student student);


    //更新学生
    @Update("update student set sex=#{sex}, name=#{name}, sno=#{sno},faculty#{faculty}," +
            " passwork=#{passwork}, img_id=#{img_id}, teacher_id=#{teacher_id} " +
            " where sid=#{sid} ")
    void updateStudent(Student student);


    /**
     * 查询分页 (管理端)
     * @return
     */
    @Select("<script> select  d.*,i2.img as img  from student d left join img  i2 on i2.id =d.img_id  " +
            " <when test='teacher_id !=null'> where teacher_id = #{teacher_id} </when>  " +
            "<when test='name !=null'> and d.name like #{name} </when>" +
            "   limit #{start},#{limit} </script> ")
    @Results(id = "getTeacherStudentList", value = {
            @Result(property = "sid", column = "sid", javaType = Integer.class),
            @Result(property = "faculty", column = "faculty", javaType = String.class),
            @Result(property = "sex", column = "sex", javaType = String.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "sno", column = "sno", javaType = String.class),
            @Result(property = "teacher_id", column = "teacher_id", javaType = String.class),
            @Result(property = "passwork", column = "passwork", javaType = String.class),
            @Result(property = "img_id", column = "img_id", javaType = String.class),
            @Result(property = "img", column = "img", javaType = String.class),
    })
    List<Student> getTeacherStudentList(String name, int start, int limit, String teacher_id);

    /**
     * 查询总数(根据当前登录教师id)
     * @return
     */
    @Select("<script> select count(1) as count from student " +
            " <when test='teacher_id !=null'> where teacher_id = #{teacher_id} </when>  " +
            " <when test='name !=null'> and name like #{name} </when> " +
            "</script> ")
    @Results(id = "getTeacherStudentCount", value = {
            @Result(property = "count", column = "count", javaType = Integer.class)
    })
    int getTeacherStudentCount(String name, String teacher_id);





    // 新增评分
    @Insert("insert into kscore(id, sid, kid, score) " +
            "values(#{id}, #{sid},#{kid},#{score})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addKscore(Kscore kscore);

    // 修改评分
    @Update("Update kscore set sid=#{sid}, kid=#{kid}, score=#{score} where id=#{id} ")
    void updateKscore(Kscore kscore);


    // 查询评分 由于int 初始值为0,得判断
    @Select("<script>" +
            " select * from kscore where 1 = 1 " +
            " <when test='sid !=null and sid!=0'> and sid = #{sid} </when>  " +
            " <when test='kid !=null  and kid!=0'> and kid = #{kid} </when>  " +
            " <when test='score !=null'> and score = #{score} </when>  " +
            " <when test='id !=null  and id!=0'> and id = #{id} </when>  " +
            "</script> ")
    List<Kscore> getKscore(Kscore kscore);

}
