package com.team.puddy.domain.user.domain;

import com.team.puddy.domain.BaseEntity;
import com.team.puddy.domain.BaseTimeEntity;
import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.pet.domain.Pet;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.type.UserRole;
import io.jsonwebtoken.Claims;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name="\"user\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String account;

    @Setter
    private String password;

    @Column(unique = true)
    private String email;

    private String username;

    @Setter private String nickname;

    @Setter
    private boolean isNotificated;

    @NotNull
    @Column(name = "role")
    private String role;

    @Setter
    @Column(length = 1000)
    private String imagePath;

    @Setter
    @Builder.Default
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Question> questionList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name="expert_id")
    private Expert expert;

    @Builder.Default
    @OneToOne
    @JoinColumn(name= "pet_id")
    private Pet pet= null;

    public void updateAuth() {
        this.role = UserRole.EXPERT.getRole();
    }

    public void setExpert(Expert expert) {
        this.expert=expert;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

}

