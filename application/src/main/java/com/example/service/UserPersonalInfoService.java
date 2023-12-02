package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.UserPersonalInfoEntity;
import com.example.repository.UserPersonInfoRepository;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPersonalInfoService {
    private final UserPersonInfoRepository userPersonInfoRepository;
    private final UserRepository userRepository;

    public UserPersonalInfoEntity getUserInfo(long id) {
        if (!isUserExist(id)) {
            if (userRepository.existsById(id)) {
                return createEmpty(id);
            }

            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }

        return userPersonInfoRepository.findById(id).get();
    }

    public UserPersonalInfoEntity createEmpty(long id) {
        if (isUserExist(id)) {
            return getUserInfo(id);
        }

        var userInfo = new UserPersonalInfoEntity();
        userInfo.setId(id);

        return userPersonInfoRepository.save(userInfo);
    }

    public UserPersonalInfoEntity updateUserInfo(UserPersonalInfoEntity userInfo) {
        if (!isUserExist(userInfo.getId())) {
            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }

        var oldEntity = userPersonInfoRepository.findById(userInfo.getId()).get();
        UserPersonalInfoEntity.UpdateEntity(oldEntity, userInfo);

        return userPersonInfoRepository.save(oldEntity);
    }

    public boolean isUserExist(long id) {
        return userPersonInfoRepository.existsById(id);
    }
}
