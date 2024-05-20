package com.scalpnote.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CreatePostReq {

    @Schema(type = "string", example = "제 머리 상태 어떤가요?", description = "게시글 제목")
    @NotNull
    private String title;

    @Schema(type = "string", example = "지루성 두피염이 나았는데 상태가 어떤가요?", description = "게시글 내용")
    private String content;

    @Schema(type = "string", description = "게시글에 첨부할 이미지 파일", format = "binary")
    private MultipartFile image;


}
