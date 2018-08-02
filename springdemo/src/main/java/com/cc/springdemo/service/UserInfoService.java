package com.cc.springdemo.service;

import com.cc.springdemo.dao.UserInfoDao;
import com.cc.springdemo.entity.UserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserInfoService {

    @Autowired
    private UserInfoDao dao;


    public UserInfo getById(Long id) {
        UserInfo vo = new UserInfo();
        UserInfo record = dao.getById(id);
        BeanUtils.copyProperties(record, vo);
        return vo;
    }
}
