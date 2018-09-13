package com.cc.homeserver.utils;

import java.util.HashMap;
import java.util.Map;

public class WebResponse{

    public static Map<String, Object> getSuccessResponse(Object val){
        Map<String, Object> res = new HashMap<>();
        res.put("success", "true");
        res.put("data", val);
        return res;
    }

    public static Map<String, Object> getFailResponse(Object val){
        Map<String, Object> res = new HashMap<>();
        res.put("success", "false");
        res.put("msg", val);
        return res;
    }
}
