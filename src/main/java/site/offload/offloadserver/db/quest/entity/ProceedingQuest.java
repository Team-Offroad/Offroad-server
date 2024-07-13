package site.offload.offloadserver.db.quest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.db.member.entity.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProceedingQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quest quest;

    @Column(nullable = false, columnDefinition = "integer CHECK (current_clear_count <= SELECT total_required_clear_count FROM quest WHERE quest.id = proceeding_quest.quest_id")
    private int currentClearCount = 1;
}
