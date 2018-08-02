package com.cc.springdemo.dao.impl.mapper;

import com.cc.springdemo.entity.UserInfo;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    UserInfo selectByPrimaryKey(Long id);
}
