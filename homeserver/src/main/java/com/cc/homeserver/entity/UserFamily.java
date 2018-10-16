package com.cc.homeserver.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude={"userInfos", "createUser"})
@ToString(exclude={"userInfos", "createUser"})
@Accessors(chain = true)
@Entity
@Table(name = "user_family")
public class UserFamily implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "家庭组名称不能为空")
    @Column(nullable=false)
    private String familyName;


    @NotBlank(message = "家庭组创建者不能为空")
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="create_id")
    private UserInfo createUser;

    @ManyToMany
    @JoinTable(name = "tmp_user_family", joinColumns = { @JoinColumn(name = "familyId") }, inverseJoinColumns ={@JoinColumn(name = "uid") })
    private Set<UserInfo> userInfos;// 家庭组所有成员

    @OneToMany
    @JoinColumn(name="family_id")
    private Set<FamilyImage> images;

}
