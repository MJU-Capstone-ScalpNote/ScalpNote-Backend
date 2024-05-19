package com.scalpnote.domain.comment.presentation;

import com.scalpnote.domain.board.dto.FindPostRes;
import com.scalpnote.domain.comment.domain.Comment;
import com.scalpnote.domain.comment.dto.CommentReq;
import com.scalpnote.domain.comment.dto.CommentRes;
import com.scalpnote.domain.comment.service.CommentService;
import com.scalpnote.global.payload.ErrorResponse;
import com.scalpnote.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Board Comment", description = "Comment API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/comment")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "게시글 댓글 작성", description = "커뮤니티 게시글 댓글을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 댓글 작성 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentRes.class))}),
            @ApiResponse(responseCode = "400", description = "게시글 댓글 작성 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/{boardId}")
    public ResponseCustom<Long> insert(@PathVariable Long boardId,
                                             @RequestBody CommentReq commentReq) {
        return ResponseCustom.OK(commentService.insert(boardId, commentReq));
    }
}
