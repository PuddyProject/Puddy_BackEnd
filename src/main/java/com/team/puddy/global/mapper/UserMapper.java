package com.team.puddy.global.mapper;

import com.team.puddy.domain.type.UserRole;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.dto.request.RegisterUserRequest;
import com.team.puddy.domain.user.dto.response.ResponseUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    default User toEntity(RegisterUserRequest request){
        return User.builder()
                .account(request.account())
                .password(request.password())
                .email(request.email())
                .username(request.username())
                .role(UserRole.USER.getRole())
                .isNotificated(request.isNotificated())
                .nickname("퍼디1234")
                .build();
    }

    ResponseUserDto toDto(User user);
}
