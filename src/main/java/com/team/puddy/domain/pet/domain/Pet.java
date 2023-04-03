package com.team.puddy.domain.pet.domain;

import com.team.puddy.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "pet")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private String breed;

    private int age;

    private float weight;

    private boolean gender;

    private boolean isNeutered;

    private String imagePath;
    @Lob
    @Type(type = "text")
    private String note;


}