package com.team.puddy.domain.pet.controller;

import com.team.puddy.domain.pet.dto.request.RequestPetDto;
import com.team.puddy.domain.pet.dto.response.ResponsePetDto;
import com.team.puddy.domain.pet.service.PetService;
import com.team.puddy.domain.user.service.UserService;
import com.team.puddy.global.common.S3UpdateUtil;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.user.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin("http://localhost:3000")

public class PetController {

    private final S3UpdateUtil s3UpdateUtil;

    private final PetService petService;

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/pets")
    public Response<Void> addPet(@RequestPart("request") RequestPetDto requestDto,
                                 @RequestParam(value = "file",required = false) MultipartFile file,
                                 @AuthenticationPrincipal JwtUserDetails user) throws IOException {
        String imagePath = "";
        if (file != null && !file.isEmpty()) {
            String fileName = s3UpdateUtil.createFileName(file.getOriginalFilename());
            imagePath = s3UpdateUtil.uploadPetToS3(file, fileName);
        }
        petService.addPet(user.getUserId(),imagePath,requestDto);

        return Response.success();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pets/detail")
    public Response<?> getPet(@AuthenticationPrincipal JwtUserDetails user) {
        ResponsePetDto response = petService.getPetByUserId(user.getUserId());

        return Response.success(response);
    }


}
