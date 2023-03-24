package com.team.puddy.domain.expert.domain;


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
    @Setter
    private String companyNm;
    @ColumnDefault("false")
    @Column(name = "is_confirm")
    private boolean isConfirm;
    @NotNull
    @Setter
    private String introduction;
    @NotNull
    @Column(name = "company_name")
    @Setter
    private String companyName;

    @NotNull
    @Setter
    private String career;
    @Setter
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