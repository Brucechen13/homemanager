package com.cc.homeserver.service;

import com.cc.homeserver.entity.OutFamilyInfo;
import com.cc.homeserver.entity.OutUserInfo;
import com.cc.homeserver.entity.UserFamily;
import com.cc.homeserver.entity.UserInfo;

import java.util.List;
import java.util.Set;

public interface IUserService {

    public void saveUser(String userName, String nickName, String password);

    public boolean loginUser(String userName, String password);

    public void delete(UserInfo userInfo);

    public UserInfo findByLoginName(String name);

    public void addFamily(UserInfo userInfo, String familyName);

    public void addFamilyUser(UserInfo userInfo, String familyName, UserInfo addUser);

    public void removeFamilyUser(UserInfo userInfo, String familyName, UserInfo removeUser);

    public Set<OutUserInfo> getFamilyUsers(UserInfo userInfo, String familyName);

    public Set<OutFamilyInfo> getAllFamilys(UserInfo userInfo);
    public Set<OutFamilyInfo> getAllCreateFamilys(UserInfo userInfo);

    public UserFamily getUserFamily(UserInfo userInfo, String familyName);

}
