package com.cc.homeserver.service;

import com.cc.homeserver.entity.UserInfo;

import java.util.List;

public interface IUserService {

    public List<UserInfo> findAll();

    public void saveUser(String userName, String nickName, String password);

    public boolean loginUser(String userName, String password);

    public void delete(UserInfo userInfo);

    public UserInfo findByLoginName(String name);
}
