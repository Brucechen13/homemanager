package com.cc.homeserver.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

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

    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "tmp_user_family", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns ={@JoinColumn(name = "userId") })
    private List<UserInfo> userInfos;// 一个用户具有多个角色
}
