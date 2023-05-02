//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.tuangou.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.tuangou.mapper.Merchandiseapper;
import com.example.tuangou.mapper.chnl.OrderMapper;
import com.example.tuangou.mapper.chnl.UserMapper;
import com.example.tuangou.pojo.chnl.ReMerchandise;
import com.example.tuangou.pojo.chnl.User;
import com.example.tuangou.pojo.mng.Img;
import com.example.tuangou.pojo.result.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/chnl")
public class ChnlController {
    public static Logger logger = Logger.getLogger(ChnlController.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Merchandiseapper merchandiseapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 用户账号密码登录
     *
     * @return
     */
    @RequestMapping(
            value = {"/user/login"},
            method = {RequestMethod.POST}
    )
    public Map loginUser(
            @RequestBody JSONObject jsonParam) {
        Map result = Result.Result("1", "登录成功");
        try {
            String phone = jsonParam.get("phone").toString();
            String passwork = jsonParam.get("pass_work").toString();
            User user = userMapper.loginUser(phone, passwork);
            result.put("user", user);
            if (user == null) {
                result = Result.Result("ccs", "账号密码错误");
                return result;
            }
        } catch (Exception ex) {
            logger.error("登录失败", ex);
            result.put("code", "ccs");
            result.put("msg", "登录失败");
        }
        return result;
    }


    /**
     * 用户账号注册
     *
     * @return
     */
    @RequestMapping(
            value = {"/user/create"},
            method = {RequestMethod.POST}
    )
    public Map createUser(
            @RequestBody User user) {
        Map result = Result.Result("1", "账号注册成功");
        try {
            int userPhoneCount = userMapper.getUserPhoneCount(user.getPhone());
            if (userPhoneCount > 0) {
                result.put("code", "ccs");
                result.put("msg", "手机号已存在");
                return result;
            }
            userMapper.createUser(user);
        } catch (Exception ex) {
            result.put("code", "ccs");
            result.put("msg", "账号注册失败");
        }
        return result;
    }


    /**
     * 用户下单拼团
     *
     * @return
     */
    @RequestMapping(
            value = {"/user/createOrder"},
            method = {RequestMethod.POST}
    )
    public Map createOrder(
            @RequestBody JSONObject jsonParam) {
        Map result = Result.Result("1", "用户下单拼团成功");
        try {
            String phone = jsonParam.get("phone").toString();
            int mcd_id = Integer.parseInt(jsonParam.get("mcd_id").toString());
            if (phone == null || mcd_id == 0) {
                result.put("code", "ccs");
                result.put("msg", "请先登录");
                return result;
            }
            String adress = jsonParam.get("adress").toString();
            User user = userMapper.getUser(phone);
            double amount = user.getAmount();//用户余额
            //查询商品成团数
            ReMerchandise mcdDetails = merchandiseapper.getMcdDetails(mcd_id);
            if (mcdDetails == null) {
                result.put("code", "ccs");
                result.put("msg", "查询不到此商品");
                return result;
            }
            int group_num = mcdDetails.getGroup_num();//成团数
            double price = mcdDetails.getPrice();//价格
            if (amount < price) {
                result.put("code", "ccs");
                result.put("msg", "余额不足");
                return result;
            }
            //当前商品拼团数量
            int mcdOrderCount = orderMapper.getMcdOrderCount(mcd_id);
            //创建订单
            orderMapper.createOrder(phone, mcd_id, 0,adress);
            if (mcdOrderCount + 1 >= group_num) {
                //成功成团 修改此商品拼团中的状态
                orderMapper.updateOrderStatus(1, mcd_id);
            }
            //扣减用户余额
            userMapper.rechargeUserAmount(-price,phone);
        } catch (Exception ex) {
            result.put("code", "ccs");
            result.put("msg", "用户下单拼团失败");
            logger.error("用户下单拼团失败",ex);
        }
        return result;
    }

    /**
     * 用户充值
     *
     * @return
     */
    @RequestMapping(
            value = {"/rechargeUserAmount"},
            method = {RequestMethod.POST}
    )
    public Map rechargeUserAmount(
            @RequestBody JSONObject jsonParam) {
        Map result = null;
        try {
            result = Result.Result("1", "用户充值成功");
            int amount = Integer.parseInt(jsonParam.get("amount").toString()) ;
            String phone = jsonParam.get("phone").toString();
            this.userMapper.rechargeUserAmount(amount,phone);
        } catch (Exception ex) {
            result = Result.Result("ccs", "用户充值失败");
            logger.error("用户充值失败", ex);
        }
        return result;
    }


    /**
     * 用户查询订单
     *
     * @return
     */
    @RequestMapping(
            value = {"/getOrderList"},
            method = {RequestMethod.POST}
    )
    public Map getOrderList(
            @RequestBody JSONObject jsonParam) {
        Map result = null;
        try {
            result = Result.Result("1", "用户查询订单成功");
            String phone = jsonParam.get("phone").toString();
            List<Map> myOrderList = this.orderMapper.getMyOrderList(phone);
            for(Map map:myOrderList){
                if(map.get("img_id")!=null){
                    String img_id =map.get("img_id").toString() ;
                    String img_idz =img_id.split(",")[0];
                    Img img = this.merchandiseapper.getImgById(img_idz);
                    map.put("mcdImg",img.getImg());
                }
            }
            result.put("rows",myOrderList);
        } catch (Exception ex) {
            result = Result.Result("ccs", "用户查询订单失败");
            logger.error("用户查询订单失败", ex);
        }
        return result;
    }
    /**
     * 查询用户列表
     *
     * @return
     */
    @RequestMapping(
            value = {"/getUserList"},
            method = {RequestMethod.POST}
    )
    public Map getUserList(
            @RequestBody JSONObject jsonParam) {
        Map result = null;
        try {
            result = Result.Result("1", "查询用户列表成功");
            int start = (Integer) jsonParam.get("start");
            int limit = (Integer) jsonParam.get("limit");
            Object phoneObj = jsonParam.get("phone");
            String phone = null;
            if (phoneObj != null && !phoneObj.equals("")) {
                phone = phoneObj.toString();
                phone ="%"+phone+"%";
            }
            List<User> userList = this.userMapper.getUserList(phone,start,limit);
            int total = this.userMapper.getUserCount(phone);
            result.put("total", total);
            result.put("rows", userList);
        } catch (Exception ex) {
            result = Result.Result("ccs", "查询用户列表失败");
            logger.error("查询用户列表失败", ex);
        }
        return result;
    }
}
