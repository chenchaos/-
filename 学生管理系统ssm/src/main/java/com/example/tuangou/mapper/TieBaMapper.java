/*
package com.example.tieba.mapper;

import com.example.tieba.pojo.Comment;
import com.example.tieba.pojo.TitBa;
import com.example.tieba.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

*/
/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2022/12/10 8:24 PM
 *//*


@Component
@Mapper
//此处的@Mapper注解可以放在启动类上这样不用每个Mapper接口上都加
public interface TieBaMapper {
    @Select("select * from tieba order by createTime desc")
    @Results(id = "findAllTieBa", value = {
            @Result(property = "username", column = "username", javaType = String.class),
            @Result(property = "createTime", column = "createTime", javaType = String.class),
            @Result(property = "title", column = "title", javaType = String.class),
            @Result(property = "img", column = "img", javaType = String.class),
            @Result(property = "good", column = "good", javaType = Integer.class),
            @Result(property = "img2", column = "img2", javaType = String.class),
            @Result(property = "img3", column = "img3", javaType = String.class)
    })
    List<TitBa> findAllTieBa();


    @Select("select * from tieba where id = #{id} ")
    TitBa findById(String id);

    @Select("select * from User where phone =#{phone} and passwork =#{passwork}")
    @Results(id = "loginUser", value = {
            @Result(property = "username", column = "username", javaType = String.class),
            @Result(property = "phone", column = "phone", javaType = String.class),
            @Result(property = "passwork", column = "passwork", javaType = String.class),
            @Result(property = "userimg", column = "userimg", javaType = String.class)
    })
    User loginUser(String phone,String passwork);


    //评论
    @Insert("insert into comment(tiebaid,userid,username,userimg,context,createTime) values(#{tiebaid},#{userid},#{username},#{userimg},#{context},now())")
    void addComment(String tiebaid,String userid,String username,String userimg,String context);


    //获取文档评论
    @Select("select * from comment where tiebaid =#{tiebaid} order by createTime desc")
    @Results(id = "CommentList", value = {
            @Result(property = "tiiebaid", column = "tiiebaid", javaType = String.class),
            @Result(property = "userid", column = "userid", javaType = String.class),
            @Result(property = "createTime", column = "createTime", javaType = String.class),
            @Result(property = "content", column = "content", javaType = String.class),
            @Result(property = "username", column = "username", javaType = String.class),
            @Result(property = "userimg", column = "userimg", javaType = String.class)
    })
    List<Comment> getCommentList();
//    @Insert("insert into student(sno,sname,ssex) values(#{sno},#{name},#{sex})")
//    int add(Student student);
//
//    @Update("update student set sname=#{name},ssex=#{sex} where sno=#{sno}")
//    int update(Student student);
//
//    //@Delete("delete from student where sno=#{sno}")
//    void deleteBysno(String sno);
//
//    @Select("select * from student where sno=#{sno}")
//    @Results(id = "student",value= {
//            @Result(property = "sno", column = "sno", javaType = String.class),
//            @Result(property = "name", column = "sname", javaType = String.class),
//            @Result(property = "sex", column = "ssex", javaType = String.class)
//    })
//    Student queryStudentBySno(String sno);
}*/
