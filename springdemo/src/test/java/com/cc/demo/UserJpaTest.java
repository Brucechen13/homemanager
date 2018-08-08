package com.cc.demo;

import com.cc.springdemo.SpringBootDemo21Application;
import com.cc.springdemo.entity.UserInfo;
import com.cc.springdemo.repository.UserJpaRepository;
import com.cc.springdemo.utils.ShiroUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootDemo21Application.class)
public class UserJpaTest {

    @Autowired
    private UserJpaRepository userRepository;

    @Test
    public void test1() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("chenchi");
        userInfo.setLoginName("1821059");
        String salt = ShiroUtils.generateSalt(20);
        String password = ShiroUtils.encryptPassword("SHA-256", "test", salt, 16);
        userInfo.setSalt(salt);
        userInfo.setPassword(password);
        userRepository.save(userInfo);
        System.out.println("save user");
    }


    @Test
    public void test2() {
        System.out.println("test2");
    }
}

