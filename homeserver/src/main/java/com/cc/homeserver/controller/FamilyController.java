package com.cc.homeserver.controller;

import com.cc.homeserver.entity.OutFamilyInfo;
import com.cc.homeserver.entity.OutUserInfo;
import com.cc.homeserver.entity.UserFamily;
import com.cc.homeserver.entity.UserInfo;
import com.cc.homeserver.service.IUserService;
import com.cc.homeserver.utils.JsonResponse;
import com.cc.homeserver.utils.WebResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/family")
public class FamilyController {
    private static final transient Logger logger = LoggerFactory.getLogger(FamilyController.class);

    @Autowired
    private IUserService service;

    @RequestMapping(value = "/addUser", method = POST, produces = "application/json")
    public JsonResponse addUser(String familyName, String userName) {
        Subject subject = SecurityUtils.getSubject();
        // 取身份信息
        UserInfo adminUser = (UserInfo) subject.getPrincipal();
        UserInfo addUser = (UserInfo)service.findByLoginName(userName);
        service.addFamilyUser(adminUser, familyName, addUser);
        return WebResponse.getSuccessResponse("申请添加用户成功");
    }

    @RequestMapping(value = "/addFamily", method = POST, produces = "application/json")
    public JsonResponse addFamily(String familyName) {
        Subject subject = SecurityUtils.getSubject();
        // 取身份信息
        UserInfo adminUser = (UserInfo) subject.getPrincipal();
        adminUser = service.findByLoginName(adminUser.getLoginName());
        for (OutFamilyInfo family:
             service.getAllCreateFamilys(adminUser)) {
            if(family.getFamilyName().equals(familyName))
                throw new DuplicateKeyException(familyName + "重复");
        }
        service.addFamily(adminUser, familyName);

        return WebResponse.getSuccessResponse("申请加入家庭组成功");
    }

    @RequestMapping(value = "/members", method = POST, produces = "application/json")
    public JsonResponse members(String familyName) {
        Subject subject = SecurityUtils.getSubject();
        // 取身份信息
        UserInfo adminUser = (UserInfo) subject.getPrincipal();
        adminUser = service.findByLoginName(adminUser.getLoginName());
        Set<OutUserInfo> infos = service.getFamilyUsers(adminUser, familyName);

        return WebResponse.getSuccessResponse("获得家庭组成员", infos);
    }

    @RequestMapping(value = "/familys", method = POST, produces = "application/json")
    public JsonResponse familys() {
        Subject subject = SecurityUtils.getSubject();
        // 取身份信息
        UserInfo adminUser = (UserInfo) subject.getPrincipal();
        adminUser = service.findByLoginName(adminUser.getLoginName());

        Set<OutFamilyInfo> familyNames = service.getAllFamilys(adminUser);

        return WebResponse.getSuccessResponse("获得全部家庭组", familyNames);
    }

}
