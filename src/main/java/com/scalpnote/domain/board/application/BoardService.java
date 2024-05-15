package com.scalpnote.domain.board.application;

import com.scalpnote.domain.board.domain.Board;
import com.scalpnote.domain.board.domain.repository.BoardRepository;
import com.scalpnote.domain.board.dto.CreatePostReq;
import com.scalpnote.domain.user.domain.User;
import com.scalpnote.domain.user.domain.repository.UserRepository;
import com.scalpnote.global.DefaultAssert;
import com.scalpnote.global.config.security.token.UserPrincipal;
import com.scalpnote.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public Message createPost(UserPrincipal userPrincipal, CreatePostReq createPostReq) {

        System.out.println("userPrincipal = " + userPrincipal);
        System.out.println("userPrincipal.getEmail() = " + userPrincipal.getEmail());
        Optional<User> user = userRepository.findById(userPrincipal.getId());
        DefaultAssert.isTrue(user.isPresent(), "올바른 유저가 아닙니다.");
        User user2 = user.get();


        Board board = Board.builder()
                .title(createPostReq.getTitle())
                .content(createPostReq.getContent())
                .writer(user2)
                .build();

        System.out.println("board = " + board);
        Board board2 = boardRepository.save(board);
        System.out.println("Saved board ID: " + board2.getId());


        return Message.builder()
                .message("게시물이 작성되었습니다.")
                .build();
    }
}