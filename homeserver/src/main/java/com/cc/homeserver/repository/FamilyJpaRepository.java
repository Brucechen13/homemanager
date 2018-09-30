package com.cc.homeserver.repository;


import com.cc.homeserver.entity.UserFamily;
import com.cc.homeserver.entity.UserInfo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyJpaRepository extends JpaRepository<UserFamily, Long> {

    @EntityGraph(attributePaths = { "userInfos" })
    UserFamily findById(int id);
}
