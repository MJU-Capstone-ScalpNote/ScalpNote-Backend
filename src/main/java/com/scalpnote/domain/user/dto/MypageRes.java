package com.scalpnote.domain.user.dto;

import com.scalpnote.domain.user.domain.Model6Result;
import com.scalpnote.domain.user.domain.Role;
import com.scalpnote.domain.user.domain.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MypageRes {
    private Long id;
    private String email;
    private String name;
    private Model6ResultRes model6ResultRes;
    private Role role;

    @Builder
    public MypageRes(Long id, String email, String name, Model6ResultRes model6ResultRes, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.model6ResultRes = model6ResultRes;
        this.role = role;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Model6ResultRes {
        private List<Model6ResultDto> model6ResultList = new ArrayList<>();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Model6ResultDto {
        private Long id;
        private String scalpCondition;
        private String createdAt;
    }

    public static MypageRes toDto(User user, List<Model6Result> model6ResultList) {
        List<Model6ResultDto> model6ResultDtos = new ArrayList<>();
        for (Model6Result result : model6ResultList) {
            model6ResultDtos.add(Model6ResultDto.builder()
                    .id(result.getId())
                    .scalpCondition(result.getScalpCondition())
                    .createdAt(result.getCreatedAt().toString())
                    .build());
        }

        return MypageRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .model6ResultRes(Model6ResultRes.builder()
                        .model6ResultList(model6ResultDtos)
                        .build())
                .role(user.getRole())
                .build();
    }
}