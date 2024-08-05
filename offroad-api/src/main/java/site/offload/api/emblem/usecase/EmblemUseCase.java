package site.offload.api.emblem.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.emblem.dto.request.UpdateCurrentEmblemRequest;
import site.offload.api.emblem.dto.response.EmblemResponse;
import site.offload.api.emblem.dto.response.EmblemsResponse;
import site.offload.api.emblem.dto.response.GainedEmblemListResponse;
import site.offload.api.emblem.dto.response.GainedEmblemResponse;
import site.offload.api.emblem.service.GainedEmblemService;
import site.offload.api.exception.BadRequestException;
import site.offload.api.exception.NotFoundException;
import site.offload.api.member.service.MemberService;
import site.offload.api.quest.service.QuestRewardService;
import site.offload.api.quest.service.QuestService;
import site.offload.db.emblem.entity.GainedEmblemEntity;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.quest.entity.QuestRewardEntity;
import site.offload.enums.emblem.Emblem;
import site.offload.enums.response.ErrorMessage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmblemUseCase {

    private final MemberService memberService;
    private final GainedEmblemService gainedEmblemService;
    private final QuestRewardService questRewardService;
    private final QuestService questService;

    @Transactional
    public void updateCurrentEmblem(final UpdateCurrentEmblemRequest request) {
        //존재하는 칭호인지 확인
        if (isExistsEmblem(request.emblemCode())) {
            final MemberEntity findMemberEntity = memberService.findById(request.memberId());
            //유저가 얻은 칭호인지 확인
            if (gainedEmblemService.isExistsByMemberAndEmblemCode(findMemberEntity, request.emblemCode())) {
                findMemberEntity.updateEmblemName(Emblem.getEmblemByCode(request.emblemCode()));
            } else {
                throw new BadRequestException(ErrorMessage.MEMBER_EMBLEM_UPDATE_EXCEPTION);
            }
        } else {
            throw new NotFoundException(ErrorMessage.EMBLEM_NOTFOUND_EXCEPTION);
        }
    }

    private boolean isExistsEmblem(String emblemCode) {
        return Emblem.isExistsEmblem(emblemCode);
    }

    @Transactional(readOnly = true)
    public GainedEmblemListResponse getGainedEmblems(Long memberId) {
        return GainedEmblemListResponse.of(gainedEmblemService.findAllByMemberId(memberId).stream().map(
                gainedEmblem -> GainedEmblemResponse.of(gainedEmblem.getEmblemCode(),
                        Emblem.getEmblemByCode(gainedEmblem.getEmblemCode()).getEmblemName())).toList());
    }

    @Transactional(readOnly = true)
    public EmblemsResponse getEmblems(Long memberId) {
        List<QuestRewardEntity> questRewardEntitiesWithEmblems = questRewardService.findQuestWithEmblems();
        List<GainedEmblemEntity> gainedEmblemEntities = gainedEmblemService.findAllByMemberId(memberId);

        List<String> emblemCodes = gainedEmblemEntities.stream()
                .map(GainedEmblemEntity::getEmblemCode)
                .toList();

        List<EmblemResponse> gainedEmblems = questRewardEntitiesWithEmblems.stream()
                .filter(questRewardEntityWithEmblem -> emblemCodes.contains(questRewardEntityWithEmblem.getRewardList().getEmblemCode()))
                .map(questRewardEntity -> EmblemResponse.of(Emblem.getEmblemByCode(questRewardEntity.getRewardList().getEmblemCode()).getEmblemName(), questService.findById(questRewardEntity.getQuestId()).getName()))
                .toList();

        List<EmblemResponse> notGainedEmblems = questRewardEntitiesWithEmblems.stream()
                .filter(questRewardEntityWithEmblem -> !emblemCodes.contains(questRewardEntityWithEmblem.getRewardList().getEmblemCode()))
                .map(questRewardEntity -> EmblemResponse.of(Emblem.getEmblemByCode(questRewardEntity.getRewardList().getEmblemCode()).getEmblemName(), questService.findById(questRewardEntity.getQuestId()).getName()))
                .toList();

        return EmblemsResponse.of(gainedEmblems, notGainedEmblems);
    }
}
