package com.team.puddy.domain.user.domain;

import com.team.puddy.domain.BaseEntity;
import com.team.puddy.domain.question.domain.Question;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String account;

    private String password;

    private String email;

    private String username;

    @Setter private String nickname;

    @Setter
    @Column(length = 1000)
    private String imagePath;

    @Setter
    @Builder.Default
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Question> questionList = new ArrayList<>();

}
