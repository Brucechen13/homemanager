package com.cc.demo;

import com.cc.homeserver.App;
import com.cc.homeserver.entity.EnumTypeUtils;
import com.cc.homeserver.entity.SysRole;
import com.cc.homeserver.entity.UserInfo;
import com.cc.homeserver.repository.UserJpaRepository;
import com.cc.homeserver.repository.UserRoleJpaRepository;
import com.cc.homeserver.service.IUserService;
import com.cc.homeserver.utils.ShiroUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserJpaTest {

    @Autowired
    private IUserService service;

    @Test
    public void test00() {
        System.out.println("test2");
    }

    @Test
    public void test01(){
        UserInfo userInfo = service.findByLoginName("1821059");
        if(userInfo != null){
            System.out.println("search user");
            service.delete(userInfo);
        }
    }

    @Test
    public void test02() {
        service.saveUser("1821059", "test1", "test");
        System.out.println("save user");
    }

    @Test
    public void test03(){
        boolean isLogin  = service.loginUser("1821059", "test");
        System.out.println(isLogin);
        assert isLogin;
        isLogin  = service.loginUser("1821059", "test1");
        assert !isLogin;
        isLogin  = service.loginUser("18210592", "test2");
        assert !isLogin;
    }


}
