package com.example.tuangou.pojo.result;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Ccs ｡◕‿◕｡
 * @Date 2023/1/5 3:36 AM
 */
public class Result {
    public static Map Result (String code,String msg){
        Map result = new HashMap();
        result.put("code",code);
        result.put("msg",msg);
        return  result;
    }
}
