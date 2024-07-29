package site.offload.api.quest.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.quest.dto.response.QuestInformationResponse;
import site.offload.api.quest.dto.response.QuestResponse;
import site.offload.api.quest.service.QuestService;
import site.offload.db.quest.entity.ProceedingQuestEntity;
import site.offload.db.quest.entity.QuestEntity;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class QuestUseCase {

    private final QuestService questService;

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
}
