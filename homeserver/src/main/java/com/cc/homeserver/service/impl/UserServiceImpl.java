package com.cc.homeserver.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cc.homeserver.entity.EnumTypeUtils;
import com.cc.homeserver.entity.SysRole;
import com.cc.homeserver.entity.UserInfo;
import com.cc.homeserver.repository.UserJpaRepository;
import com.cc.homeserver.repository.UserRoleJpaRepository;
import com.cc.homeserver.service.IUserService;
import com.cc.homeserver.utils.ShiroUtils;
import org.apache.catalina.User;
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
    @Autowired
    private UserRoleJpaRepository userRoleRepository;

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

    public void saveUser(String userName, String nickName, String password)
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginName(userName);
        userInfo.setNickName(nickName);
        userInfo.setState(EnumTypeUtils.StateType.NORMAL);
        String salt = ShiroUtils.generateSalt(20);
        password = ShiroUtils.encryptPassword(password, salt);
        userInfo.setSalt(salt);
        userInfo.setPassword(password);
        SysRole role = userRoleRepository.findByRole("user");
        Set<SysRole> roles = new HashSet<>();
        roles.add(role);
        userInfo.setRoleList(roles);
        userRepository.save(userInfo);
    }

    @Override
    public boolean loginUser(String userName, String password) {
        UserInfo userInfo = userRepository.findByLoginName(userName);
        if(userInfo != null){
            if(userInfo.getPassword().equals(ShiroUtils.encryptPassword(password, userInfo.getSalt()))){
                return true;
            }
        }
        return false;
    }

    public void delete(UserInfo userInfo)
    {
        userRepository.delete(userInfo);
    }
}
