package com.cc.springdemo.service;

import com.cc.springdemo.entity.UserInfo;

import java.util.List;

public interface IUserService {

    public List<UserInfo> findAll();

    public void saveUser(UserInfo book);

    public UserInfo findOne(long id);

    public void delete(long id);

    public UserInfo findByLoginName(String name);
}
