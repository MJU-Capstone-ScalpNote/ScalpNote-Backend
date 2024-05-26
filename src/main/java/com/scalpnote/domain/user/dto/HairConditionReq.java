package com.scalpnote.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HairConditionReq {
    @Schema(type = "string", example = "1단계", description = "두피상태 진단 결과")
    @NotNull
    private String hairCondition;
}
