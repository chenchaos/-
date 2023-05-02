//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.tuangou.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.tuangou.mapper.Merchandiseapper;
import com.example.tuangou.mapper.doctor.chnl.*;
import com.example.tuangou.mapper.teacher.TeacherMapper;
import com.example.tuangou.pojo.doctor.*;
import com.example.tuangou.pojo.mng.Img;
import com.example.tuangou.pojo.result.Result;
import com.example.tuangou.pojo.teacher.Teacher;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping(value = "")
public class TeacherController {
    public static Logger logger = Logger.getLogger(TeacherController.class);
    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private Merchandiseapper merchandiseapper;

    @Autowired
    private OrdeMapper orderMapper;

    @Autowired
    private DrugMsgMapper drugMsgMapper;

    @Autowired
    private DrugOrerMapper drugOrerMapper;

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private TeacherMapper teacherMapper;
    /**
     * 新增教师
     * @return
     */
    @RequestMapping(
            value = {"/addTeacher"},
            method = {RequestMethod.POST}
    )
    public Map addTeacher(
            @RequestBody Teacher teacher){
        Map result  = null;
        try {
            result =Result.Result("1", "新增教师成功");
            teacher.setPasswork("123456");
            this.teacherMapper.addTeacher(teacher);
        }catch (Exception ex){
            result =Result.Result("ccs", "新增教师失败");
            logger.error("新增教师失败",ex);
        }
        return result;
    }


    /**
     * 查询教师分页
     * @return
     */
    @RequestMapping(
            value = {"/getTeacherPage"},
            method = {RequestMethod.POST}
    )
    public Map getTeacherPage(
            @RequestBody JSONObject jsonParam){
        Map result  = null;
        try {
            result =Result.Result("1", "查询教师分页成功");
            int start =(Integer)jsonParam.get("start");
            int limit =(Integer)jsonParam.get("limit");
            Object name =jsonParam.get("name");
            String nameStr =null;
            if(name!=null&&!name.equals("")){
                nameStr ="%"+name.toString()+"%";
            }
            List<Teacher> teacherList = this.teacherMapper.getTeacherList(nameStr, start, limit);
            for(Teacher teacher:teacherList){
                String img_id = teacher.getImg_id();
                List<String> imgList = new ArrayList();
                for(String img_idz:img_id.split(",")){
                    Img img = this.merchandiseapper.getImgById(img_idz);
                    imgList.add(img.getImg());
                }
                teacher.setImg(imgList);
            }
            int teacherCount = this.teacherMapper.getTeacherCount(nameStr);
            result.put("rows",teacherList);
            result.put("total",teacherCount);
        }catch (Exception ex){
            result =Result.Result("ccs", "查询教师分页失败");
            logger.error("查询教师分页失败",ex);
        }
        return result;
    }

}
