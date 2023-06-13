package com.team.puddy.domain.user.domain

import com.team.puddy.domain.BaseTimeEntity
import com.team.puddy.domain.expert.domain.Expert
import com.team.puddy.domain.image.domain.Image
import com.team.puddy.domain.pet.domain.Pet
import com.team.puddy.domain.question.domain.Question
import com.team.puddy.domain.type.JwtProvider
import com.team.puddy.domain.type.UserRole
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.springframework.security.oauth2.core.user.OAuth2User

@Entity
@Table(name = "\"user\"")
class User(

    @Column(nullable = false, unique = true)
    private val account: String,

    private var password: String,

    @Column(unique = true)
    private val email: String,

    private val username: String,

    private var nickname: String,

    private var isNotificated: Boolean = false,

    @Enumerated(value = EnumType.STRING)
    private var provider: JwtProvider? = null,

    @NotNull @Column(name = "role")
    private var role: String,

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    private var expert: Expert? = null

) : BaseTimeEntity() {

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = [CascadeType.ALL])
    @JoinColumn(name = "image_id")
    private var image: Image? = null

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "pet_id")
    private var pet: Pet? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL])
    private var questionList : List<Question> = ArrayList()

    fun setProvider(jwtProvider: JwtProvider) {
        if (provider == null) {
            provider = jwtProvider
        }
    }

    fun updateOauthUser(isNotificated: Boolean, provider: String) {
        this.isNotificated = isNotificated
    }

    fun updateAuth() {
        this.role = UserRole.EXPERT.role
    }

    fun updatePassword(tmpPassword: String) {
        this.password = tmpPassword
    }

    companion object {
        fun fromOAuth2User(oAuth2User: OAuth2User, provider: JwtProvider): User {
            return User(
                account = oAuth2User.getAttribute("id")!!,
                password = "",
                email = oAuth2User.getAttribute("email")!!,
                username = oAuth2User.getAttribute("name")!!,
                nickname = "",
                role = "",
                provider = provider
            )
        }
    }
}