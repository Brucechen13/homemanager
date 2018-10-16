package com.cc.homeserver.service.impl;

import com.cc.homeserver.entity.EnumTypeUtils;
import com.cc.homeserver.entity.FamilyImage;
import com.cc.homeserver.entity.UserFamily;
import com.cc.homeserver.entity.UserInfo;
import com.cc.homeserver.repository.FamilyImageJpaRepository;
import com.cc.homeserver.repository.UserJpaRepository;
import com.cc.homeserver.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class ImageServiceImpl implements IImageService {

    @Autowired
    private FamilyImageJpaRepository imageJpaRepository;

    @Override
    public Set<FamilyImage> getAllImages(UserInfo userInfo) {
        return null;
    }

    @Override
    public void addFamilyImage(UserInfo userInfo, String picUrl,
                               EnumTypeUtils.ImageStateType type, UserFamily group) {
        FamilyImage familyImage = new FamilyImage();
        familyImage.setSender(userInfo);
        familyImage.setPicUrl(picUrl);
        if(type == EnumTypeUtils.ImageStateType.PRIVATEGROUP){
            familyImage.setGroup(group);
        }
        familyImage.setType(type);
        imageJpaRepository.save(familyImage);
    }
}
