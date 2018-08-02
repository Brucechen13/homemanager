package com.cc.springdemo.dao;

import com.cc.springdemo.entity.UserInfo;

public interface UserInfoDao {
    UserInfo getById(Long id);
}
