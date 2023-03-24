package com.team.puddy.domain.expert.domain;


import com.team.puddy.domain.expert.dto.ExpertFormDto;
import com.team.puddy.domain.user.domain.User;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "exeprt")
@Data
public class Expert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expert_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "company_no")
    private String companyNm;
    @ColumnDefault("false")
    @Column(name = "is_confirm")
    private boolean isConfirm;
    @NotNull
    private String introduction;
    @NotNull
    @Column(name = "company_name")
    private String companyName;

    @NotNull
    private String career;
    private String location;

//     private File file;

    public static Expert createExpert(ExpertFormDto expertFormDto){
        Expert expert = new Expert();
        expert.setCompanyNm(expertFormDto.getCompanyNm());
        expert.setIntroduction(expertFormDto.getIntroduction());
        expert.setCompanyName(expertFormDto.getCompanyName());
        expert.setCareer(expertFormDto.getCareer());
        expert.setLocation(expertFormDto.getLocation());
        return expert;
    }
}
