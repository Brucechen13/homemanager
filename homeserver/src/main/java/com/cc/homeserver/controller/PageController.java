package com.cc.homeserver.controller;

import com.cc.homeserver.utils.JsonResponse;
import com.cc.homeserver.utils.WebResponse;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

@RestController
@RequestMapping("/page")
public class PageController {

    // shiro.loginUrl映射到这里，我在这里直接抛出异常交给GlobalExceptionHandler来统一返回json信息，
    // 您也可以在这里json，不过这样子就跟GlobalExceptionHandler中返回的json重复了。
    @RequestMapping("/401")
    public JsonResponse page401() {
        throw new UnauthenticatedException();
    }

    // shiro.unauthorizedUrl映射到这里。由于demo3统一约定了url方式只做鉴权控制，不做权限访问控制，
    // 也就是说在ShiroConfig中如果没有roles[js],perms[mvn:install]这样的权限访问控制配置的话，
    // 是不会跳转到这里的。
    @RequestMapping("/403")
    public JsonResponse page403() {
        throw new UnauthorizedException();
    }

    @RequestMapping("/index")
    public JsonResponse pageIndex() {
        return new JsonResponse("index",true,1,"index page",null);
    }
}
