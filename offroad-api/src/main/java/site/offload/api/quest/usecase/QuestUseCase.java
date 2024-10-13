package site.offload.api.quest.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.member.service.MemberService;
import site.offload.api.quest.dto.request.QuestDetailListRequest;
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
import java.util.concurrent.atomic.AtomicInteger;
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
    public QuestDetailListResponse getQuestDetailList(final Long memberId, final QuestDetailListRequest request) {
        if (request.isOngoing()) {
            return QuestDetailListResponse.of(getPaginatedListOfQuestDetails(request.size(), getOngoingQuestList(memberId), request.cursor()));
        } else {
            return QuestDetailListResponse.of(getPaginatedListOfQuestDetails(request.size(), getOrderedQuestList(memberId), request.cursor()));
        }
    }

    private List<QuestDetailResponse> getOngoingQuestList(final Long memberId) {
        AtomicInteger cursorId = new AtomicInteger(1);

        final List<ProceedingQuestEntity> proceedingQuestEntities = proceedingQuestService.findAllByMemberId(memberId);
        return proceedingQuestEntities
                .stream()
                .filter(this::isValidProceedingQuest)
                .sorted(getOngoingQuestListComparator())
                .map(proceedingQuestEntity -> {
                    final QuestEntity questEntity = proceedingQuestEntity.getQuestEntity();
                    return mapToQuestDetailResponse(questEntity, memberId, cursorId);
                })
                .toList();
    }

    private List<QuestDetailResponse> getOrderedQuestList(final Long memberId) {
        final List<QuestEntity> completedQuests = getCompletedQuests(memberId);
        final List<QuestEntity> notCompletedQuests = getNotCompletedQuests(completedQuests);

        final List<QuestEntity> orderedQuests = mergeAndOrderQuests(notCompletedQuests, completedQuests);

        return mapToQuestDetailResponses(orderedQuests, memberId);
    }

    private List<QuestEntity> getCompletedQuests(final Long memberId) {
        return completeQuestService.findAllByMemberId(memberId).stream()
                .map(CompleteQuestEntity::getQuestEntity)
                .toList();
    }

    private List<QuestEntity> getNotCompletedQuests(final List<QuestEntity> completedQuests) {
        final List<Integer> completedQuestIds = completedQuests.stream()
                .map(QuestEntity::getId)
                .toList();

        return completedQuestIds.isEmpty() ? questService.findAll() : questService.findByIdNotIn(completedQuestIds)
                .stream()
                .sorted(getNotCompletedQuestListComparator())
                .toList();
    }

    private List<QuestEntity> mergeAndOrderQuests(final List<QuestEntity> notCompletedQuests, final List<QuestEntity> completedQuests) {
        final List<QuestEntity> allQuests = new ArrayList<>(notCompletedQuests);
        allQuests.addAll(completedQuests);
        return allQuests;
    }

    private boolean isValidProceedingQuest(final ProceedingQuestEntity proceedingQuestEntity) {
        return proceedingQuestEntity.isValid();
    }

    private Comparator<ProceedingQuestEntity> getOngoingQuestListComparator() {
        // 달성도 내림차순
        return Comparator.comparingDouble(proceedingQuestEntity ->
                (double) proceedingQuestEntity.getQuestEntity().getTotalRequiredClearCount() / (double) proceedingQuestEntity.getCurrentClearCount()
        );
    }

    private List<QuestEntity> getCompletedQuestList(final Long memberId) {
        return completeQuestService.findAllByMemberId(memberId)
                .stream()
                .map(CompleteQuestEntity::getQuestEntity).toList();
    }

    private Comparator<QuestEntity> getNotCompletedQuestListComparator() {
        return Comparator.comparing(QuestEntity::getId);
    }

    private List<QuestDetailResponse> mapToQuestDetailResponses(final List<QuestEntity> questEntities, final Long memberId) {
        AtomicInteger cursorId = new AtomicInteger(1);

        return questEntities.stream()
                .map(questEntity -> mapToQuestDetailResponse(questEntity, memberId, cursorId)
                )
                .toList();
    }

    private QuestDetailResponse mapToQuestDetailResponse(final QuestEntity questEntity, final Long memberId, final AtomicInteger cursorId) {
        return QuestDetailResponse.of(
                questEntity.getName(),
                questEntity.getDescription(),
                countCurrentClear(questEntity, memberId),
                questEntity.getTotalRequiredClearCount(),
                questEntity.getClearConditionText(),
                questEntity.getRewardText(),
                cursorId.getAndIncrement()
        );
    }

    private int countCurrentClear(final QuestEntity questEntity, final Long memberId) {
        final int currentClearCount = 0;
        final MemberEntity memberEntity = memberService.findById(memberId);

        if (doesCompletedQuestListContain(questEntity, memberId)) {
            return questEntity.getTotalRequiredClearCount();
        }

        if (doesQuestExistInProceedingQuest(questEntity, memberEntity)) {
            return getCurrentClearCountFromProceedingQuest(questEntity, memberEntity);
        }

        return currentClearCount;
    }

    private boolean doesCompletedQuestListContain(final QuestEntity questEntity, final Long memberId) {
        return getCompletedQuestList(memberId).contains(questEntity);
    }

    private boolean doesQuestExistInProceedingQuest(final QuestEntity questEntity, final MemberEntity memberEntity) {
        return proceedingQuestService.existsByMemberAndQuest(memberEntity, questEntity);
    }

    private int getCurrentClearCountFromProceedingQuest(final QuestEntity questEntity, final MemberEntity memberEntity) {
        return proceedingQuestService.findByMemberAndQuest(memberEntity, questEntity).getCurrentClearCount();
    }

    private List<QuestDetailResponse> getPaginatedListOfQuestDetails(final int size, final List<QuestDetailResponse> questDetails, final int cursor) {
        return questDetails.stream()
                // 첫 요청시 cursor == 0 (클라와 합의해야되는 내용)
                .filter(quest -> cursor == 0 || quest.cursorId() > cursor)
                .limit(size)
                .toList();
    }
}
