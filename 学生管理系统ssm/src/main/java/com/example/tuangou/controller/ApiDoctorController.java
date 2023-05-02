//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.tuangou.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.tuangou.mapper.Merchandiseapper;
import com.example.tuangou.mapper.doctor.chnl.*;
import com.example.tuangou.pojo.doctor.*;
import com.example.tuangou.pojo.mng.Img;
import com.example.tuangou.pojo.result.Result;
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
@RequestMapping(value = "/api")
public class ApiDoctorController {
    public static Logger logger = Logger.getLogger(ApiDoctorController.class);
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
    /**
     * 新增医生
     * @return
     */
    @RequestMapping(
            value = {"/addDoctor"},
            method = {RequestMethod.POST}
    )
    public Map addDoctor(
            @RequestBody Doctor doctor){
        Map result  = null;
        try {
            result =Result.Result("1", "新增医生成功");
            doctor.setPassword("123456");
            this.doctorMapper.addDoctor(doctor);
        }catch (Exception ex){
            result =Result.Result("ccs", "新增医生失败");
            logger.error("新增医生失败",ex);
        }
        return result;
    }



    /**
     * 编辑商品
     * @return
     */
  /*  @RequestMapping(
            value = {"/editMcd"},
            method = {RequestMethod.POST}
    )
    public Map editMcd(
            @RequestBody Merchandise merchandise){
        Map result  = null;
        try {
            result =Result.Result("1", "编辑商品成功");
            this.merchandiseapper.updateMerchandise(merchandise);
        }catch (Exception ex){
            result =Result.Result("ccs", "编辑商品失败");
            logger.error("编辑商品失败",ex);
        }
        return result;
    }*/



    /**
     * 查询医生分页
     * @return
     */
    @RequestMapping(
            value = {"/getDockerPage"},
            method = {RequestMethod.POST}
    )
    public Map getDockerPage(
            @RequestBody JSONObject jsonParam){
        Map result  = null;
        try {
            result =Result.Result("1", "查询医生分页成功");
            int start =(Integer)jsonParam.get("start");
            int limit =(Integer)jsonParam.get("limit");
            Object name =jsonParam.get("name");
            String nameStr =null;
            if(name!=null&&!name.equals("")){
                nameStr ="%"+name.toString()+"%";
            }
            List<Doctor> mcdPage = this.doctorMapper.getDoctorList(nameStr, start, limit);
            for(Doctor doctor:mcdPage){
                String img_id = doctor.getImg_id();
                List<String> imgList = new ArrayList();
                for(String img_idz:img_id.split(",")){
                    Img img = this.merchandiseapper.getImgById(img_idz);
                    imgList.add(img.getImg());
                }
                doctor.setImg(imgList);
            }
            int mcdCount = this.doctorMapper.getMcdCount(nameStr);
            result.put("rows",mcdPage);
            result.put("total",mcdCount);
        }catch (Exception ex){
            result =Result.Result("ccs", "查询医生分页失败");
            logger.error("查询医生分页失败",ex);
        }
        return result;
    }

    /**
     * 就症下单
     * @return
     */
    @RequestMapping(
            value = {"/addOrder"},
            method = {RequestMethod.POST}
    )
    public Map addOrder(
            @RequestBody Order order){
        Map result  = null;
        try {
            result =Result.Result("1", "就症下单成功");
            Doctor randDoctor = doctorMapper.getRandDoctor();
            order.setDoctor_id(randDoctor.getId());
            this.orderMapper.insert(order);
//            this.orderMapper.addOrder(order.getFall_time(),order.getIs_see(),order.getDoctor_id(),order.getUser_id(),order.getDetail());
        }catch (Exception ex){
            result =Result.Result("ccs", "就症下单失败");
            logger.error("就症下单失败",ex);
        }
        return result;
    }



    /**
     * 我的就症订单
     * @return
     */
    @RequestMapping(
            value = {"/getMyOrder"},
            method = {RequestMethod.POST}
    )
    public Map getMyOrder(
            @RequestBody JSONObject object){
        Map result  = null;
        try {
            result =Result.Result("1", "我的就症订单成功");
            String user_id =object.get("user_id").toString();
            List<UserOrder> userOrderList = this.orderMapper.getMyOrder(user_id);
            for(UserOrder userOrder:userOrderList){
                String img_id = userOrder.getDoctor_img_id();
                List<String> imgList = new ArrayList();
                for(String img_idz:img_id.split(",")){
                    Img img = this.merchandiseapper.getImgById(img_idz);
                    imgList.add(img.getImg());
                }
                userOrder.setDoctorImgList(imgList);
                String tit_id =userOrder.getDoctor_desc_img_id();
                Img img2 = this.merchandiseapper.getImgById(tit_id);
                userOrder.setDoctorTitImg(img2.getImg());
            }
            result.put("orderList",userOrderList);
        }catch (Exception ex){
            result =Result.Result("ccs", "我的就症订单失败");
            logger.error("我的就症订单失败",ex);
        }
        return result;
    }


