package site.offload.offloadserver.db.emblem.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.offload.offloadserver.api.exception.NotFoundException;
import site.offload.offloadserver.api.message.ErrorMessage;

//칭호
@RequiredArgsConstructor
@Getter
public enum Emblem {

    WALKING_MANIA("TT000001", "산책 매니아"),
    GAME_MASTER("TT000002", "게임 마스터"),
    CONSISTENT_EXPLORER("TT000003", "꾸준한 탐험가"),
    STUBBORN_EXPLORER("TT000004", "줏대있는 탐험가"),
    CULTURAL_LIFE("TT000005", "문화생활"),
    WHISTLE_OF_THE_STADIUM("TT000006", "경기장의 휘슬 소리"),
    FOOD_HUNTER("TT000007", "맛집 헌터"),
    HUNDREDTH_RESTAURANT("TT000008", "너와 100번째 식당"),
    OFFROAD_STARTER("TT000009", "오프로드 스타터"),
    CAFE_FRIEND("TT000010", "카페는 내 친구"),
    CAFE_ADDICT("TT000011", "카페 중독"),
    GREAT_FIRST_STEP("TT000012", "위대한 첫 걸음"),
    NOVICE_EXPLORER("TT000013", "왕초보 탐험가"),
    BEGINNER_EXPLORER("TT000014", "초보 탐험가"),
    VETERAN_EXPLORER("TT000015", "베테랑 탐험가"),
    EXPLORATION_EXPERT("TT000016", "탐험 전문가"),
    ESSENCE_OF_EXPLORATION("TT000017", "탐험의 정수"),
    EXPLORATION_CHAMPION("TT000018", "탐험 챔피언"),
    LEGEND_OF_EXPLORATION("TT000019", "탐험의 전설"),
    GOD_OF_EXPLORATION("TT000020", "탐험의 신");
    private final String emblemCode;
    private final String emblemName;

    public static boolean isExistsEmblem(final String emblemCode) {
        for (Emblem thisEmblem : Emblem.values()) {
            if (thisEmblem.getEmblemCode().equals(emblemCode)) {
                return true;
            }
        }
        return false;
    }

    public static Emblem getEmblemByCode(final String emblemCode) {
        for (Emblem thisEmblem : Emblem.values()) {
            if (thisEmblem.getEmblemCode().equals(emblemCode)) {
                return thisEmblem;
            }
        }
        throw new NotFoundException(ErrorMessage.EMBLEM_CODE_NOTFOUND_EXCEPTION);
    }

}
