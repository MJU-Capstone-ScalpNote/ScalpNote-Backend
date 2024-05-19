package com.scalpnote.domain.comment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReq {
    private Long userId;
    private Long parentId;
    private String content;

    public CommentReq(String content) {
        this.content = content;
    }
}
