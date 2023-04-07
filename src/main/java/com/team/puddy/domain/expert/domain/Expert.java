package com.team.puddy.domain.expert.domain;


import com.team.puddy.domain.BaseTimeEntity;
import com.team.puddy.domain.review.domain.Review;
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
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    private String username;

    @NotNull
    private String introduce;

    @ElementCollection
    @CollectionTable(name = "career", joinColumns =
    @JoinColumn(name = "expert_id"))
    @Column(name = "content")
    @Builder.Default
    private List<String> careerList = new ArrayList<>();

    private String location;

    private String education;

    @OneToMany(mappedBy = "expert", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Review> reviewList = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    //리뷰 추가 로직 컨트롤러 어디에 할건지 생각하기
    public void updateCareerList(List<String> careerList) {
        this.careerList.clear();
        this.careerList.addAll(careerList);
    }

}
