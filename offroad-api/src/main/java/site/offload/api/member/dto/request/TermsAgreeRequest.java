package site.offload.api.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record TermsAgreeRequest(
        @Schema(description = "회원 ID", example = "1")
        boolean marketing
) {
}
