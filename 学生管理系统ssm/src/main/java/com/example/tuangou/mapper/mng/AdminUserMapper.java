package com.example.tuangou.mapper.mng;

import com.example.tuangou.pojo.doctor.Doctor;
import com.example.tuangou.pojo.mng.AdminUser;
import com.example.tuangou.pojo.teacher.Teacher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;


/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2022/12/10 8:24 PM
 */


@Component
@Mapper
//此处的@Mapper注解可以放在启动类上这样不用每个Mapper接口上都加
public interface AdminUserMapper {


    /**
     * 管理端账号密码登录
     * @return
     */
    @Select("select * from adminUser where user_name =#{user_name} and passwork =#{passwork}")
    @Results(id = "loginUser", value = {
            @Result(property = "user_name", column = "user_name", javaType = String.class),
            @Result(property = "passwork", column = "passwork", javaType = String.class)
    })
    AdminUser loginAdminUser(String user_name, String passwork);

    @Select("select * from doctor where name =#{user_name} and password =#{passwork}")
    @Results(id = "loginDoctor", value = {
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "password", column = "password", javaType = String.class)
    })
    Doctor loginDoctor(String user_name, String passwork);


    @Select("select * from teacher where tno =#{tno} and passwork =#{passwork}")
    @Results(id = "loginTeacher", value = {
            @Result(property = "tno", column = "tno", javaType = String.class),
            @Result(property = "passwork", column = "passwork", javaType = String.class)
    })
    Teacher loginTeacher(String tno, String passwork);
}
