package com.cc.homeserver.service;

import com.cc.homeserver.entity.EnumTypeUtils;
import com.cc.homeserver.entity.FamilyImage;
import com.cc.homeserver.entity.UserFamily;
import com.cc.homeserver.entity.UserInfo;

import java.util.Set;

public interface IImageService {
    public Set<FamilyImage> getAllImages(UserInfo userInfo);

    public void addFamilyImage(UserInfo userInfo, String picUrl,
                               EnumTypeUtils.ImageStateType type, UserFamily group);
}
