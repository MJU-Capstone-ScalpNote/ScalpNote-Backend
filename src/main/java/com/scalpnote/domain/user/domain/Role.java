package com.scalpnote.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    DOCTOR, ADMIN, USER;
}
