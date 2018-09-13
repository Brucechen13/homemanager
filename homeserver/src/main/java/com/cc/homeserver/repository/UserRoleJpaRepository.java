package com.cc.homeserver.repository;

import com.cc.homeserver.entity.SysRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleJpaRepository extends JpaRepository<SysRole, Long> {
    SysRole findByRole(String name);


    @EntityGraph(attributePaths = { "userInfos" })
    SysRole findUsersByRole(String name);
}
