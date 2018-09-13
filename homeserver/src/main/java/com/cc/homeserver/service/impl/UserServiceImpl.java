package com.cc.homeserver.service.impl;

import java.util.List;

import com.cc.homeserver.entity.UserInfo;
import com.cc.homeserver.repository.UserJpaRepository;
import com.cc.homeserver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService
{
    @Autowired
    private UserJpaRepository userRepository;

    public List<UserInfo> findAll()
    {
        return userRepository.findAll();
    }

    public UserInfo findByLoginName(String name)
    {
        UserInfo userInfo = userRepository.findByLoginName(name);
        System.out.println("userList3:" + userInfo);
        return userInfo;
    }

    public void saveUser(UserInfo userInfo)
    {
        userRepository.save(userInfo);
    }

    @Cacheable("users")
    public UserInfo findOne(long id)
    {
        System.out.println("Cached Pages");
        return null; //userRepository.findOne(id);
    }

    public void delete(long id)
    {
        //userRepository.delete(id);
    }
}
