package com.cc.homeserver.controller;


import com.cc.homeserver.entity.UserInfo;
import com.cc.homeserver.service.IUserService;
import com.cc.homeserver.utils.JsonResponse;
import com.cc.homeserver.utils.ShiroUtils;
import com.cc.homeserver.utils.WebResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private static final transient Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService service;

    @RequestMapping(value = "/update")
    @ResponseBody
    public String updateAdminUserPassword(String newPassword) {
        // 从shiro的session中取activeUser
        Subject subject = SecurityUtils.getSubject();
        // 取身份信息
        UserInfo adminUser = (UserInfo) subject.getPrincipal();
        // 生成salt,随机生成
        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        String salt = secureRandomNumberGenerator.nextBytes().toHex();
        String newMd5Password = ShiroUtils.encryptPassword("MD5", newPassword, salt, 3);
        // 设置新密码
        adminUser.setPassword(newMd5Password);
        // 设置盐
        adminUser.setSalt(salt);
        //service.updateAdminUserPassword(adminUser);
        return newPassword;
    }

    @RequestMapping(value = "/save", method = POST, produces = "application/json")
    public JsonResponse saveUser(String userName, String nickName, String password) {
        service.saveUser(userName, nickName, password);
        return WebResponse.getSuccessResponse("注册成功");
    }

    @RequestMapping(value = "/login", method = POST, produces = "application/json")
    public JsonResponse loginUser(String userName, String password){
        // 安全操作
        Subject currentUser = SecurityUtils.getSubject();

        // 在应用的当前会话中设置属性
        Session session =  currentUser.getSession();
        session.setAttribute("key","value");

        //当前我们的用户是匿名的用户，我们尝试进行登录，
        if (!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

            //this is all you have to do to support 'remember me' (no config - built in!):
            token.setRememberMe(true);

            //尝试进行登录用户，如果登录失败了，我们进行一些处理

            try{
                currentUser.login(token);

                // Session session = currentUser.getSession(true);

                //主机
                System.out.println("host:"+session.getHost());
                //session超时时间
                session.setTimeout(1500000);
                //属性参数值
                session.setAttribute("name", currentUser.getPrincipal());

                //当我们获登录用户之后
                logger.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");


                // 查看用户是否有指定的角色
                if ( currentUser.hasRole( "client" ) ) {
                    logger.info("Look is in your role" );
                } else {
                    logger.info( "Look isnot in your role" );
                }

                // 查看用户是否有某个权限
                if ( currentUser.isPermitted( "look:desk" ) ) {
                    logger.info("You can look.  Use it wisely.");
                } else {
                    logger.info("Sorry, you can't look.");
                }

                //登出

                // currentUser.logout();
                return WebResponse.getSuccessResponse("登录成功");

            }
            catch ( UnknownAccountException uae ) {
                //账户不存在的操作
                logger.info("账户不存在的操作");
            } catch ( IncorrectCredentialsException ice ) {
                //密码不正确
                logger.info("密码不正确");
            } catch ( LockedAccountException lae ) {
                //用户被锁定了
                logger.info("用户被锁定了");
            } catch ( AuthenticationException ae ) {
                //无法判断的情形
                logger.info("无法判断的情形");
            }
        }
        return WebResponse.getFailResponse("登录失败");
    }

    @RequestMapping(value = "/{userName}", method = GET, produces = "application/json")
    public JsonResponse getUser(@PathVariable String userName) {
        UserInfo userInfo  = service.findByLoginName(userName);
        Map<String, Object> ret = new HashMap<>();
        ret.put("id", userInfo.getId());
        ret.put("userName", userInfo.getLoginName());
        ret.put("nickName", userInfo.getNickName());
        ret.put("password", userInfo.getPassword());
        return WebResponse.getSuccessResponse("用户信息", ret);
    }
}
