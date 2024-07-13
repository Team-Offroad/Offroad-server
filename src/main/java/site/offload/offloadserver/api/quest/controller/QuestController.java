package site.offload.offloadserver.api.quest.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.offload.offloadserver.api.member.dto.response.MemberAdventureInformationResponse;
import site.offload.offloadserver.api.message.SuccessMessage;
import site.offload.offloadserver.api.quest.dto.response.HomeQuestResponse;
import site.offload.offloadserver.api.quest.usecase.QuestUseCase;
import site.offload.offloadserver.api.response.APISuccessResponse;
import site.offload.offloadserver.common.auth.PrincipalHandler;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class QuestController {

    private final QuestUseCase questUseCase;
    private final PrincipalHandler principalHandler;

    @GetMapping("/quests")
    public ResponseEntity<APISuccessResponse<HomeQuestResponse>> getQuestInformation() {
        final Long memberId = principalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.GET_QUEST_INFORMATION_SUCCESS.getMessage(), questUseCase.getHomeQuestInformation(memberId));
    }

}
