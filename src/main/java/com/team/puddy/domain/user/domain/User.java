package com.team.puddy.domain.user.domain;

import com.team.puddy.domain.BaseTimeEntity;
import com.team.puddy.domain.article.domain.Article;
import com.team.puddy.domain.comment.domain.Comment;
import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.domain.pet.domain.Pet;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.type.JwtProvider;
import com.team.puddy.domain.type.UserRole;
import lombok.*;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "\"user\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {

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

    @Setter
    private String nickname;

    @Setter
    private boolean isNotificated;

    @Enumerated(value = EnumType.STRING)
    private JwtProvider provider;

    public void setProvider(JwtProvider provider) {
        if(this.provider == null) {
            this.provider = provider;
        }
    }

    @NotNull
    @Column(name = "role")
    private String role;


    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @Setter
    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Question> questionList = new ArrayList<>();

    @Builder.Default
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Expert expert = null;

    @Builder.Default
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="pet_id")
    private Pet pet = null;

    @Builder.Default
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Article> articleList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    public static User fromOAuth2User(OAuth2User oAuth2User, JwtProvider provider) {
        return User.builder()
                .provider(provider)
                .account(oAuth2User.getAttribute("id"))
                .email(oAuth2User.getAttribute("email"))
                .username(oAuth2User.getAttribute("name"))
                .build();
    }

    public void updateAuth() {
        this.role = UserRole.EXPERT.getRole();
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

}

