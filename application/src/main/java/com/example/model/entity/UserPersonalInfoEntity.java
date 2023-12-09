package com.example.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "user_personal_info_jn")
public class UserPersonalInfoEntity {
    @Id
    private Long id;

    @Column(value = "real_name")
    private String realName;

    private String gender;

    private String city;

    private String telephone;

    private Long course;

    private String university;

    @Column(value = "avatar_bytes")
    private byte[] avatarBytes;

    @Column(value = "avatar_content_type")
    private String avatarContentType;

    public void updateEntity(UserPersonalInfoEntity newEntity) {
        updateEntityWithoutAvatar(newEntity);
        this.avatarBytes = newEntity.getAvatarBytes();
        this.avatarContentType = newEntity.getAvatarContentType();
    }

    public void updateEntityWithoutAvatar(UserPersonalInfoEntity newEntity) {
        this.realName = newEntity.getRealName();
        this.gender = newEntity.getGender();
        this.city = newEntity.getCity();
        this.telephone = newEntity.getTelephone();
        this.course = newEntity.getCourse();
        this.university = newEntity.getUniversity();
    }

    public UserPersonalInfoEntity(UserPersonalInfoEntity entity) {
        this.id = entity.getId();
        this.realName = entity.getRealName();
        this.gender = entity.getGender();
        this.city = entity.getCity();
        this.telephone = entity.getTelephone();
        this.course = entity.getCourse();
        this.university = entity.getUniversity();
        this.avatarBytes = entity.getAvatarBytes();
        this.avatarContentType = entity.getAvatarContentType();
    }
}
