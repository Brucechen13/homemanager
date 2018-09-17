package com.cc.homeserver.utils;

import java.util.HashMap;
import java.util.Map;

public class WebResponse{

    public static JsonResponse getSuccessResponse(String msg){
        return JsonResponse.succ(msg);
    }

    public static JsonResponse getSuccessResponse(String msg, Object val){
        return JsonResponse.succ(msg, val);
    }

    public static JsonResponse getFailResponse(String msg){
        return JsonResponse.fail(msg);
    }
}
