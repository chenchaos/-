//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.tuangou.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.tuangou.mapper.Merchandiseapper;
import com.example.tuangou.mapper.doctor.chnl.*;
import com.example.tuangou.mapper.teacher.CourseMapper;
import com.example.tuangou.mapper.teacher.StudentMapper;
import com.example.tuangou.mapper.teacher.TeacherMapper;
import com.example.tuangou.pojo.doctor.*;
import com.example.tuangou.pojo.mng.Img;
import com.example.tuangou.pojo.result.Result;
import com.example.tuangou.pojo.teacher.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CourseMapper courseMapper;

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



    /**
     * 新增学生（根据教师Id）
     * @return
     */
    @RequestMapping(
            value = {"/addStudent"},
            method = {RequestMethod.POST}
    )
    public Map addStudent(
            @RequestBody Student student){
        Map result  = null;
        try {
            result =Result.Result("1", "新增学生成功");
            student.setPasswork("123456");
            // 查询老师哪个学院
            Teacher teacher =  teacherMapper.getTeacherByTid(student.getTeacher_id());
            student.setFaculty(teacher.getFaculty());
            this.studentMapper.addStudent(student);
        }catch (Exception ex){
            result =Result.Result("ccs", "新增学生失败");
            logger.error("新增学生失败",ex);
        }
        return result;
    }


    /**
     * 查询学生列表（根据教师ID）
     * @return
     */
    @RequestMapping(
            value = {"/getTeacherStudentPage"},
            method = {RequestMethod.POST}
    )
    public Map getTeacherStudentPage(
            @RequestBody JSONObject jsonParam){
        Map result  = null;
        try {
            result =Result.Result("1", "查询教师分页成功");
            int start =(Integer)jsonParam.get("start");
            int limit =(Integer)jsonParam.get("limit");
            Object name =jsonParam.get("name");
            String teacher_id = jsonParam.getString("teacher_id");
            if (StringUtils.isEmpty(teacher_id)) {
                throw new Exception("教师ID为空");
            }
            String nameStr =null;
            if(name!=null&&!name.equals("")){
                nameStr ="%"+name.toString()+"%";
            }
            List<Student> studentList = this.studentMapper.getTeacherStudentList(nameStr, start, limit, teacher_id);
            int studentCount = this.studentMapper.getTeacherStudentCount(nameStr, teacher_id);
            result.put("rows",studentList);
            result.put("total",studentCount);
        }catch (Exception ex){
            result =Result.Result("ccs", "查询学生分页失败");
            logger.error("查询学生分页失败",ex);
        }
        return result;
    }




    /**
     * 学生评分（根据教师Id）
     * @return
     */
    @RequestMapping(
            value = {"/addStudentKScore"},
            method = {RequestMethod.POST}
    )
    public Map addStudentKScore(
            @RequestBody Kscore kscore) {
        Map result  = null;
        try {
            result =Result.Result("1", "评分成功");
            if (kscore.getId() == 0) {
                this.studentMapper.addKscore(kscore);
            } else {
                this.studentMapper.updateKscore(kscore);
            }
        }catch (Exception ex){
            result =Result.Result("ccs", "新增学生失败");
            logger.error("新增学生失败",ex);
        }
        return result;
    }


    /**
     * 点击评分按钮，查询课程评分列表（根据教师ID）
     * @return
     */
    @RequestMapping(
            value = {"/getScoreCourseList"},
            method = {RequestMethod.POST}
    )
    public Map getScoreCourseList(
            @RequestBody JSONObject jsonParam) {
        Map result  = null;
        try {
            result =Result.Result("1", "查询课程评分列表成功");
            String sid = jsonParam.getString("sid");

            String tid = jsonParam.getString("tid");
            if (StringUtils.isEmpty(tid)) {
                throw new Exception("教师ID为空");
            }

            List<ScoreCourse> scoreCourseist = this.courseMapper.getScoreCourseList(sid, tid);
            int scoreCourseCount = scoreCourseist.size();
            result.put("rows", scoreCourseist);
            result.put("total", scoreCourseCount);
        }catch (Exception ex){
            result =Result.Result("ccs", "查询课程评分列表失败");
            logger.error("查询课程评分列表失败",ex);
        }

        return result;
    }

}
