package com.cc.homeserver.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class OutFamilyInfo {
    private String familyName;
    private OutUserInfo createUser;
    private Set<OutUserInfo> users;


    public void parseFromFamilyInfo(UserFamily family){
        this.setFamilyName(family.getFamilyName());
        OutUserInfo userInfo = new OutUserInfo();
        userInfo.parseFromUserInfo(family.getCreateUser());
        Set<OutUserInfo> users = new HashSet<>();
        for (UserInfo user:
             family.getUserInfos()) {
            OutUserInfo outUserInfo = new OutUserInfo();
            outUserInfo.parseFromUserInfo(user);
            users.add(outUserInfo);
        }
        this.setUsers(users);
        this.setCreateUser(userInfo);
    }
}
