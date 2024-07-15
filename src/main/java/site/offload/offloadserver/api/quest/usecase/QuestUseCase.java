package site.offload.offloadserver.api.quest.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.offloadserver.api.quest.dto.response.QuestResponse;
import site.offload.offloadserver.api.quest.dto.response.QuestInformationResponse;
import site.offload.offloadserver.api.quest.service.QuestService;
import site.offload.offloadserver.db.quest.entity.ProceedingQuest;
import site.offload.offloadserver.db.quest.entity.Quest;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class QuestUseCase {

    private final QuestService questService;

    @Transactional(readOnly = true)
    public QuestResponse getQuestInformation(Long memberId) {
        List<ProceedingQuest> proceedingQuests = questService.findProceedingQuests(memberId);
        List<Quest> quests = proceedingQuests.stream()
                .map(ProceedingQuest::getQuest)
                .toList();

        List<Double> achievement = IntStream.range(0, proceedingQuests.size())
                .mapToObj(i -> {
                    ProceedingQuest proceedingQuest = proceedingQuests.get(i);
                    Quest quest = quests.get(i);
                    return calculateAchievement(proceedingQuest.getCurrentClearCount(), quest.getTotalRequiredClearCount());
                }).toList();

        int almost = achievement.indexOf(achievement.stream().mapToDouble(Double::doubleValue).max().getAsDouble());//16.0

        ProceedingQuest recentProceedingQuest = proceedingQuests.get(0);
        Quest recentQuest = quests.get(0);
        ProceedingQuest almostProceedingQuest = proceedingQuests.get(almost);
        Quest almostQuest = quests.get(almost);

        return QuestResponse.of(QuestInformationResponse.of(recentQuest.getName(), recentProceedingQuest.getCurrentClearCount(), recentQuest.getTotalRequiredClearCount()), QuestInformationResponse.of(almostQuest.getName(), almostProceedingQuest.getCurrentClearCount(), almostQuest.getTotalRequiredClearCount()));
    }

    private Double calculateAchievement(int current, int goal) {
        return (double) current / (double) goal;
    }
}
