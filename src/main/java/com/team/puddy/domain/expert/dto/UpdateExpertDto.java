package com.team.puddy.domain.expert.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record UpdateExpertDto(String username,
                              String introduce,

                              List<String> careerList,

                              @NotBlank
                              String location,

                              @NotBlank
                              String education

) {
}
