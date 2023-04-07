package com.team.puddy.domain.pet.domain;

import com.team.puddy.domain.BaseTimeEntity;
import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.domain.pet.dto.request.UpdatePetDto;
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
public class Pet extends BaseTimeEntity {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    private String name;

    private String breed;

    private int age;

    private float weight;

    private boolean gender;

    private boolean isNeutered;

    @Builder.Default
    @OneToOne(fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)
    private Image image = null;

    @Lob
    @Type(type = "text")
    private String note;

    public void setImage(Image image) {
        this.image = image;
    }

    public void updatePet(UpdatePetDto updatePetDto) {
        this.name = updatePetDto.name();
        this.age = updatePetDto.age();
        this.breed = updatePetDto.breed();
        this.isNeutered = updatePetDto.isNeutered();
        this.weight = updatePetDto.weight();
        this.gender = updatePetDto.gender();
        this.note = updatePetDto.note();
    }

}