package com.cc.springdemo.service.impl;

import java.util.List;

import com.cc.springdemo.entity.User;
import com.cc.springdemo.repository.UserJpaRepository;
import com.cc.springdemo.service.IUserService;
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

    public List<User> findAll()
    {
        return userRepository.findAll();
    }

    public List<User> findByName(String name)
    {
        List<User> userList1 = userRepository.findByName1(name);
        List<User> userList2 = userRepository.findByName2(name);
        List<User> userList3 = userRepository.findByNameAndAddress(name, "3");
        System.out.println("userList1:" + userList1);
        System.out.println("userList2:" + userList2);
        System.out.println("userList3:" + userList3);
        return userRepository.findByName(name);
    }

    public void saveUser(User book)
    {
        userRepository.save(book);
    }

    @Cacheable("users")
    public User findOne(long id)
    {
        System.out.println("Cached Pages");
        return null; //userRepository.findOne(id);
    }

    public void delete(long id)
    {
        //userRepository.delete(id);
    }
}
