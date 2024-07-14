package site.offload.offloadserver.db.quest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.offload.offloadserver.db.BaseTimeEntity;
import site.offload.offloadserver.db.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProceedingQuest extends BaseTimeEntity {

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
    private int currentClearCount = 1;

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
