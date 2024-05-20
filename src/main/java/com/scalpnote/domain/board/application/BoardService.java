package com.scalpnote.domain.board.application;

import com.scalpnote.domain.board.domain.Board;
import com.scalpnote.domain.board.domain.repository.BoardRepository;
import com.scalpnote.domain.board.dto.CreatePostReq;
import com.scalpnote.domain.board.dto.EditPostReq;
import com.scalpnote.domain.board.dto.FindPostRes;
import com.scalpnote.domain.comment.domain.repository.CommentRepository;
import com.scalpnote.domain.comment.dto.CommentRes;
import com.scalpnote.domain.user.domain.User;
import com.scalpnote.domain.user.domain.repository.UserRepository;
import com.scalpnote.global.DefaultAssert;
import com.scalpnote.global.config.s3.S3Service;
import com.scalpnote.global.config.security.token.UserPrincipal;
import com.scalpnote.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    private final S3Service s3Service;

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
                .imageUrl(s3Service.uploadImageToS3(createPostReq.getImage()))
                .build();

        boardRepository.save(board);


        return Message.builder()
                .message("게시물이 작성되었습니다.")
                .build();
    }

    @Transactional
    public Message editPost(Long postId, UserPrincipal userPrincipal, EditPostReq editPostReq) {

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(NullPointerException::new);

        Board board = boardRepository.findById(postId).orElseThrow(NullPointerException::new);

        if (!board.getWriter().equals(user)) {
            return Message.builder()
                    .message("해당 게시글의 수정 권한이 없습니다.")
                    .build();
        }


        board.updatePost(editPostReq.getTitle(), editPostReq.getContent());


        return Message.builder()
                .message("게시물 수정이 완료되었습니다.")
                .build();
    }

    @Transactional
    public Message deletePost(Long postId, UserPrincipal userPrincipal) {

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(NullPointerException::new);

        Board board = boardRepository.findById(postId).orElseThrow(NullPointerException::new);

        if (!board.getWriter().equals(user)) {
            return Message.builder()
                    .message("게시글 삭제에 실패했습니다.")
                    .build();
        }

        boardRepository.delete(board);

        return Message.builder()
                .message("게시글 삭제를 완료했습니다.")
                .build();
    }

    @Transactional(readOnly = true)
    public FindPostRes findPost(Long postId) {
        Board board = boardRepository.findById(postId).orElseThrow(NullPointerException::new);

        List<CommentRes> comments = commentRepository.findByBoardId(postId);

        return FindPostRes.toDto(board, comments);
    }

    @Transactional
    public Page<FindPostRes> findAllPosts(Pageable pageable) {
        Page<Board> boardsPage = boardRepository.findAll(pageable);

        List<FindPostRes> findPostResList = boardsPage.getContent().stream()
                .map(board -> FindPostRes.builder()
                        .postId(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .createdAt(board.getCreatedAt())
                        .writer(board.getWriter().getName())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(findPostResList, pageable, boardsPage.getTotalElements());
    }
}
