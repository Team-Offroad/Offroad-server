package site.offload.offloadserver.db.quest.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.db.emblem.entity.Emblem;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RewardList {

    //캐릭터모션
    private boolean isCharacterMotion;

    //쿠폰 -> 앱잼 이후에 추가
    //private String couponCode;

    //칭호
    private Emblem Emblem;
}
