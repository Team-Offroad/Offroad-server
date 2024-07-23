package sites.offload.db.quest.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RewardList {

    //캐릭터모션
    private boolean isCharacterMotion;

    //쿠폰 -> 앱잼 이후에 추가
    //private String couponCode;

    //칭호
    private String emblemCode;
}
