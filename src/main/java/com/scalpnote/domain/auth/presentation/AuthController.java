package com.scalpnote.domain.auth.presentation;

import com.scalpnote.domain.auth.application.AuthService;
import com.scalpnote.domain.auth.dto.AuthRes;
import com.scalpnote.domain.auth.dto.SignInReq;
import com.scalpnote.domain.auth.dto.SignUpReq;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authorization", description = "Authorization API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "유저 회원가입", description = "회원가입을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "400", description = "회원가입 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping(value = "/sign-up")
    public ResponseCustom<?> signUp(
            @Parameter(description = "SignUpReq를 참고해주세요.", required = true) @Valid @RequestBody SignUpReq signUpReq
    ) {
        return authService.signUp(signUpReq);
    }

    @Operation(summary = "유저 로그인", description = "유저 로그인을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 로그인 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthRes.class))}),
            @ApiResponse(responseCode = "400", description = "유저 로그인 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping(value = "/sign-in")
    public ResponseCustom<?> signIn(
            @Parameter(description = "SignInReq를 참고해주세요.", required = true) @Valid @RequestBody SignInReq signInReq
    ) {
        return authService.signIn(signInReq);
    }
}
