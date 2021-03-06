package com.cc.homeserver.handle;

import com.cc.homeserver.utils.Codes;
import com.cc.homeserver.utils.JsonResponse;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 处理系统自定义的异常
     *
     * @param e 异常
     * @return 状态码和错误信息
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public JsonResponse handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return new JsonResponse("401", false, Codes.SQL_ERR, "数据库中已存在该记录", null);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public JsonResponse handleAuthorizationException(AuthorizationException e) {
        logger.error(e.getMessage(), e);
        return new JsonResponse("401", false, Codes.SHIRO_ERR, "没有权限，请联系管理员授权", null);
    }
    /**
     * 处理异常
     *
     * @param e 异常
     * @return 状态码
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResponse handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return new JsonResponse("401", false, Codes.SERVER_ERR,
                "服务器出现错误"+e.getLocalizedMessage(), null);
    }

    //不满足@RequiresGuest注解时抛出的异常信息
    private static final String GUEST_ONLY = "Attempting to perform a guest-only operation";


    @org.springframework.web.bind.annotation.ExceptionHandler(ShiroException.class)
    @ResponseBody
    public JsonResponse handleShiroException(ShiroException e) {
        String eName = e.getClass().getSimpleName();
        logger.error("shiro执行出错：{}",eName);
        return new JsonResponse(eName, false, Codes.SHIRO_ERR, "鉴权或授权过程出错", null);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    @ResponseBody
    public JsonResponse handleBusinessException(BusinessException e) {
        String eName = e.getClass().getSimpleName();
        logger.error("程序出错", eName);
        return new JsonResponse(eName, false, Codes.SHIRO_ERR, "程序出错" + e.getResultEnum(), null);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public JsonResponse page401(UnauthenticatedException e) {
        String eMsg = e.getMessage();
        if (StringUtils.startsWithIgnoreCase(eMsg,GUEST_ONLY)){
            return new JsonResponse("401", false, Codes.UNAUTHEN, "只允许游客访问，若您已登录，请先退出登录", null)
                    .data("detail",e.getMessage());
        }else{
            return new JsonResponse("401", false, Codes.UNAUTHEN, "用户未登录", null)
                    .data("detail",e.getMessage());
        }
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public JsonResponse page403() {
        return new JsonResponse("403", false, Codes.UNAUTHZ, "用户没有访问权限", null);
    }
}
