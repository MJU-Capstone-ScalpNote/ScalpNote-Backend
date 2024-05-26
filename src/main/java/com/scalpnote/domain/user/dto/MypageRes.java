package com.scalpnote.domain.user.dto;

import com.scalpnote.domain.user.domain.Role;
import com.scalpnote.domain.user.domain.User;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MypageRes {
    private Long id;
    private String email;
    private String name;
    private String scalpCondition;
    private Role role;



    public static MypageRes toDto(User user) {
        MypageRes mypageRes = MypageRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .scalpCondition(user.getScalpCondition())
                .build();
        return mypageRes;
    }
}
