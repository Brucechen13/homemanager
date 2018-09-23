package com.cc.homeserver.config;

import com.cc.homeserver.entity.SysPermission;
import com.cc.homeserver.entity.SysRole;
import com.cc.homeserver.entity.UserInfo;
import com.cc.homeserver.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 这个类是参照JDBCRealm写的，主要是自定义了如何查询用户信息，如何查询用户的角色和权限，如何校验密码等逻辑
 */
public class CustomRealm extends AuthorizingRealm {


    @Autowired
    private IUserService service;


    //告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
    {
        //设置用于匹配密码的CredentialsMatcher
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        hashMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        //hashMatcher.setStoredCredentialsHexEncoded(false);
        hashMatcher.setHashIterations(16);
        this.setCredentialsMatcher(hashMatcher);
    }


    //定义如何获取用户的角色和权限的逻辑，给shiro做权限判断
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        //String username = (String)getAvailablePrincipal(principals);

        UserInfo user = (UserInfo)getAvailablePrincipal(principals);//service.findByLoginName(username);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>();
        Set<String> perms = new HashSet<>();
        for(SysRole role : user.getRoleList()){
            roles.add(role.getRole());
            for (SysPermission perm:
                 role.getPermissions()) {
                perms.add(perm.getName());
            }
        }
        System.out.println("获取角色信息："+roles.size());
        System.out.println("获取权限信息："+perms.size());
        info.setRoles(roles);
        info.setStringPermissions(perms);
        return info;
    }

    //定义如何获取用户信息的业务逻辑，给shiro做登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        UserInfo userDB = service.findByLoginName(username);


        if (userDB == null) {
            throw new UnknownAccountException("No account found for admin [" + username + "]");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDB,
                userDB.getPassword(), getName());//.getLoginName()
        if (userDB.getSalt() != null) {
            info.setCredentialsSalt(ByteSource.Util.bytes(userDB.getSalt()));
        }
        return info;
    }
}
