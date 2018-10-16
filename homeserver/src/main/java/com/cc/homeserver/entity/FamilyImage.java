package com.cc.homeserver.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(exclude={"sender", "group"})
@ToString(exclude={"sender", "group"})
@Accessors(chain = true)
@Entity
@Table(name = "family_image")
public class FamilyImage {

    @Id@GeneratedValue
    private int id;

    @Column(nullable=false)
    private String picUrl;

    @Column(name = "tmModified", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp uploadTime;

    @NotBlank(message = "图片上传者不能为空")
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="create_id")
    private UserInfo sender;

    @Enumerated(EnumType.STRING)//枚举字符串
    @Column(nullable=false, columnDefinition="varchar(32) default 'PUBLIC'")
    private EnumTypeUtils.ImageStateType type;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="group_id")
    private UserFamily group;

}
