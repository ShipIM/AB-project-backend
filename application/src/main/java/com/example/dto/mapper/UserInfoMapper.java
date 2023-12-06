package com.example.dto.mapper;

import com.example.dto.userinfo.request.UpdateUserInfo;
import com.example.dto.userinfo.response.ResponseUserInfo;
import com.example.model.entity.UserPersonalInfoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {
    ResponseUserInfo mapToResponseUserInfo(UserPersonalInfoEntity userPersonInfo);

    UserPersonalInfoEntity mapToUserInfoEntity(UpdateUserInfo userPersonInfo);
}
