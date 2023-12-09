package com.example.application.service;

import com.example.application.configuration.BaseTestClass;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.UserPersonalInfoEntity;
import com.example.repository.UserPersonInfoRepository;
import com.example.service.UserPersonalInfoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

public class UserPersonalInfoServiceTests extends BaseTestClass {
    @Autowired
    private UserPersonalInfoService personalInfoService;

    @Autowired
    private UserPersonInfoRepository personInfoRepository;

    private UserPersonalInfoEntity defaultPersonalInfo;

    @BeforeEach
    @AfterEach
    void init() {
        personInfoRepository.deleteAll();

        defaultPersonalInfo = new UserPersonalInfoEntity();
        defaultPersonalInfo.setId(1L);
        defaultPersonalInfo.setCity("city");
        defaultPersonalInfo.setGender("gender");
        defaultPersonalInfo.setTelephone("telephone");
        defaultPersonalInfo.setRealName("real name");
        defaultPersonalInfo.setCourse(0L);
        defaultPersonalInfo.setUniversity("university");
        defaultPersonalInfo.setAvatarBytes(new byte[]{});
        defaultPersonalInfo.setAvatarContentType("type");
    }

    @Test
    public void createOnlyOneInfoTest() {
        personalInfoService.createEmpty(1L);

        var infoCount = personInfoRepository.count();

        Assertions.assertEquals(1, infoCount);
    }

    @Test
    public void createCorrectInfoTest() {
        var id = 1L;
        var createInfo = personalInfoService.createEmpty(id);
        var repositoryInfo = personInfoRepository.findById(id).get();

        defaultPersonalInfo = new UserPersonalInfoEntity();
        defaultPersonalInfo.setId(id);

        Assertions.assertEquals(defaultPersonalInfo, repositoryInfo);
        Assertions.assertEquals(defaultPersonalInfo, createInfo);
    }

    @Test
    public void createExistIdInfoTest() {
        var id = 1L;
        var createInfo = personalInfoService.createEmpty(id);
        var createInfo2 = personalInfoService.createEmpty(id);

        defaultPersonalInfo = new UserPersonalInfoEntity();
        defaultPersonalInfo.setId(id);

        Assertions.assertEquals(defaultPersonalInfo, createInfo2);
        Assertions.assertEquals(defaultPersonalInfo, createInfo);
    }

    @Test
    public void createIncorrectIdTest() {
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> personalInfoService.createEmpty(-1L));
    }

    @Test
    public void updateFullInfoCorrectTest() {
        personalInfoService.createEmpty(1L);

        personalInfoService.updateUserInfo(defaultPersonalInfo);

        var info = personalInfoService.getById(1L);

        Assertions.assertEquals(info, defaultPersonalInfo);
    }

    @Test
    public void updateFullInfoIncorrectIdTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> personalInfoService.updateUserInfo(defaultPersonalInfo));
    }

    @Test
    public void updateWithoutAvatarCorrectTest() {
        personalInfoService.createEmpty(1L);

        personalInfoService.updateUserInfoWithoutAvatar(defaultPersonalInfo);

        var info = personalInfoService.getById(1L);

        defaultPersonalInfo.setAvatarBytes(null);
        defaultPersonalInfo.setAvatarContentType(null);
        Assertions.assertEquals(info, defaultPersonalInfo);
    }

    @Test
    public void updateWithoutAvatarIncorrectIdTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> personalInfoService.updateUserInfoWithoutAvatar(defaultPersonalInfo));
        Assertions.assertThrows(EntityNotFoundException.class, () -> personalInfoService.updateUserInfoWithoutAvatar(defaultPersonalInfo));
    }

    @Test
    public void getExistIdTest() {
        var id = personalInfoService.createEmpty(1L).getId();

        var info = personalInfoService.getById(id);

        Assertions.assertNotNull(info);
    }

    @Test
    public void getById_CreateInfoForExistUserTest() {
        var id = 1L;

        Assertions.assertNull(personInfoRepository.findById(id).orElse(null));
        Assertions.assertDoesNotThrow(() -> personalInfoService.getById(id));
        var repositoryInfo = personInfoRepository.findById(id).get();

        defaultPersonalInfo = new UserPersonalInfoEntity();
        defaultPersonalInfo.setId(id);

        Assertions.assertEquals(defaultPersonalInfo, repositoryInfo);
    }

    @Test
    public void getNotExistUserIdTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> personalInfoService.getById(2L));
    }

    @Test
    public void isExistTrueTest() {
        var id = personalInfoService.createEmpty(1L).getId();

        var isExist = personalInfoService.isUserExist(id);

        Assertions.assertTrue(isExist);
    }

    @Test
    public void isExistFalseTest() {
        var isExist = personalInfoService.isUserExist(1);

        Assertions.assertFalse(isExist);
    }
}
