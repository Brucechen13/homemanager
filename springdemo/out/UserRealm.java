package com.cc.springdemo.config;

import com.cc.springdemo.entity.UserInfo;

import java.util.HashSet;
import java.util.Set;

public class UserRealm extends AbstractUserRealm {

    @Override
    public UserRolesAndPermissions doGetGroupAuthorizationInfo(UserInfo userInfo) {
        Set<String> userRoles = new HashSet<>();
        Set<String> userPermissions = new HashSet<>();
        //TODO 获取当前用户下拥有的所有角色列表,及权限
        return new UserRolesAndPermissions(userRoles, userPermissions);
    }

    @Override
    public UserRolesAndPermissions doGetRoleAuthorizationInfo(UserInfo userInfo) {
        Set<String> userRoles = new HashSet<>();
        Set<String> userPermissions = new HashSet<>();
        //TODO 获取当前用户下拥有的所有角色列表,及权限
        return new UserRolesAndPermissions(userRoles, userPermissions);
    }
}
