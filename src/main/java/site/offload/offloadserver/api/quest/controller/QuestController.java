package site.offload.offloadserver.api.quest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.offload.offloadserver.api.message.SuccessMessage;
import site.offload.offloadserver.api.quest.dto.response.QuestResponse;
import site.offload.offloadserver.api.quest.usecase.QuestUseCase;
import site.offload.offloadserver.api.response.APISuccessResponse;
import site.offload.offloadserver.common.auth.PrincipalHandler;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class QuestController implements QuestControllerSwagger {

    private final QuestUseCase questUseCase;

    @GetMapping("/quests")
    public ResponseEntity<APISuccessResponse<QuestResponse>> getQuestInformation() {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.GET_QUEST_INFORMATION_SUCCESS.getMessage(), questUseCase.getQuestInformation(memberId));
    }

}
