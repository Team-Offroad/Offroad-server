package site.offload.api.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberDeleteRequest(
        @Schema(description = "회원 삭제 코드", example = "deleteCode")
        String deleteCode
) {
}
