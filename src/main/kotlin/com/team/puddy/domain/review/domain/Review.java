package com.team.puddy.domain.review.domain;

import com.team.puddy.domain.BaseTimeEntity;
import com.team.puddy.domain.expert.domain.Expert;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="expert_id")
    private Expert expert;



}
