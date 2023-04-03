package com.team.puddy.global.mapper;

import com.team.puddy.domain.type.UserRole;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.dto.request.RegisterUserRequest;
import com.team.puddy.domain.user.dto.response.ResponseUserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Random;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    default User toEntity(RegisterUserRequest request){
        Random random = new Random();
        int randomNumber = random.nextInt(100000); // 랜덤한 5자리 숫자 생성 (~100000)
        String nickname = "퍼디" + randomNumber;
        return User.builder()
                .account(request.account())
                .password(request.password())
                .email(request.email())
                .username(request.username())
                .role(UserRole.USER.getRole())
                .isNotificated(request.isNotificated())
                .nickname(nickname)
                .build();
    }

    ResponseUserInfoDto toDto(User user,boolean hasPet);
}
