package com.scalpnote.domain.comment.domain.repository;

import com.scalpnote.domain.comment.dto.CommentRes;

import java.util.List;

public interface CommentCustomRepository {
    List<CommentRes> findByBoardId(Long id);
}