    /**
     * 查询医生分页
     * @return
     */
    @RequestMapping(
            value = {"/getUserFallPage"},
            method = {RequestMethod.POST}
    )
    public Map getUserFallPage(
            @RequestBody JSONObject jsonParam){
        Map result  = null;
        try {
            result =Result.Result("1", "查询患者分页成功");
            int start =(Integer)jsonParam.get("start");
            int limit =(Integer)jsonParam.get("limit");
            Object phone =jsonParam.get("phone");
            String phoneStr =null;
            if(phone!=null&&!phone.equals("")){
                phoneStr ="%"+phone.toString()+"%";
            }
            String doctor_id =jsonParam.get("doctor_id").toString();
            List<UserOrder> userFallList = this.orderMapper.getUserFallList(doctor_id,phoneStr, start, limit);
            int mcdCount = this.orderMapper.getUserFallCount(doctor_id);
            for(UserOrder userOrder:userFallList){
                int order_id = userOrder.getOrder_id();
                List<DrugOrder> drugOrder = this.drugOrerMapper.getDrugOrder(order_id);
                userOrder.setDrugOrderList(drugOrder);
            }
            result.put("rows",userFallList);
            result.put("total",mcdCount);
        }catch (Exception ex){
            result =Result.Result("ccs", "查询医生分页失败");
            logger.error("查询医生分页失败",ex);
        }
        return result;
    }


    /**
     * 查询所有药品
     * @return
     */
    @RequestMapping(
            value = {"/getAllDrug"},
            method = {RequestMethod.POST}
    )
    public Map getAllDrug(){
        Map result  = null;
        try {
            result =Result.Result("1", "查询所有药品成功");
            List<DrugMsg> allDrug = this.drugMsgMapper.getAllDrug();
            result.put("rows",allDrug);
        }catch (Exception ex){
            result =Result.Result("ccs", "查询所有药品失败");
            logger.error("查询所有药品失败",ex);
        }
        return result;
    }


    /**
     * 查询医生开的药品
     * @return
     */
    @RequestMapping(
            value = {"/getDrugOrder"},
            method = {RequestMethod.POST}
    )
    public Map getDrugOrder(@RequestBody JSONObject jsonParam){
        Map result  = null;
        try {
            int order_id =Integer.parseInt(jsonParam.get("order_id").toString());
            result =Result.Result("1", "查询医生开的药品成功");
            List<DrugOrder> drugOrder = this.drugOrerMapper.getDrugOrder(order_id);
            result.put("rows",drugOrder);
        }catch (Exception ex){
            result =Result.Result("ccs", "查询医生开的药品失败");
            logger.error("查询医生开的药品失败",ex);
        }
        return result;
    }

    /**
     * 支付药品
     * @return
     */
    @RequestMapping(
            value = {"/payDrug"},
            method = {RequestMethod.POST}
    )
    public Map payDrug(@RequestBody JSONObject jsonParam){
        Map result  = null;
        try {
            int order_id =Integer.parseInt(jsonParam.get("order_id").toString());
            result =Result.Result("1", "支付药品成功");
            this.drugOrerMapper.pay(order_id);
        }catch (Exception ex){
            result =Result.Result("ccs", "支付药品失败");
            logger.error("支付药品失败",ex);
        }
        return result;
    }


    /**
     * 开药
     * @return
     */
    @RequestMapping(
            value = {"/insertDrug"},
            method = {RequestMethod.POST}
    )
    public Map insertDrug(@RequestBody JSONObject jsonParam){
        Map result  = null;
        try {
            int order_id = Integer.parseInt(jsonParam.get("order_id").toString());
            JSONObject json = (JSONObject) JSON.toJSON(jsonParam);
            List<Map> array = (List<Map>)json.get("drugList");
            for(int i=0;i<array.size();i++){
                Map map =array.get(i);
                String name = map.get("name").toString();
                String price = map.get("price").toString();
                DrugOrder drugOrder= new DrugOrder();
                drugOrder.setOrder_id(order_id);
                drugOrder.setName(name);
                drugOrder.setPrice(price);
                this.drugOrerMapper.insert(drugOrder);
            }
            result =Result.Result("1", "开药成功");
        }catch (Exception ex){
            result =Result.Result("ccs", "开药失败");
            logger.error("开药失败",ex);
        }
        return result;
    }


    /**
     * 发送聊天消息
     * @return
     */
    @RequestMapping(
            value = {"/addChat"},
            method = {RequestMethod.POST}
    )
    public Map addChat(@RequestBody Chat chat){
        Map result  = null;
        try {
            this.chatMapper.addChat(chat);
            result =Result.Result("1", "发送聊天消息成功");
        }catch (Exception ex){
            result =Result.Result("ccs", "发送聊天消息失败");
            logger.error("发送聊天消息失败",ex);
        }
        return result;
    }



    /**
     * 查看最新聊天消息
     * @return
     */
    @RequestMapping(
            value = {"/getChatList"},
            method = {RequestMethod.POST}
    )
    public Map getChatList(@RequestBody JSONObject jsonParam){
        String order_id =jsonParam.get("order_id").toString();
        String create_time =null;
        if(jsonParam.get("create_time")!=null){
            create_time =jsonParam.get("create_time").toString();
        }
        Map result  = null;
        try {
            result =Result.Result("1", "查看最新聊天消息成功");
            List<Chat> chatList = this.chatMapper.getChatList(order_id, create_time);
            result.put("chatList",chatList);
        }catch (Exception ex){
            result =Result.Result("ccs", "查看最新聊天消息失败");
            logger.error("查看最新聊天消息失败",ex);
        }
        return result;
    }
}
