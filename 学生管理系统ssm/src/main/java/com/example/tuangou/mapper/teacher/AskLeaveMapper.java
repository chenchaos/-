package com.example.tuangou.mapper.teacher;


import com.example.tuangou.pojo.teacher.AskLeave;
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
public interface AskLeaveMapper {

    //新增请假单
    @Insert("insert into askLeave(id, sid, leave_resaon, start_time, end_time, create_time) " +
            "values(#{id}, #{sid}, #{leave_resaon}, #{start_time}, #{end_time}, #{create_time}")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addAskLeave(AskLeave askLeave);





    /**
     * 查询分页 (管理端)
     * @return
     */
    @Select("<script> select  a.*,b.name,b.faculty  from askleave a, student b    where a.sid=b.sid " +
            "<when test=\"tid!=null and tid !=''\"> and b.teacher_id=#{tid} </when>  " +
            "<when test=\"sid!=null and sid !=0\"> and a.sid = #{sid} </when>  " +
            "<when test=\"name!=null and name !=''\"> and b.name like CONCAT('%',#{name},'%') </when>" +
            "   order by a.create_time desc limit #{start},#{limit} </script> ")
    @Results(id = "getTeacherAskLeaveList", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "sid", column = "sid", javaType = Integer.class),
            @Result(property = "leave_reason", column = "leave_reason", javaType = String.class),
            @Result(property = "start_time", column = "start_time", javaType = Date.class),
            @Result(property = "end_time", column = "end_time", javaType = Date.class),
            @Result(property = "create_time", column = "create_time", javaType = Date.class),
    })
    List<AskLeave> getTeacherAskLeaveList(int start, int limit, int sid, int tid, String name);


    /**
     * 查询总数(根据当前登录教师id)
     * @return
     */
    @Select("<script> select count(1) as count from askleave a, student b   where a.sid=b.sid " +
            "<when test=\"tid!=null and tid !=0\"> and b.teacher_id=#{tid} </when>  " +
            "<when test=\"sid!=null and sid !=0\"> and a.sid = #{sid} </when>  " +
            "<when test=\"name!=null and name !=''\"> and b.name like CONCAT('%',#{name},'%') </when>" +
            "</script> ")
    @Results(id = "getTeacherAskLeaveCount", value = {
            @Result(property = "count", column = "count", javaType = Integer.class)
    })
    int getTeacherAskLeaveCount(int sid, int tid, String name);


}
