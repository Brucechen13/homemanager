package com.cc.homeserver.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import javax.persistence.*;

/**
 * 角色:家长、家人
 */

@Data
@Accessors(chain = true)
@Entity
@NamedEntityGraph(name = "SysRole.lazy", attributeNodes = {@NamedAttributeNode("userInfos")})
@Table(name = "sys_role")
public class SysRole {
    @Id
    @GeneratedValue
    private Integer id; // 编号
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    private String description; // 角色描述,UI界面显示使用
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户

    //角色 -- 权限关系：多对多关系;
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name="sys_role_permission",joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="permissionId")})
    private List<SysPermission> permissions;

    // 用户 - 角色关系定义;
    @ManyToMany
    @JoinTable(name="sys_user_role",joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="id")})
    private List<UserInfo> userInfos;// 一个角色对应多个用户

    // 省略 get set 方法
}
