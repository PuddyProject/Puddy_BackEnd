package com.team.puddy.domain.pet.domain;

import com.team.puddy.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="pet")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private String breed;

    private String birthday_date;

    private int age;

    private int weight;

    private boolean gender;



    private String imagePath;


}