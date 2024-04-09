package com.scalpnote.domain.comment.domain;

import com.scalpnote.domain.board.domain.Board;
import com.scalpnote.domain.common.BaseEntity;
import com.scalpnote.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;

@Entity
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parentComment;


    @Builder
    public Comment(String content, User writer, Board board, Comment parentComment) {
        this.content = content;
        this.writer = writer;
        this.board = board;
        this.parentComment = parentComment;
    }
}
