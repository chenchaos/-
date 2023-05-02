//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.tuangou.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.tuangou.mapper.FileMapper;
import com.example.tuangou.mapper.chnl.OrderMapper;
import com.example.tuangou.mapper.mng.AdminUserMapper;
import com.example.tuangou.mapper.mng.ClassifyMapper;
import com.example.tuangou.mapper.teacher.TeacherMapper;
import com.example.tuangou.pojo.doctor.Doctor;
import com.example.tuangou.pojo.mng.AdminUser;
import com.example.tuangou.pojo.mng.Classify;
import com.example.tuangou.pojo.mng.Img;
import com.example.tuangou.pojo.mng.ReClassify;
import com.example.tuangou.pojo.result.Result;
import com.example.tuangou.pojo.teacher.Teacher;
import com.example.tuangou.util.GenerateBase64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class ApiMngController {
    public static Logger logger = Logger.getLogger(ApiMngController.class);
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private ClassifyMapper classifyMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private TeacherMapper teacherMapper;
    /**
     * 管理端账号密码登录
     * @return
     */
    @RequestMapping(
            value = {"/user/login"},
            method = {RequestMethod.POST}
    )
    public Map loginAdminUser(
            @RequestBody JSONObject jsonParam){
        Map result  = null;
        try{
            String user_name =jsonParam.get("user_name").toString();
            String passwork =jsonParam.get("passwork").toString();
            AdminUser adminUser = adminUserMapper.loginAdminUser(user_name, passwork);
            Teacher teacher = adminUserMapper.loginTeacher(user_name, passwork);
            if(adminUser==null&&teacher==null){
                result =Result.Result("ccs", "账号密码错误");
                return result;
            }
            result =Result.Result("1", "登陆成功");
            if(adminUser!=null){
                result.put("role_id","1");
            }else{
                result.put("role_id","2");
                result.put("teacher_id",teacher.getTid());
                result.put("teacher_name",teacher.getName());
            }
        }catch (Exception ex){
            result =Result.Result("ccs", "登陆失败");
        }
        return result;
    }


    /**
     *上传图片，使用mybatis-plus保存到数据库
     *User为实体类，数据库对应user表，有id、image两个属性
     */
    @PostMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam("file") MultipartFile file) {
        Map result  = null;
        try {
            result =Result.Result("1", "上传成功");
            if (!file.isEmpty()) {
                String base64 = GenerateBase64.generateBase64(file);
                Img img = new Img();
                img.setImg(base64);
                fileMapper.addComment(img);
                result.put("id",img.getId());
                return result;
            }
            throw new  Exception() ;
        }catch (Exception ex){
            result =Result.Result("ccs", "上传失败");
            logger.error("上传文件失败",ex);
            return result;
        }
    }


    /**
     * 新增商品分类
     * @return
     */
    @RequestMapping(
            value = {"/addClassify"},
            method = {RequestMethod.POST}
    )
    public Map addMcdClassify(
            @RequestBody Classify classify){
        Map result  = null;
        try {
            result =Result.Result("1", "新增商品分类成功");
//            String name = jsonParam.get("name").toString();
//            String img_id = jsonParam.get("img_id").toString();
//            String order = jsonParam.get("order").toString();
            this.classifyMapper.addClassify(classify);
        }catch (Exception ex){
            result =Result.Result("ccs", "新增商品分类失败");
            logger.error("新增商品分类失败",ex);
        }
        return result;
    }


    /**
     * 订单查询
     * @return
     */
    @RequestMapping(
            value = {"/getOrderList"},
            method = {RequestMethod.POST}
    )
    public Map getOrderList(
            @RequestBody JSONObject jsonParam){
        Map result  = null;
        try {
            result =Result.Result("1", "订单查询成功");
            String phone =null;
            if(jsonParam.get("phone")!=null){
                phone ="%"+jsonParam.get("phone").toString()+"%";
            }
            int start =(Integer)jsonParam.get("start");
            int limit =(Integer)jsonParam.get("limit");
            List<Map<String,Object>> orderList = this.orderMapper.getOrderList(phone,start, limit);
            for(Map<String,Object>orderMap:orderList){
                int status =(int)orderMap.get("status");
                if(status==0){
                    orderMap.put("status","拼团中");
                }else{
                    orderMap.put("status","拼团成功");
                }
            }
            int orderCount = this.orderMapper.getOrderCount();
            result.put("rows",orderList);
            result.put("total",orderCount);
        }catch (Exception ex){
            result =Result.Result("ccs", "订单查询失败");
            logger.error("订单查询失败",ex);
        }
        return result;
    }


    /**
     * 查询商品分类
     * @return
     */
    @RequestMapping(
            value = {"/getClassify"},
            method = {RequestMethod.POST}
    )
    public Map getClassify(
            @RequestBody JSONObject jsonParam){
        Map result  = null;
        try {
            result =Result.Result("1", "查询商品分类成功");
            int start =(Integer)jsonParam.get("start");
            int limit =(Integer)jsonParam.get("limit");
            Object name =jsonParam.get("name");
            if(name==null||name.equals("")){
                List<ReClassify> reClassify = this.classifyMapper.getReClassify2(start, limit);
                int total =this.classifyMapper.getReClassifyCount2();
                result.put("total",total);result.put("rows",reClassify);
            }else{
                String nameStr = name.toString();
                List<ReClassify> reClassify = this.classifyMapper.getReClassify("%"+nameStr+"%",start, limit);
                int total =this.classifyMapper.getReClassifyCount("%"+nameStr+"%");
                result.put("total",total);result.put("rows",reClassify);
            }
        }catch (Exception ex){
            result =Result.Result("ccs", "查询商品分类失败");
            logger.error("查询商品分类失败",ex);
        }
        return result;
    }

    /**
     * 编辑商品分类
     * @return
     */
    @RequestMapping(
            value = {"/editClassify"},
            method = {RequestMethod.POST}
    )
    public Map editClassify(
            @RequestBody Classify classify){
        Map result  = null;
        try {
            result =Result.Result("1", "编辑商品分类成功");
            String name =classify.getName();
            String order_num =classify.getOrder_num();
            String id =classify.getId();
            String img_id =classify.getImg_id();
//            if(classify.getImg_id()!=null&&!classify.getImg_id().equals("")){
//                img_id =" ,img_id ="+classify.getImg_id();
//            }
            this.classifyMapper.updateClassify(order_num,name,img_id,id);
        }catch (Exception ex){
            result =Result.Result("ccs", "编辑商品分类失败");
            logger.error("编辑商品分类失败",ex);
        }
        return result;
    }

}
