package com.cc.homeserver.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cc.homeserver.entity.*;
import com.cc.homeserver.repository.FamilyJpaRepository;
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
    @Autowired
    private FamilyJpaRepository familyJpaRepository;

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
        userInfo.setFamilyList(new HashSet<>());
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



    @Override
    public void addFamily(UserInfo userInfo, String familyName) {
        UserFamily family = new UserFamily();
        family.setCreateUser(userInfo);
        family.setFamilyName(familyName);
        HashSet<UserInfo> users = new HashSet<>();
        users.add(userInfo);
        family.setUserInfos(users);
        familyJpaRepository.save(family);
    }

    @Override
    public void addFamilyUser(UserInfo userInfo, String familyName, UserInfo addUser) {
        for (UserFamily userFamily:
             userInfo.getFamilyList()) {
            if(userFamily.getFamilyName().equals(familyName)){
                UserFamily family = familyJpaRepository.findById(userFamily.getId());
                family.getUserInfos().add(addUser);
                familyJpaRepository.save(family);
            }
        }
    }

    @Override
    public void removeFamilyUser(UserInfo userInfo, String familyName, UserInfo removeUser) {
        for (UserFamily userFamily:
                userInfo.getFamilyList()) {
            if(userFamily.getFamilyName().equals(familyName)){
                UserFamily family = familyJpaRepository.findById(userFamily.getId());
                if(family.getUserInfos().contains(removeUser)) {
                    family.getUserInfos().remove(removeUser);
                    familyJpaRepository.save(family);
                }
            }
        }
    }

    @Override
    public Set<OutUserInfo> getFamilyUsers(UserInfo userInfo, String familyName) {
        for (UserFamily userFamily:
                userInfo.getFamilyList()) {
            if(userFamily.getFamilyName().equals(familyName)){
                UserFamily family = familyJpaRepository.findById(userFamily.getId());


                Set<OutUserInfo> data = new HashSet<>();
                for (UserInfo info:
                        family.getUserInfos()) {
                    OutUserInfo outUserinfo = new OutUserInfo();
                    outUserinfo.parseFromUserInfo(info);
                    data.add(outUserinfo);
                }

                return data;
            }
        }
        return new HashSet<>();
    }

    @Override
    public Set<OutFamilyInfo> getAllFamilys(UserInfo userInfo) {
        Set<OutFamilyInfo> familyNames = new HashSet<>();
        for (UserFamily family:
                userInfo.getFamilyList()) {
            family = familyJpaRepository.findById(family.getId());
            OutFamilyInfo familyInfo = new OutFamilyInfo();
            familyInfo.parseFromFamilyInfo(family);
            familyNames.add(familyInfo);
        }
        return familyNames;
    }

    @Override
    public Set<OutFamilyInfo> getAllCreateFamilys(UserInfo userInfo) {
        Set<OutFamilyInfo> familyNames = new HashSet<>();
        for (UserFamily family:
                userInfo.getFamilyList()) {
            if(family.getCreateUser().getLoginName().equals(userInfo.getLoginName())) {
                family = familyJpaRepository.findById(family.getId());
                OutFamilyInfo familyInfo = new OutFamilyInfo();
                familyInfo.parseFromFamilyInfo(family);
                familyNames.add(familyInfo);
            }
        }
        return familyNames;
    }
}
