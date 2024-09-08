package site.offload.api.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import site.offload.api.exception.BadRequestException;
import site.offload.enums.response.ErrorMessage;

import java.util.List;

public record TestMemberDeleteRequest(
        @Schema(description = "이름", example = "민성")
        String name
) {
    public TestMemberDeleteRequest {
        validateName(name);
    }

    private void validateName(final String name) {
        List<String> offroadNameList = List.of("민성", "지원", "혜린", "정현", "석찬", "준서", "의진", "환준", "윤한");
        if (!offroadNameList.contains(name)) {
            throw new BadRequestException(ErrorMessage.ERROR_MESSAGE);
        }
    }
}
