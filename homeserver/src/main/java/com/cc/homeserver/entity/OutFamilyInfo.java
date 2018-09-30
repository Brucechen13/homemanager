package com.cc.homeserver.entity;

import lombok.Data;

@Data
public class OutFamilyInfo {
    private String familyName;
    private OutUserInfo createUser;


    public void parseFromFamilyInfo(UserFamily family){
        this.setFamilyName(family.getFamilyName());
        OutUserInfo userInfo = new OutUserInfo();
        userInfo.parseFromUserInfo(family.getCreateUser());
        this.setCreateUser(userInfo);
    }
}
