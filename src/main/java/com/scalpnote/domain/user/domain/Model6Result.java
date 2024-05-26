package com.scalpnote.domain.user.domain;

import com.scalpnote.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Model6Result")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Model6Result extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "scalpCondition")
    private String scalpCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User user;



    @Builder
    public Model6Result(String scalpCondition, User user) {
        this.scalpCondition = scalpCondition;
        this.user = user;
    }
}
