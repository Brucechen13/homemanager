package com.cc.springdemo.controller;


import com.cc.springdemo.entity.UserInfo;
import com.cc.springdemo.service.UserInfoService;
import com.cc.springdemo.utils.ShiroUtils;
import com.cc.springdemo.utils.WebResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserInfoService service;

    @RequestMapping(value = "/view")
    @ResponseBody
    public String updateAdminUserPassword(String newPassword) {
        // 从shiro的session中取activeUser
        Subject subject = SecurityUtils.getSubject();
        // 取身份信息
        UserInfo adminUser = (UserInfo) subject.getPrincipal();
        // 生成salt,随机生成
        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        String salt = secureRandomNumberGenerator.nextBytes().toHex();
        String newMd5Password = ShiroUtils.encryptPassword("MD5", newPassword, salt, 3);
        // 设置新密码
        adminUser.setPassword(newMd5Password);
        // 设置盐
        adminUser.setSalt(salt);
        //service.updateAdminUserPassword(adminUser);
        return newPassword;
    }

    @RequestMapping(value = "/", method = POST, produces = "application/json")
    public Map<String, Object> saveUser(@RequestBody UserInfo userInfo) {
        //article.setUserId(1L);
        //articleService.saveArticle(article);
        Map<String, Object> ret = new HashMap<>();
        ret.put("id", userInfo.getUid());
        Map<String, Object> response = WebResponse.getSuccessResponse(ret);
        return response;
    }

    @RequestMapping(value = "/{userId}", method = GET, produces = "application/json")
    public Map<String, Object> getUser(@PathVariable String userId) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("id", userId);
        Map<String, Object> response = WebResponse.getSuccessResponse(ret);
        return response;
    }

    //ModelAttribute RequestBody
    @RequestMapping(value = "/{userId}", method = PUT, produces = "application/json")
    public Map<String, Object> getUser2(@PathVariable String userId, @RequestBody UserInfo userInfo) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("name", userInfo.getLoginName());
        Map<String, Object> response = WebResponse.getSuccessResponse(ret);
        return response;
    }
}
