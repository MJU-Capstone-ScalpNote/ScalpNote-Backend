package com.scalpnote.domain.comment.domain;

import com.scalpnote.domain.board.domain.Board;
import com.scalpnote.domain.common.BaseEntity;
import com.scalpnote.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", updatable = false)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String content;

    @ColumnDefault("FALSE")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;


    @Builder
    public Comment(String content, User writer, Board board) {
        this.content = content;
        this.writer = writer;
        this.board = board;
    }



    public void updateWriter(User  user) {
        this.writer = user;
    }

    public void updateBoard(Board board) {
        this.board = board;
    }

    public void updateParent(Comment comment) {
        this.parent = comment;
    }

    public void changeIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
