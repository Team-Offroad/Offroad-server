package site.offload.dbjpa.quest.embeddable;

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

    private boolean isCharacterMotion;
    private String couponCode;
    private String emblemCode;
    private boolean isCharacter;
}
