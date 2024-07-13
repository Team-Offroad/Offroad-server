package site.offload.offloadserver.db.quest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.db.quest.embeddable.RewardList;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestReward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "integer CHECK (questId > 0)")
    private int questId;

    @Embedded
    private RewardList rewardList;
}
