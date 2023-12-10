package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.UserPersonalInfoEntity;
import com.example.repository.UserPersonInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPersonalInfoService {
    private final UserPersonInfoRepository userPersonInfoRepository;
    private final UserService userRepository;

    @Transactional
    public UserPersonalInfoEntity getById(long id) {
        if (!isUserExist(id) && userRepository.isUserExists(id)) {
            return createEmpty(id);
        }

        return userPersonInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким идентификатором не существует"));
    }

    @Transactional
    public UserPersonalInfoEntity createEmpty(long id) {
        if (!isUserExist(id)) {
            userPersonInfoRepository.createEmpty(id);
        }

        return getById(id);
    }

    public UserPersonalInfoEntity updateUserInfo(UserPersonalInfoEntity userInfo) {
        var oldEntity = userPersonInfoRepository.findById(userInfo.getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким идентификатором не существует"));
        oldEntity.updateEntity(userInfo);

        return userPersonInfoRepository.save(oldEntity);
    }

    public UserPersonalInfoEntity updateUserInfoWithoutAvatar(UserPersonalInfoEntity userInfo) {
        var oldEntity = userPersonInfoRepository.findById(userInfo.getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким идентификатором не существует"));
        oldEntity.updateEntityWithoutAvatar(userInfo);

        return userPersonInfoRepository.save(oldEntity);
    }

    public boolean isUserExist(long id) {
        return userPersonInfoRepository.existsById(id);
    }
}
