package sites.offload.db.quest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sites.offload.db.BaseTimeEntity;
import sites.offload.db.quest.embeddable.RewardList;

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
}
