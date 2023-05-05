//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.tuangou.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.tuangou.mapper.Merchandiseapper;
import com.example.tuangou.mapper.doctor.chnl.*;
import com.example.tuangou.mapper.teacher.*;
import com.example.tuangou.pojo.doctor.*;
import com.example.tuangou.pojo.enums.WeekDay;
import com.example.tuangou.pojo.mng.Img;
import com.example.tuangou.pojo.result.Result;
import com.example.tuangou.pojo.teacher.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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


    @Autowired
    private TimetableMapper timetableMapper;


    @Autowired
    private AskLeaveMapper askLeaveMapper;

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
            result =Result.Result("1", "查询学生分页成功");
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


    /**
     * 查询教师信息（根据教师id查询）
     * @return
     */
    @RequestMapping(
            value = {"/getTeacher"},
            method = {RequestMethod.POST}
    )
    public Map getTeacher(
            @RequestBody Teacher teacher){
        Map result  = null;
        try {
            result =Result.Result("1", "新增教师成功");

            if (teacher.getTid()==0) {
                throw  new Exception("参数异常");
            }

            Teacher loginTeacher = this.teacherMapper.getTeacherByTid(String.valueOf(teacher.getTid()));
            result.put("loginTeacher", loginTeacher);

        }catch (Exception ex){
            result =Result.Result("ccs", "新增教师失败");
            logger.error("新增教师失败",ex);
        }
        return result;
    }

    /********** *********
     *
     *    课程管理
     *
     * ****************************/

    /**
     * 新增课程
     * @return
     */
    @RequestMapping(
            value = {"/addCourse"},
            method = {RequestMethod.POST}
    )
    public Map addCourse(
            @RequestBody Course course){
        Map result  = null;
        try {
            result =Result.Result("1", "新增课程成功");
            // 查询老师哪个学院
            Teacher teacher =  teacherMapper.getTeacherByTid(course.getTid());
            course.setFaculty(teacher.getFaculty());
            this.courseMapper.addCourse(course);
        }catch (Exception ex){
            result =Result.Result("ccs", "新增课程失败");
            logger.error("新增课程失败",ex);
        }
        return result;
    }


    /**
     * 查询课程列表（根据教师ID）
     * @return
     */
    @RequestMapping(
            value = {"/getTeacherCoursePage"},
            method = {RequestMethod.POST}
    )
    public Map getTeacherCoursePage(
            @RequestBody JSONObject jsonParam){
        Map result  = null;
        try {
            result =Result.Result("1", "查询课程分页成功");
            int start =(Integer)jsonParam.get("start");
            int limit =(Integer)jsonParam.get("limit");
            Object name =jsonParam.get("name");
            String tid = jsonParam.getString("tid");
            if (StringUtils.isEmpty(tid)) {
                throw new Exception("教师ID为空");
            }
            String nameStr =null;
            if(name!=null&&!name.equals("")){
                nameStr ="%"+name.toString()+"%";
            }
            List<Course> courseList = this.courseMapper.getTeacherCourseList(nameStr, start, limit, tid);
            int courseCount = this.courseMapper.getTeacherCourseCount(nameStr, tid);
            result.put("rows", courseList);
            result.put("total", courseCount);
        }catch (Exception ex){
            result =Result.Result("ccs", "查询课程分页失败");
            logger.error("查询课程分页失败",ex);
        }
        return result;
    }


    /**
     * 编辑课程
     * @return
     */
    @RequestMapping(
            value = {"/editCourse"},
            method = {RequestMethod.POST}
    )
    public Map editCourse(
            @RequestBody Course course){
        Map result  = null;
        try {
            result =Result.Result("1", "编辑课程成功");
            this.courseMapper.updateCourse(course);
        }catch (Exception ex){
            result =Result.Result("ccs", "编辑课程失败");
            logger.error("编辑课程失败",ex);
        }
        return result;
    }

    /**
     * 删除课程
     * @return
     */
    @RequestMapping(
            value = {"/removeCourse"},
            method = {RequestMethod.GET}
    )
    public Map removeCourse(
            @RequestParam int kid){
        Map result  = null;
        try {
            result =Result.Result("1", "删除课程成功");
            // 先查询是否存在评分，有评分记录，不允许删除
            Kscore kscore = new Kscore();
            kscore.setKid(kid);
            List<Kscore> kscoreList = studentMapper.getKscore(kscore);
            if (kscoreList != null && kscoreList.size()>0) {
                result =Result.Result("ccs", "该课程存在评分记录，不允许删除");
                return result;
            }
            this.courseMapper.removeCourse(kid);
        }catch (Exception ex){
            result =Result.Result("ccs", "删除课程失败");
            logger.error("删除课程失败",ex);
        }
        return result;
    }




    /********** *********
     *
     *    功课表管理
     *
     * ****************************/

    /**
     * 新增功课表
     * @return
     */
    @RequestMapping(
            value = {"/addTimetable"},
            method = {RequestMethod.POST}
    )
    public Map addTimetable(
            @RequestBody Timetable timetable){
        Map result  = null;
        try {
            result =Result.Result("1", "新增功课成功");
            // 判断是否已存在
            int count = timetableMapper.getTimetable(timetable);
            if (count > 0) {
                // 获取中文
                String weekday="";
                for(WeekDay w: WeekDay.values()){
                    String day = timetable.getWeekday();
                    if (w.getCode().equals(day)) {
                        weekday = w.getMessage();
                        break;
                    }
                }
                result =Result.Result("ccs", timetable.getFaculty() + "已存在" + weekday + "的功课，不能新增");
                return result;
            }
            this.timetableMapper.addTimetable(timetable);
        }catch (Exception ex){
            result =Result.Result("ccs", "新增功课失败");
            logger.error("新增功课失败",ex);
        }
        return result;
    }


    /**
     * 查询功课列表
     * @return
     */
    @RequestMapping(
            value = {"/getTeacherTimetablePage"},
            method = {RequestMethod.POST}
    )
    public Map getTeacherTimetablePage(
            @RequestBody JSONObject jsonParam){
        Map result  = null;
        try {
            result =Result.Result("1", "查询功课分页成功");
            int start =(Integer)jsonParam.get("start");
            int limit =(Integer)jsonParam.get("limit");
            String faculty = jsonParam.getString("faculty");
            String weekday = jsonParam.getString("weekday");

            List<Timetable> timetableList = timetableMapper.getTeacherTimetableList(start, limit, faculty, weekday);
            int timetableCount = this.timetableMapper.getTeacherTimetableCount(faculty, weekday);
            result.put("rows", timetableList);
            result.put("total", timetableCount);
        }catch (Exception ex){
            result =Result.Result("ccs", "查询功课分页失败");
            logger.error("查询功课分页失败",ex);
        }
        return result;
    }


    /**
     * 编辑功课
     * @return
     */
    @RequestMapping(
            value = {"/editTimetable"},
            method = {RequestMethod.POST}
    )
    public Map editTimetable(
            @RequestBody Timetable timetable){
        Map result  = null;
        try {
            result =Result.Result("1", "编辑功课成功");
            // 判断是否已存在
            int count = timetableMapper.getTimetableNotSelf(timetable);
            if (count > 0) {
                // 获取中文
                String weekday="";
                for(WeekDay w: WeekDay.values()){
                    String day = timetable.getWeekday();
                    if (w.getCode().equals(day)) {
                        weekday = w.getMessage();
                        break;
                    }
                }
                result =Result.Result("ccs", timetable.getFaculty() + "已存在" + weekday + "的功课,不能编辑");
                return result;
            }

            this.timetableMapper.updateTimetable(timetable);
        }catch (Exception ex){
            result =Result.Result("ccs", "编辑功课失败");
            logger.error("编辑功课失败",ex);
        }
        return result;
    }

    /**
     * 删除功课
     * @return
     */
    @RequestMapping(
            value = {"/removeTimetable"},
            method = {RequestMethod.GET}
    )
    public Map removeTimetable(
            @RequestParam int id){
        Map result  = null;
        try {
            result =Result.Result("1", "删除功课成功");

            this.timetableMapper.removeTimetable(id);
        }catch (Exception ex){
            result =Result.Result("ccs", "删除功课失败");
            logger.error("删除功课失败",ex);
        }
        return result;
    }



    /**
     * 查询学生请假单列表
     * @return
     */
    @RequestMapping(
            value = {"/getTeacherAskLeavePage"},
            method = {RequestMethod.POST}
    )
    public Map getTeacherAskLeavePage(
            @RequestBody JSONObject jsonParam) {
        Map result  = null;
        try {
            result =Result.Result("1", "查询请假单分页成功");
            int start =(Integer)jsonParam.get("start");
            int limit =(Integer)jsonParam.get("limit");
            int tid = jsonParam.getInteger("tid");// 教师id
            int sid = jsonParam.getInteger("sid"); //学生id
            String name = jsonParam.getString("name");//学生姓名

            List<AskLeave> askLeaveList = askLeaveMapper.getTeacherAskLeaveList(start, limit, sid, tid, name);
            int askLeaveCount = this.askLeaveMapper.getTeacherAskLeaveCount(sid, tid, name);
            result.put("rows", askLeaveList);
            result.put("total", askLeaveCount);
        }catch (Exception ex){
            result =Result.Result("ccs", "查询请假单分页失败");
            logger.error("查询请假单分页失败",ex);
        }
        return result;
    }


}
