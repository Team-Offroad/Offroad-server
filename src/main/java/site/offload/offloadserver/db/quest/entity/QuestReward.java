package site.offload.offloadserver.db.quest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.db.BaseTimeEntity;
import site.offload.offloadserver.db.quest.embeddable.RewardList;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestReward extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "integer CHECK (quest_id > 0)")
    private int questId;

    @Embedded
    private RewardList rewardList;
}
