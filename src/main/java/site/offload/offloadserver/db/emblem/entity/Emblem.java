package site.offload.offloadserver.db.emblem.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.offload.offloadserver.api.exception.NotFoundException;
import site.offload.offloadserver.api.message.ErrorMessage;

//칭호
@RequiredArgsConstructor
@Getter
public enum Emblem {

    DEFAULT_EMBLEM("0001", "오프로드 스타터");
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
