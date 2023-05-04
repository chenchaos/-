package com.example.tuangou.mapper.teacher;


import com.example.tuangou.pojo.teacher.Timetable;
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
public interface TimetableMapper {

    //新增功课
    @Insert("insert into timetable(id, am8, am10, pm2, pm4, weekday, faculty) " +
            "values(#{id}, #{am8}, #{am10}, #{pm2}, #{pm4}, #{weekday}, #{faculty})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addTimetable(Timetable timetable);

    //编辑功课
    @Update("update timetable set am8=#{am8}, " +
            " am10=#{am10}, pm2=#{pm2}, pm4=#{pm4}, weekday=#{weekday}, faculty=#{faculty} " +
            " where id=#{id} ")
    void updateTimetable(Timetable timetable);




    /**
     * 查询分页 (管理端)
     * @return
     */
    @Select("<script> select  *  from timetable  where 1=1 " +
            "<when test=\"weekday!=null and weekday !=''\"> and weekday = #{weekday} </when>  " +
            "<when test=\"faculty!=null and faculty !=''\"> and faculty like CONCAT('%',#{faculty},'%') </when>" +
            "   order by faculty, weekday limit #{start},#{limit} </script> ")
    @Results(id = "getTeacherTimetableList", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "faculty", column = "faculty", javaType = String.class),
            @Result(property = "am8", column = "am8", javaType = String.class),
            @Result(property = "am10", column = "am10", javaType = String.class),
            @Result(property = "pm2", column = "pm2", javaType = String.class),
            @Result(property = "pm4", column = "pm4", javaType = String.class),
            @Result(property = "weekday", column = "weekday", javaType = String.class),
    })
    List<Timetable> getTeacherTimetableList(int start, int limit, String faculty, String weekday);


    /**
     * 查询总数(根据当前登录教师id)
     * @return
     */
    @Select("<script> select count(1) as count from timetable  where 1=1  " +
            "<when test=\"weekday!=null and weekday !=''\"> and weekday = #{weekday} </when>  " +
            "<when test=\"faculty!=null and faculty !=''\"> and faculty like CONCAT('%',#{faculty},'%') </when>" +
            "</script> ")
    @Results(id = "getTeacherTimetableCount", value = {
            @Result(property = "count", column = "count", javaType = Integer.class)
    })
    int getTeacherTimetableCount(String faculty, String weekday);


    @Delete("delete from timetable where id = #{id}")
    int removeTimetable(int id);
}
