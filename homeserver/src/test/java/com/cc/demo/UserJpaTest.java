package com.cc.demo;

import com.cc.homeserver.App;
import com.cc.homeserver.entity.EnumTypeUtils;
import com.cc.homeserver.entity.SysRole;
import com.cc.homeserver.entity.UserInfo;
import com.cc.homeserver.repository.UserJpaRepository;
import com.cc.homeserver.repository.UserRoleJpaRepository;
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
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@FixMethodOrder(MethodSorters.JVM)
public class UserJpaTest {

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private UserRoleJpaRepository userRoleRepository;

    @Test
    public void testSearchAndDelete(){
        UserInfo userInfo = userRepository.findByLoginName("1821059");
        if(userInfo != null){
            System.out.println("search user");
            userRepository.delete(userInfo);
        }
    }

    @Test
    public void test1() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("test1");
        userInfo.setLoginName("1821059");
        userInfo.setState(EnumTypeUtils.StateType.NORMAL);
        String salt = ShiroUtils.generateSalt(20);
        String password = ShiroUtils.encryptPassword("SHA-256", "test", salt, 16);
        userInfo.setSalt(salt);
        userInfo.setPassword(password);
        SysRole role = userRoleRepository.findByRole("user");
        if(userInfo.getRoleList() == null){
            List<SysRole> roles = new ArrayList<>();
            roles.add(role);
            userInfo.setRoleList(roles);
        }else{
            userInfo.getRoleList().add(role);
        }
        userRepository.save(userInfo);
        System.out.println("save user");
    }

    @Test
    public void test_fetch(){
        UserInfo userInfo = userRepository.findByLoginName("1821059");
        SysRole role = userRoleRepository.findByRole("user");
        if(userInfo.getRoleList()!=null && userInfo.getRoleList().size() > 0){
            SysRole role1 = userInfo.getRoleList().get(0);
            SysRole role2 = userRoleRepository.findUsersByRole(role1.getRole());
            System.out.println(userInfo.getRoleList().size() + " " + role.getRole()
              + " " + role1.getUserInfos().size());
        }
    }


    @Test
    public void test2() {
        System.out.println("test2");
    }
}
