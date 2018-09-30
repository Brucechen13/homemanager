package com.cc.homeserver.entity;

import lombok.Data;

@Data
public class OutUserInfo {
    private String loginName;
    private String nickName;

    public void parseFromUserInfo(UserInfo userInfo){
        this.setNickName(userInfo.getNickName());
        this.setLoginName(userInfo.getLoginName());
    }
}
