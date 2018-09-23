package com.cc.homeserver.controller;

import com.cc.homeserver.entity.UserInfo;
import com.cc.homeserver.utils.JsonResponse;
import com.cc.homeserver.utils.WebResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/family")
public class FamilyController {
    private static final transient Logger logger = LoggerFactory.getLogger(FamilyController.class);


    @RequestMapping(value = "/create", method = POST, produces = "application/json")
    public JsonResponse saveUser(String familyName) {
        Subject subject = SecurityUtils.getSubject();
        // 取身份信息
        UserInfo adminUser = (UserInfo) subject.getPrincipal();

        return WebResponse.getSuccessResponse("添加家庭组成功");
    }

}
