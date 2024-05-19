package com.scalpnote.domain.user.dto;

import com.scalpnote.domain.user.domain.Role;
import com.scalpnote.domain.user.domain.User;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRes {

    private Long id;
    private String email;
    private String name;
    private Role role;

    public UserRes(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole();
    }

    @Builder
    public UserRes(Long id, String email, String name, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }
}
