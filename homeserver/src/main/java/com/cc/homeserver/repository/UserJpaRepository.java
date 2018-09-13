package com.cc.homeserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.cc.homeserver.entity.UserInfo;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserJpaRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByLoginName(String name);
}
