package com.scalpnote.domain.bookmark.domain;

import com.scalpnote.domain.board.domain.Board;
import com.scalpnote.domain.common.BaseEntity;
import com.scalpnote.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;

@Entity
public class BoardBookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public BoardBookmark(User user, Board board) {
        this.user = user;
        this.board = board;
    }
}
