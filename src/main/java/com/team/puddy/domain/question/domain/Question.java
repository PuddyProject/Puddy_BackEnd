package com.team.puddy.domain.question.domain;

import com.team.puddy.domain.BaseEntity;
import com.team.puddy.domain.BaseTimeEntity;
import com.team.puddy.domain.answer.domain.Answer;
import com.team.puddy.domain.type.Category;
import com.team.puddy.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Question extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",updatable = false)
    private User user;
    @NotBlank
    @Column(name = "title", length = 50)
    private String title;
    @NotBlank
    @Lob
    @Type(type = "text")
    private String content;

    @ColumnDefault("0")
    private long viewCount;

    @ColumnDefault("false")
    private boolean isSolved;

    @ColumnDefault("false")
    private boolean isDeleted;

    private int postCategory;

    @Setter
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Setter
    @Builder.Default
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY,
            orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Answer> answerList = new ArrayList<>();

    @Column(length = 500)
    private String imagePath;

}
