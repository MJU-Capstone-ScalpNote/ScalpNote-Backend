package com.scalpnote.domain.user.presentation;

import com.scalpnote.domain.board.dto.CreatePostReq;
import com.scalpnote.domain.comment.dto.CommentReq;
import com.scalpnote.domain.comment.dto.CommentRes;
import com.scalpnote.domain.user.application.UserService;
import com.scalpnote.domain.user.dto.HairConditionReq;
import com.scalpnote.domain.user.dto.MypageRes;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "model6 두피 진단 결과 저장", description = "model6 두피 진단결과를 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "진단 결과 저장 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "400", description = "진단 결과 저장 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/model6")
    public ResponseCustom<Message> model6Insert(@Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
                                                @Parameter(description = "Schemas의 hairConditionReq를 참고해주세요.") @Valid @RequestBody HairConditionReq hairConditionReq) {
        return ResponseCustom.OK(userService.model6Insert(userPrincipal, hairConditionReq));
    }

    @Operation(summary = "마이페이지 조회", description = "마이페이지를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MypageRes.class))}),
            @ApiResponse(responseCode = "400", description = "마이페이지 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/mypage")
    public ResponseCustom<MypageRes> findMypage(@Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal) {
        return ResponseCustom.OK(userService.findMypage(userPrincipal));
    }

}
