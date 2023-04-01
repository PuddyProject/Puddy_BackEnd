package com.team.puddy.domain.expert.domain;


import com.team.puddy.domain.BaseTimeEntity;
import com.team.puddy.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "expert")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expert extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expert_id")
    private Long id;

    @OneToOne(mappedBy = "expert",fetch = FetchType.LAZY)
    private User user;

    private String username;

    @NotNull
    private String introduce;

    @ElementCollection
    @CollectionTable(name="career",joinColumns =
            @JoinColumn(name="expert_id"))
    @Column(name = "content")
    @Builder.Default
    private List<String> careerList = new ArrayList<>();

    private String location;

    private String education;

    public void setCareerList(List<String> careerList) {
        this.careerList = careerList;
    }

}
