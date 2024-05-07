package com.scalpnote.domain.user.domain;

import com.scalpnote.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "Member")
@NoArgsConstructor
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    @Column(name = "password", nullable = false, unique = true, updatable = false)
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "hairLossType")
    private String hairLossType;

    @Column(name = "scalpCondition")
    private String scalpCondition;


    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Builder
    public User(String email, String password, String name, String hairLossType, String scalpCondition, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.hairLossType = hairLossType;
        this.scalpCondition = scalpCondition;
        this.role = role;
    }
}
