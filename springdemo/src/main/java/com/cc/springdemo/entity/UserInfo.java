package com.cc.springdemo.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 *
 */
@Data
@Accessors(chain = true)
public class UserInfo implements Serializable {
    private int id;
    @NotBlank(message = "用户名不能为空")
    private String userName;
    private Date gmtCreate;
    private Date gmtModified;
}
