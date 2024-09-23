package site.offload.enums.place;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PlaceArea {
    AREA1("시간이 머무는 마을"),
    AREA2("예술가의 거리"),
    AREA3("트렌드의 시작점"),
    AREA4("해방의 숲"),
    AREA5("번영의 도시"),
    AREA6("지혜의 전당"),
    NONE("구역 없음");

    private final String placeAreaAlias;
}
