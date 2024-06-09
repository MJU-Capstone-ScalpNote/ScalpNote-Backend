package com.scalpnote.domain.board.presentation;

import com.scalpnote.domain.board.application.BoardService;
import com.scalpnote.domain.board.dto.BoardRes;
import com.scalpnote.domain.board.dto.CreatePostReq;
import com.scalpnote.domain.board.dto.EditPostReq;
import com.scalpnote.domain.board.dto.FindPostRes;
import com.scalpnote.global.config.security.token.CurrentUser;
import com.scalpnote.global.config.security.token.UserPrincipal;
import com.scalpnote.global.payload.ErrorResponse;
import com.scalpnote.global.payload.Message;
import com.scalpnote.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "Board", description = "Board API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;


    @Operation(summary = "게시글 작성", description = "커뮤니티 게시글을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 작성 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "400", description = "게시글 작성 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/new")
    public ResponseCustom<Message> createPost(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "Schemas의 createPostReq를 참고해주세요.") @Valid @ModelAttribute CreatePostReq createPostReq
            ) {
        if (userPrincipal == null) {
            throw new RuntimeException("UserPrincipal is null");
        }
        System.out.println("createPostReq.getImage() = " + createPostReq.getImage());
        System.out.println("createPostReq = " + createPostReq.getTitle());
        return ResponseCustom.OK(boardService.createPost(userPrincipal, createPostReq));
    }

    @Operation(summary = "게시글 수정", description = "커뮤니티 게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "400", description = "게시글 수정 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PatchMapping("/{postId}/edit")
    public ResponseCustom<Message> editPost(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "Schemas의 eidtPostReq를 참고해주세요.") @Valid @RequestBody EditPostReq editPostReq,
            @Parameter(description = "게시물의 id를 입력해주세요.") @PathVariable Long postId
    ) {
        if (userPrincipal == null) {
            throw new RuntimeException("UserPrincipal is null");
        }
        return ResponseCustom.OK(boardService.editPost(postId, userPrincipal, editPostReq));
    }

    @Operation(summary = "게시글 삭제", description = "커뮤니티 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "400", description = "게시글 삭제 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/{postId}/delete")
    public ResponseCustom<Message> deletePost(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "게시물의 id를 입력해주세요.") @PathVariable Long postId
    ) {
        if (userPrincipal == null) {
            throw new RuntimeException("UserPrincipal is null");
        }
        return ResponseCustom.OK(boardService.deletePost(postId, userPrincipal));
    }

    @Operation(summary = "게시글 개별 조회", description = "커뮤니티 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FindPostRes.class))}),
            @ApiResponse(responseCode = "400", description = "게시글 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/{postId}/simple-boards")
    public ResponseCustom<FindPostRes> findPost(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "게시물의 id를 입력해주세요.") @PathVariable Long postId
    ) {
        // Todo 북마크 기능 추가 후, 북마크 여부 알려주는 기능 추가
//        if (userPrincipal == null) {
//            throw new RuntimeException("UserPrincipal is null");
//        } //
        return ResponseCustom.OK(boardService.findPost(userPrincipal, postId));
    }

    @Operation(summary = "게시글 목록 조회", description = "커뮤니티 게시글 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FindPostRes.class))}),
            @ApiResponse(responseCode = "400", description = "게시글 목록 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping
    public ResponseCustom<Page<FindPostRes>> findAllPosts(Pageable pageable) {
        return ResponseCustom.OK(boardService.findAllPosts(pageable));
    }


}
