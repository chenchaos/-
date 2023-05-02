//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.tuangou.controller;
import com.example.tuangou.mapper.chnl.OrderMapper;
import com.alibaba.fastjson.JSONObject;
import com.example.tuangou.mapper.FileMapper;
import com.example.tuangou.mapper.Merchandiseapper;
import com.example.tuangou.mapper.mng.AdminUserMapper;
import com.example.tuangou.mapper.mng.ClassifyMapper;
import com.example.tuangou.pojo.chnl.Merchandise;
import com.example.tuangou.pojo.chnl.ReMerchandise;
import com.example.tuangou.pojo.chnl.ReOrder;
import com.example.tuangou.pojo.mng.Img;
import com.example.tuangou.pojo.result.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping(value = "")
public class McdController {
    public static Logger logger = Logger.getLogger(McdController.class);
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private ClassifyMapper classifyMapper;
    @Autowired
    private Merchandiseapper merchandiseapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 新增商品
     * @return
     */
    @RequestMapping(
            value = {"/addMcd"},
            method = {RequestMethod.POST}
    )
    public Map addMcdClassify(
            @RequestBody Merchandise merchandise){
        Map result  = null;
        try {
            result =Result.Result("1", "新增商品成功");
            this.merchandiseapper.addMerchandise(merchandise);
        }catch (Exception ex){
            result =Result.Result("ccs", "新增商品失败");
            logger.error("新增商品失败",ex);
        }
        return result;
    }



    /**
     * 编辑商品
     * @return
     */
    @RequestMapping(
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
    }



    /**
     * 查询商品分页
     * @return
     */
    @RequestMapping(
            value = {"/getMcdPage"},
            method = {RequestMethod.POST}
    )
    public Map getMcdPage(
            @RequestBody JSONObject jsonParam){
        Map result  = null;
        try {
            result =Result.Result("1", "查询商品分页成功");
            int start =(Integer)jsonParam.get("start");
            int limit =(Integer)jsonParam.get("limit");
            Object name =jsonParam.get("name");
            String nameStr =null;
            if(name!=null&&!name.equals("")){
                nameStr ="%"+name.toString()+"%";
            }
            List<ReMerchandise> mcdPage = this.merchandiseapper.getMcdPage(nameStr, start, limit);
            for(ReMerchandise reMerchandise:mcdPage){
                String img_id = reMerchandise.getImg_id();
                List<String> imgList = new ArrayList();
                for(String img_idz:img_id.split(",")){
                    Img img = this.merchandiseapper.getImgById(img_idz);
                    imgList.add(img.getImg());
                }
                reMerchandise.setImg(imgList);
            }
            int mcdCount = this.merchandiseapper.getMcdCount(nameStr);
            result.put("rows",mcdPage);
            result.put("total",mcdCount);
        }catch (Exception ex){
            result =Result.Result("ccs", "查询商品分页失败");
            logger.error("查询商品分页失败",ex);
        }
        return result;
    }

    /**
     * 通过分类查询商品
     * @return
     */
    @RequestMapping(
            value = {"/getMcdByType"},
            method = {RequestMethod.POST}
    )
    public Map getMcdByType(
            @RequestBody JSONObject jsonParam){
        Map result  = null;
        try {
            result =Result.Result("1", "通过分类查询商品成功");
            String type_idStr =jsonParam.get("type_id").toString();
            String name =null;
            if(jsonParam.get("name")!=null){
                name =jsonParam.get("name").toString();
            }
            int type_id =Integer.parseInt(type_idStr);
            List<ReMerchandise> mcdByType = this.merchandiseapper.getMcdByType(type_id,name);
            setMcdImg(mcdByType);
            result.put("rows",mcdByType);
        }catch (Exception ex){
            result =Result.Result("ccs", "通过分类查询商品失败");
            logger.error("通过分类查询商品失败",ex);
        }
        return result;
    }


    /**
     * 查询商品详情
     * @return
     */
    @RequestMapping(
            value = {"/getMcdDetil"},
            method = {RequestMethod.POST}
    )
    public Map getMcdDetil(
            @RequestBody JSONObject jsonParam){
        Map result  = null;
        try {
            result =Result.Result("1", "查询商品详情成功");
            String id_str =jsonParam.get("id").toString();
            int id =Integer.parseInt(id_str);
            ReMerchandise mcdDetails = this.merchandiseapper.getMcdDetails(id);
            List<ReOrder> mcdOrder = this.orderMapper.getMcdOrder(id);
            setMcdImg(mcdDetails);
            result.put("rows",mcdDetails);
            result.put("mcdOrder",mcdOrder);
        }catch (Exception ex){
            result =Result.Result("ccs", "查询商品详情失败");
            logger.error("查询商品详情失败",ex);
        }
        return result;
    }



    /**
     * 查询推荐商品
     * @return
     */
    @RequestMapping(
            value = {"/getMcdByCommend"},
            method = {RequestMethod.POST}
    )
    public Map getMcdByCommend(){
        Map result  = null;
        try {
            result =Result.Result("1", "查询推荐商品成功");
            List<ReMerchandise> reMerchandiseList = this.merchandiseapper.getMcdByCommend();
            setMcdImg(reMerchandiseList);
            result.put("rows",reMerchandiseList);
        }catch (Exception ex){
            result =Result.Result("ccs", "查询推荐商品失败");
            logger.error("查询推荐商品失败",ex);
        }
        return result;
    }


    /**
     * 查询上新商品
     * @return
     */
    @RequestMapping(
            value = {"/getMcdByTime"},
            method = {RequestMethod.POST}
    )
    public Map getMcdByTime(){
        Map result  = null;
        try {
            result =Result.Result("1", "查询上新商品成功");
            List<ReMerchandise> reMerchandiseList = this.merchandiseapper.getMcdByTime();
            setMcdImg(reMerchandiseList);
            result.put("rows",reMerchandiseList);
        }catch (Exception ex){
            result =Result.Result("ccs", "查询上新商品失败");
            logger.error("查询上新商品失败",ex);
        }
        return result;
    }

    /**
     * 查出商品图片塞入图片数组
     * @param reMerchandiseList
     */
    public void setMcdImg( List<ReMerchandise> reMerchandiseList) {
        for(ReMerchandise reMerchandise:reMerchandiseList){
            String img_id = reMerchandise.getImg_id();
            List<String> imgList = new ArrayList();
            for(String img_idz:img_id.split(",")){
                Img img = this.merchandiseapper.getImgById(img_idz);
                imgList.add(img.getImg());
            }
            reMerchandise.setImg(imgList);
        }
    }

    /**
     * 查出商品图片塞入图片数组
     * @param
     */
    public void setMcdImg( ReMerchandise reMerchandise) {
            String img_id = reMerchandise.getImg_id();
            List<String> imgList = new ArrayList();
            for(String img_idz:img_id.split(",")){
                Img img = this.merchandiseapper.getImgById(img_idz);
                imgList.add(img.getImg());
            }
            reMerchandise.setImg(imgList);
    }
}
