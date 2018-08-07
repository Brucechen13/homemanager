package com.cc.springdemo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.cc.springdemo.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    List<User> findByNameAndAddress(String name, String address);

    @Query(value = "from User u where u.name=:name")
    List<User> findByName1(@Param("name") String name);

    @Query(value = "select * from #{#entityName} u where u.name=?1", nativeQuery = true)
    List<User> findByName2(String name);

    List<User> findByName(String name);
}
