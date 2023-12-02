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
    private String realName = "";

    private String gender= "";

    private String city= "";

    private String telephone= "";

    private Long course= 0L;

    private String university= "";

    public static void UpdateEntity(UserPersonalInfoEntity oldEntity, UserPersonalInfoEntity newEntity) {
        oldEntity.setRealName(newEntity.getRealName());
        oldEntity.setGender(newEntity.getGender());
        oldEntity.setCity(newEntity.getCity());
        oldEntity.setTelephone(newEntity.getTelephone());
        oldEntity.setCourse(newEntity.getCourse());
        oldEntity.setUniversity(newEntity.getUniversity());
    }
}
