package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.UserPersonalInfoEntity;
import com.example.repository.UserPersonInfoRepository;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPersonalInfoService {
    private final UserPersonInfoRepository userPersonInfoRepository;
    private final UserService userRepository;

    @Transactional
    public UserPersonalInfoEntity getUserInfo(long id) {
        if (!isUserExist(id)) {
            if (userRepository.isUserExists(id)) {
                return createEmpty(id);
            }

            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }

        return userPersonInfoRepository.findById(id).get();
    }

    @Transactional
    public UserPersonalInfoEntity createEmpty(long id) {
        if (!isUserExist(id)) {
            userPersonInfoRepository.createEmpty(id);
        }

        return getUserInfo(id);
    }

    public UserPersonalInfoEntity updateUserInfo(UserPersonalInfoEntity userInfo) {
        if (!isUserExist(userInfo.getId())) {
            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }

        var oldEntity = userPersonInfoRepository.findById(userInfo.getId()).get();
        oldEntity.UpdateEntity(userInfo);

        return userPersonInfoRepository.save(oldEntity);
    }

    public UserPersonalInfoEntity updateUserInfoWithoutAvatar(UserPersonalInfoEntity userInfo) {
        if (!isUserExist(userInfo.getId())) {
            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }

        var oldEntity = userPersonInfoRepository.findById(userInfo.getId()).get();
        oldEntity.UpdateEntityWithoutAvatar(userInfo);

        return userPersonInfoRepository.save(oldEntity);
    }

    public boolean isUserExist(long id) {
        return userPersonInfoRepository.existsById(id);
    }
}
