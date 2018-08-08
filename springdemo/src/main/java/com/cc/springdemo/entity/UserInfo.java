package com.cc.springdemo.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 实体类
 *
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {

    @Id@GeneratedValue
    private int id;

    @NotBlank(message = "用户名不能为空")
    @Column(unique=true, nullable=false)
    private String loginName;
    @Column(nullable=false)
    private String userName;

    @Column(nullable=false)
    private String salt;
    @Column(nullable=false)
    private String password;

    @Enumerated(EnumType.STRING)//枚举字符串
    @Column(nullable=false, columnDefinition="varchar(32) default 'NORMAL'")
    private StateType state;//用户状态

    @Column(name = "tmCreate", updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    //@Temporal(TemporalType.TIMESTAMP)
    private Timestamp gmtCreate;

    @Column(name = "tmModified", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    //@Temporal(TemporalType.TIMESTAMP)
    private Timestamp gmtModified;


    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns ={@JoinColumn(name = "roleId") })
    private List<SysRole> roleList;// 一个用户具有多个角色
}

enum StateType {
    NORMAL("正常"),
    LOCKED("禁用"),;
    private String name;

    StateType(String name){
        this.name = name;
    }
}



