package com.scalpnote.domain.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditPostReq {

    @Schema(type = "string", example = "제 머리 상태 어떤가요?", description = "수정할 게시글 제목")
    @NotNull
    private String title;

    @Schema(type = "string", example = "지루성 두피염이 나았는데 상태가 어떤가요?", description = "수정할 게시글 내용")
    private String content;

}
