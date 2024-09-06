package site.offload.api.quest.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.member.service.MemberService;
import site.offload.api.quest.dto.response.QuestDetailListResponse;
import site.offload.api.quest.dto.response.QuestDetailResponse;
import site.offload.api.quest.dto.response.QuestInformationResponse;
import site.offload.api.quest.dto.response.QuestResponse;
import site.offload.api.quest.service.CompleteQuestService;
import site.offload.api.quest.service.ProceedingQuestService;
import site.offload.api.quest.service.QuestService;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.quest.entity.CompleteQuestEntity;
import site.offload.db.quest.entity.ProceedingQuestEntity;
import site.offload.db.quest.entity.QuestEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestUseCase {

    private final QuestService questService;
    private final ProceedingQuestService proceedingQuestService;
    private final CompleteQuestService completeQuestService;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public QuestResponse getQuestInformation(Long memberId) {
        List<ProceedingQuestEntity> proceedingQuestEntities = questService.findProceedingQuests(memberId);

        // 진행 중인 퀘스트가 없을 경우
        // TODO: 앱잼 이후 로직 수정 필요
        if (proceedingQuestEntities.isEmpty()) {
            QuestEntity questEntity = questService.findById(4);
            return QuestResponse.of(QuestInformationResponse.of(questEntity.getName(), 0, questEntity.getTotalRequiredClearCount()), QuestInformationResponse.of(questEntity.getName(), 0, questEntity.getTotalRequiredClearCount()));
        }

        List<QuestEntity> questEntities = proceedingQuestEntities.stream()
                .map(ProceedingQuestEntity::getQuestEntity)
                .toList();

        List<Double> achievement = IntStream.range(0, proceedingQuestEntities.size())
                .mapToObj(i -> {
                    ProceedingQuestEntity proceedingQuestEntity = proceedingQuestEntities.get(i);
                    QuestEntity questEntity = questEntities.get(i);
                    return calculateAchievement(proceedingQuestEntity.getCurrentClearCount(), questEntity.getTotalRequiredClearCount());
                }).toList();

        int almost = achievement.indexOf(achievement.stream().mapToDouble(Double::doubleValue).max().getAsDouble());//16.0

        ProceedingQuestEntity recentProceedingQuestEntity = proceedingQuestEntities.get(0);
        QuestEntity recentQuestEntity = questEntities.get(0);
        ProceedingQuestEntity almostProceedingQuestEntity = proceedingQuestEntities.get(almost);
        QuestEntity almostQuestEntity = questEntities.get(almost);

        return QuestResponse.of(QuestInformationResponse.of(recentQuestEntity.getName(), recentProceedingQuestEntity.getCurrentClearCount(), recentQuestEntity.getTotalRequiredClearCount()), QuestInformationResponse.of(almostQuestEntity.getName(), almostProceedingQuestEntity.getCurrentClearCount(), almostQuestEntity.getTotalRequiredClearCount()));
    }

    private Double calculateAchievement(int current, int goal) {
        return (double) current / (double) goal;
    }


    @Transactional(readOnly = true)
    public QuestDetailListResponse getQuestDetailList(final Long memberId, final boolean isActive) {
        if (isActive) {
            return QuestDetailListResponse.of(getActiveQuestList(memberId));
        } else {
            return QuestDetailListResponse.of(getAllQuestList(memberId));
        }
    }


    private List<QuestDetailResponse> getAllQuestList(final Long memberId) {
        final List<QuestEntity> completedQuestList = completeQuestService.findAllByMemberId(memberId)
                .stream()
                .map(CompleteQuestEntity::getQuestEntity).toList();
        final List<Integer> completeQuestIdList = completedQuestList.stream()
                .map(QuestEntity::getId)
                .toList();

        List<QuestEntity> notCompletedQuestList;
        if (completeQuestIdList.isEmpty()) {
            notCompletedQuestList = questService.findAll();
        } else {
            notCompletedQuestList = questService.findByIdNotIn(completeQuestIdList)
                    .stream()
                    .sorted(Comparator.comparing(QuestEntity::getId))
                    .toList();
        }

        final List<QuestEntity> allQuests = new ArrayList<>(notCompletedQuestList);
        allQuests.addAll(completedQuestList);
        return allQuests.stream().map(
                questEntity -> {
                    int currentClearCount = 0;
                    final MemberEntity memberEntity = memberService.findById(memberId);

                    if (completedQuestList.contains(questEntity)) {
                        currentClearCount = questEntity.getTotalRequiredClearCount();
                    }
                    if (proceedingQuestService.existsByMemberAndQuest(memberEntity, questEntity)) {
                        currentClearCount = proceedingQuestService.findByMemberAndQuest(memberEntity, questEntity).getCurrentClearCount();
                    }

                    return QuestDetailResponse.of(
                            questEntity.getName(),
                            questEntity.getDescription(),
                            currentClearCount,
                            questEntity.getTotalRequiredClearCount(),
                            questEntity.getClearConditionText(),
                            questEntity.getRewardText()
                    );
                }
        ).toList();
    }


    private List<QuestDetailResponse> getActiveQuestList(final Long memberId) {
        return proceedingQuestService.findAllByMemberId(memberId)
                .stream()
                .filter(
                        (proceedingQuestEntity) -> proceedingQuestEntity.getCurrentClearCount() > 0 &&
                                proceedingQuestEntity.getCurrentClearCount() < proceedingQuestEntity.getQuestEntity().getTotalRequiredClearCount()
                )
                .sorted(
                        // 달성도 내림차순
                        Comparator.comparingDouble((proceedingQuestEntity) ->
                                (double) proceedingQuestEntity.getQuestEntity().getTotalRequiredClearCount() / (double) proceedingQuestEntity.getCurrentClearCount()
                        )
                )
                .map(proceedingQuestEntity -> {
                    final QuestEntity questEntity = proceedingQuestEntity.getQuestEntity();
                    return QuestDetailResponse.of(
                            questEntity.getName(),
                            questEntity.getDescription(),
                            proceedingQuestEntity.getCurrentClearCount(),
                            questEntity.getTotalRequiredClearCount(),
                            questEntity.getClearConditionText(),
                            questEntity.getRewardText()
                    );
                })
                .toList();
    }
}
