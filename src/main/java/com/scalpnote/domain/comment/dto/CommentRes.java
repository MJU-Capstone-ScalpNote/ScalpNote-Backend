package com.scalpnote.domain.comment.dto;

import com.scalpnote.domain.comment.domain.Comment;
import com.scalpnote.domain.user.dto.UserRes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRes {

    private Long id;
    private String content;
    private UserRes writer;
    private List<CommentRes> children = new ArrayList<>();

    public CommentRes(Long id, String content, UserRes writer) {
        this.id = id;
        this.content = content;
        this.writer = writer;
    }

    public static CommentRes toDto(Comment comment) {
        return comment.getIsDeleted() ?
                new CommentRes(comment.getId(), "삭제된 댓글입니다.", null) :
                new CommentRes(comment.getId(), comment.getContent(), new UserRes(comment.getWriter()));
    }
}
