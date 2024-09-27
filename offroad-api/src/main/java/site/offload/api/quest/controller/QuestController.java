package site.offload.api.quest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.quest.dto.request.QuestDetailListRequest;
import site.offload.api.quest.dto.response.QuestDetailListResponse;
import site.offload.api.quest.dto.response.QuestResponse;
import site.offload.api.quest.usecase.QuestUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@RestController
@RequestMapping("/api/quests")
@RequiredArgsConstructor
public class QuestController implements QuestControllerSwagger {

    private final QuestUseCase questUseCase;

    @GetMapping("/recent-almost")
    public ResponseEntity<APISuccessResponse<QuestResponse>> getQuestInformation() {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.GET_QUEST_INFORMATION_SUCCESS.getMessage(), questUseCase.getQuestInformation(memberId));
    }

    @GetMapping
    public ResponseEntity<APISuccessResponse<QuestDetailListResponse>> getQuestList(@RequestParam(value = "isActive") boolean isActive) {
        return APISuccessResponse.of(
                HttpStatus.OK.value(),
                SuccessMessage.GET_QUEST_DETAIL_LIST_SUCCESS.getMessage(),
                questUseCase.getQuestDetailList(PrincipalHandler.getMemberIdFromPrincipal(), isActive));
    }
}
