package com.cc.homeserver.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "user_family")
public class UserFamily {
    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "家庭组不能为空")
    @Column(nullable=false)
    private String familyName;

    @ManyToMany
    @JoinTable(name = "tmp_user_family", joinColumns = { @JoinColumn(name = "familyId") }, inverseJoinColumns ={@JoinColumn(name = "id") })
    private Set<UserInfo> userInfos;// 一个用户具有多个角色
}