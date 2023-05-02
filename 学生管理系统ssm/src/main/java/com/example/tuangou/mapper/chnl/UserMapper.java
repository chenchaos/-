package com.example.tuangou.mapper.chnl;

import com.example.tuangou.pojo.chnl.User;
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
public interface UserMapper {

    //注册用户
    @Insert("insert into User(phone,pass_work,img_id) values(#{phone},#{pass_work},#{img_id})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void createUser(User user);

    //用户登录
    @Select("select * from User where phone =#{phone} and pass_work=#{pass_work}")
    @Results(id = "loginUser", value = {
            @Result(property = "phone", column = "phone", javaType = String.class),
            @Result(property = "pass_work", column = "pass_work", javaType = String.class),
            @Result(property = "img_id", column = "img_id", javaType = Integer.class),
            @Result(property = "id", column = "id", javaType = Integer.class)
    })
    User loginUser(String phone,String pass_work);


    //用户登录
    @Select("select * from User where phone =#{phone}")
    @Results(id = "getUser", value = {
            @Result(property = "phone", column = "phone", javaType = String.class),
            @Result(property = "pass_work", column = "pass_work", javaType = String.class),
            @Result(property = "img_id", column = "img_id", javaType = Integer.class),
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "amount", column = "amount", javaType = Double.class),
    })
    User getUser(String phone);


    //用户列表
    @Select("<script> select * from User <when test='phone !=null'> where phone like #{phone} </when> limit #{start},#{limit}</script>")
    @Results(id = "getUserList", value = {
            @Result(property = "phone", column = "phone", javaType = String.class),
            @Result(property = "pass_work", column = "pass_work", javaType = String.class),
            @Result(property = "img_id", column = "img_id", javaType = Integer.class),
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "amount", column = "amount", javaType = Double.class),
    })
    List<User>  getUserList(String phone,int start,int limit);

    /**
     * 用户充值
     * @param amount
     * @param phone
     */
    @Update("update User set amount=amount+#{amount} where phone =#{phone}")
    void rechargeUserAmount(double amount,String phone);

    /**
     * 查询用户总数
     * @return
     */
    @Select("<script>select count(1) as count  from User  <when test='phone !=null'>  where phone like #{phone} </when> </script>")
    @Results(id = "getUserCount", value = {
            @Result(property = "count", column = "count", javaType = Integer.class)
    })
    int getUserCount(String phone);

    /**
     * 查询手机号是否重复
     * @param phone
     * @return
     */
    @Select("select count(1) as count  from  User where phone =#{phone} ")
    @Results(id = "getPhoneCount", value = {
            @Result(property = "count", column = "count", javaType = Integer.class)
    })
    int getUserPhoneCount(String phone);

    /**
     * 充值
     * @param amount
     * @param userPhone
     * @return
     */
    @Update("update User set amount =amount+#{amount} where phone =#{userPhone}")
    int updateAmount(int amount,String userPhone);
}
