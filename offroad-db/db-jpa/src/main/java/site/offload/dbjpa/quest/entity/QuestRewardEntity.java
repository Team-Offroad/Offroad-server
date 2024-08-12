package site.offload.dbjpa.quest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.dbjpa.BaseTimeEntity;
import site.offload.dbjpa.quest.embeddable.RewardList;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "quest_reward")
public class QuestRewardEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "integer CHECK (quest_id > 0)")
    private int questId;

    @Embedded
    private RewardList rewardList;

    @Builder
    public QuestRewardEntity(Integer id, int questId, RewardList rewardList) {
        this.id = id;
        this.questId = questId;
        this.rewardList = rewardList;
    }
}
