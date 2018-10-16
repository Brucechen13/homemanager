package com.cc.homeserver.service;

import com.cc.homeserver.entity.FamilyImage;
import com.cc.homeserver.entity.UserInfo;

import java.util.Set;

public interface IImageService {
    public Set<FamilyImage> getAllImages(UserInfo userInfo);
}
