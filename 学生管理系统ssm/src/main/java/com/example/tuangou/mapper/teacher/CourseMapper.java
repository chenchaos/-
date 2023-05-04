package com.example.tuangou.mapper.teacher;

import com.example.tuangou.pojo.teacher.*;
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
public interface CourseMapper {

    //新增课程
    @Insert("insert into course(kid, create_time, end_time, name, tid, weeks, faculty) " +
            "values(#{kid},#{create_time},#{end_time},#{name},#{tid},#{weeks},#{faculty})")
    @Options(useGeneratedKeys = true,keyProperty = "kid",keyColumn = "kid")
    void addCourse(Course course);

    //编辑课程
    @Update("update course set create_time=#{create_time}," +
            " end_time=#{end_time}, name=#{name}, tid=#{tid}, weeks=#{weeks}, faculty=#{faculty} " +
            " where kid=#{kid} ")
    void updateCourse(Course course);

    // 查询课程评分列表（无分页）
    @Select("select c.kid,  create_time,\n" +
            " end_time, c.name, c.tid, c.weeks, c.faculty, " +
            " (select k.id from kscore k where k.kid = c.kid and k.sid=#{sid}) id ," +
            " (select k.score from kscore k where k.kid = c.kid and k.sid=#{sid}) score " +
            "from course c where c.tid = #{tid}")
    List<ScoreCourse> getScoreCourseList(String sid, String tid);



    /**
     * 查询分页 (管理端)
     * @return
     */
    @Select("<script> select  *  from course  where 1=1 " +
            " <when test='tid !=null'> and tid = #{tid} </when>  " +
            "<when test='name !=null'> and name like #{name} </when>" +
            "   limit #{start},#{limit} </script> ")
    @Results(id = "getTeacherCourseList", value = {
            @Result(property = "kid", column = "kid", javaType = Integer.class),
            @Result(property = "faculty", column = "faculty", javaType = String.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "create_time", column = "create_time", javaType = Date.class),
            @Result(property = "end_time", column = "end_time", javaType = Date.class),
            @Result(property = "tid", column = "tid", javaType = String.class),
            @Result(property = "weeks", column = "weeks", javaType = String.class),
    })
    List<Course> getTeacherCourseList(String name, int start, int limit, String tid);


    /**
     * 查询总数(根据当前登录教师id)
     * @return
     */
    @Select("<script> select count(1) as count from course  where 1=1  " +
            " <when test='tid !=null'> and tid = #{tid} </when>  " +
            " <when test='name !=null'> and name like #{name} </when> " +
            "</script> ")
    @Results(id = "getTeacherCourseCount", value = {
            @Result(property = "count", column = "count", javaType = Integer.class)
    })
    int getTeacherCourseCount(String name, String tid);


    @Delete("delete from course where kid = #{kid}")
    int removeCourse(int kid);
}
