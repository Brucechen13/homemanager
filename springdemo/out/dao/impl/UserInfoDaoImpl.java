package com.cc.springdemo.dao.impl;

import com.cc.springdemo.entity.UserInfo;
import com.cc.springdemo.dao.UserInfoDao;
import com.cc.springdemo.dao.impl.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDaoImpl implements UserInfoDao {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getById(Long id) {
        return this.userInfoMapper.selectByPrimaryKey(id);
    }
}
