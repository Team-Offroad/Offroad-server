package site.offload.offloadserver.db.emblem.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//칭호
@RequiredArgsConstructor
@Getter
public enum Emblem  {

    DEFAULT_EMBLEM("0001", "오프로드 스타터");
    private final String emblemCodeName;
    private final String emblemName;

    public static boolean isExistsEmblem(Emblem emblem) {
        for (Emblem thisEmblem : Emblem.values()) {
            if (emblem.equals(thisEmblem)) {
                return true;
            }
        }
        return false;
    }
}
