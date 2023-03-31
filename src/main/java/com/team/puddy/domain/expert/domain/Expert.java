package com.team.puddy.domain.expert.domain;


import com.team.puddy.domain.BaseEntity;
import com.team.puddy.domain.expert.dto.ExpertFormDto;
import com.team.puddy.domain.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "expert")
@Getter
public class Expert extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expert_id")
    private Long id;

    @Column(name = "user_id")
    @Setter
    private Long userId;

    @ColumnDefault("false")
    @Column(name = "is_confirm")
    private boolean isConfirm;

    @NotNull
    @Setter
    private String introduce;

    @NotNull
    @Setter
    private String career;

    @Setter
    private String location;

    @Setter
    @Column
    private String education;

//     private File file;

    public static Expert createExpert(ExpertFormDto expertFormDto, Long userId){
        Expert expert = new Expert();
        expert.setUserId(userId);
        expert.setIntroduce(expertFormDto.getIntroduce());
        expert.setCareer(expertFormDto.getCareer());
        expert.setLocation(expertFormDto.getLocation());
        expert.setEducation(expertFormDto.getEducation());
        return expert;
    }
}
