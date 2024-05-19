package com.scalpnote.domain.comment.domain.repository;

import com.scalpnote.domain.comment.domain.Comment;
import com.scalpnote.domain.comment.dto.CommentRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository{
}
