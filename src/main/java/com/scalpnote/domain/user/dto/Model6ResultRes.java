package com.scalpnote.domain.user.dto;

import com.scalpnote.domain.user.domain.Model6Result;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class Model6ResultRes {
    private Long id;
    private String scalpCondition;
    private LocalDateTime createdAt;

    @Builder
    public Model6ResultRes(Long id, String scalpCondition, LocalDateTime createdAt) {
        this.id = id;
        this.scalpCondition = scalpCondition;
        this.createdAt = createdAt;
    }

    public static Model6ResultRes fromEntity(Model6Result model6Result) {
        return Model6ResultRes.builder()
                .id(model6Result.getId())
                .scalpCondition(model6Result.getScalpCondition())
                .createdAt(model6Result.getCreatedAt())
                .build();
    }

}
