package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public void UpdateEntity(UserPersonalInfoEntity newEntity) {
        UpdateEntityWithoutAvatar(newEntity);
        this.avatarBytes = newEntity.getAvatarBytes();
        this.avatarContentType = newEntity.getAvatarContentType();
    }

    public void UpdateEntityWithoutAvatar(UserPersonalInfoEntity newEntity) {
        this.realName = newEntity.getRealName();
        this.gender = newEntity.getGender();
        this.city = newEntity.getCity();
        this.telephone = newEntity.getTelephone();
        this.course = newEntity.getCourse();
        this.university = newEntity.getUniversity();
    }
}
