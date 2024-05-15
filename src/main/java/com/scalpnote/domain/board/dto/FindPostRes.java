package com.scalpnote.domain.board.dto;

import com.scalpnote.domain.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindPostRes {
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String writer;

    public static FindPostRes toDto(Board board) {

        return FindPostRes.builder()
                .postId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .writer(board.getWriter().getName())
                .build();
    }
}
