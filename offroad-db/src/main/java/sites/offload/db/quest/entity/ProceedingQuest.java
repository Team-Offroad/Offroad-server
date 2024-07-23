package sites.offload.db.quest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sites.offload.db.BaseTimeEntity;
import sites.offload.db.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProceedingQuest extends BaseTimeEntity {

    private static final int DEFAULT_CLEAR_COUNT = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "quest_id")
    private Quest quest;

    @Column(nullable = false)
    private int currentClearCount = DEFAULT_CLEAR_COUNT;

    private ProceedingQuest(Member member, Quest quest) {
        this.member = member;
        this.quest = quest;
    }

    public static ProceedingQuest create(Member member, Quest quest) {
        return new ProceedingQuest(member, quest);
    }

    public void addCurrentClearCount() {
        this.currentClearCount++;
    }

    public void updateCurrentClearCount(int currentClearCount) {
        this.currentClearCount = currentClearCount;
    }
}
