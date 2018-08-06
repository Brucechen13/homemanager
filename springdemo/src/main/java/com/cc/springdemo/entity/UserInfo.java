package com.cc.springdemo.entity;

import com.cc.springdemo.entity.shiro.SysRole;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 实体类
 *
 */
@Data
@Accessors(chain = true)
@Entity
public class UserInfo implements Serializable {

    @Id@GeneratedValue
    private int uid;
    @NotBlank(message = "用户名不能为空")

    private String userName;
    private String password;

    @Column(unique=true, nullable=true)
    private String loginName;
    private String salt;
    private Date gmtCreate;
    private Date gmtModified;
    private byte state;//用户状态

    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "SysUserRole", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns ={@JoinColumn(name = "roleId") })
    private List<SysRole> roleList;// 一个用户具有多个角色
}
