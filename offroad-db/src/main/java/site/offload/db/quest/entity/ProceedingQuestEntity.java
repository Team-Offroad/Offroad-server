package site.offload.db.quest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.db.BaseTimeEntity;
import site.offload.db.member.entity.MemberEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="proceeding_quest")
public class ProceedingQuestEntity extends BaseTimeEntity {

    private static final int DEFAULT_CLEAR_COUNT = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "quest_id")
    private QuestEntity questEntity;

    @Column(nullable = false)
    private int currentClearCount = DEFAULT_CLEAR_COUNT;

    private ProceedingQuestEntity(MemberEntity memberEntity, QuestEntity questEntity) {
        this.memberEntity = memberEntity;
        this.questEntity = questEntity;
    }

    public static ProceedingQuestEntity create(MemberEntity memberEntity, QuestEntity questEntity) {
        return new ProceedingQuestEntity(memberEntity, questEntity);
    }

    public void addCurrentClearCount() {
        this.currentClearCount++;
    }

    public void updateCurrentClearCount(int currentClearCount) {
        this.currentClearCount = currentClearCount;
    }
}
