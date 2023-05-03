package com.example.tuangou.mapper.teacher;

import com.example.tuangou.pojo.teacher.*;
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
public interface CourseMapper {

    //新增课程
    @Insert("insert into course(kid, create_time, end_time, name, tid, weeks, faculty) " +
            "values(#{kid},#{create_time},#{end_time},#{name},#{tid},#{weeks},#{faculty})")
    @Options(useGeneratedKeys = true,keyProperty = "kid",keyColumn = "kid")
    void addCourse(Course course);

    // 查询课程评分列表（无分页）
    @Select("select c.kid,  create_time,\n" +
            " end_time, c.name, c.tid, c.weeks, c.faculty, " +
            " (select k.id from kscore k where k.kid = c.kid and k.sid=#{sid}) id ," +
            " (select k.score from kscore k where k.kid = c.kid and k.sid=#{sid}) score " +
            "from course c where c.tid = #{tid}")
    List<ScoreCourse> getScoreCourseList(String sid, String tid);
}
