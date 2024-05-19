package com.scalpnote.domain.comment.service;

import com.scalpnote.domain.board.domain.Board;
import com.scalpnote.domain.board.domain.repository.BoardRepository;
import com.scalpnote.domain.comment.domain.Comment;
import com.scalpnote.domain.comment.domain.repository.CommentRepository;
import com.scalpnote.domain.comment.dto.CommentReq;
import com.scalpnote.domain.user.domain.User;
import com.scalpnote.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long insert(Long boardId, CommentReq commentReq) {

        User user = userRepository.findById(commentReq.getUserId())
                .orElseThrow(NullPointerException::new);

        Board board = boardRepository.findById(boardId)
                .orElseThrow(NullPointerException::new);

        Comment comment = Comment.builder()
                .writer(user)
                .board(board)
                .content(commentReq.getContent())
                .build();

        Comment parentComment;
        if (commentReq.getParentId() != null) {
            parentComment = commentRepository.findById(commentReq.getParentId())
                    .orElseThrow(NullPointerException::new);

            comment.updateParent(parentComment);
        }


        return commentRepository.save(comment).getId();
    }
}
