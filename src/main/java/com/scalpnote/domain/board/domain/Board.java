package com.scalpnote.domain.board.domain;

import com.scalpnote.domain.common.BaseEntity;
import com.scalpnote.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Table(name = "Board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;


    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "access_scope")
    private String accessScope;


    @Builder
    public Board(User writer, String title, String content, String imageUrl, String accessScope) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.accessScope = accessScope;
    }




    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
