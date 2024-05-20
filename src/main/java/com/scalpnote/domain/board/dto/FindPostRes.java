package com.scalpnote.domain.board.dto;

import com.scalpnote.domain.board.domain.Board;
import com.scalpnote.domain.comment.dto.CommentRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
    private List<CommentRes> commentResList;
    private String imageUrl;

    public static FindPostRes toDto(Board board, List<CommentRes> comments) {

        return FindPostRes.builder()
                .postId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .writer(board.getWriter().getName())
                .imageUrl(board.getImageUrl())
                .commentResList(comments)
                .build();
    }
}
