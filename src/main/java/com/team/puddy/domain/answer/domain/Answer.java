package com.team.puddy.domain.answer.domain;

import com.team.puddy.domain.BaseEntity;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="answer")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    private Question question;

    @NotBlank
    private String content;

    @ColumnDefault("0")
    private boolean selected;

}
