package site.offload.dbjpa.place.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.dbjpa.BaseTimeEntity;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

import java.util.Objects;

//서비스에 등록된 장소
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="place")
public class PlaceEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String shortIntroduction;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlaceCategory placeCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceArea placeArea;

    @Column(nullable = false)
    private String offroadCode;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String categoryImageUrl;

    @Column(nullable = false)
    private String couponAuthCode;

    public boolean isValidOffroadCode(String offroadCode) {
        return Objects.equals(this.offroadCode, offroadCode);
    }

    @Builder
    public PlaceEntity(String name, String address, String shortIntroduction, PlaceCategory placeCategory, PlaceArea placeArea, String offroadCode, double latitude, double longitude, String categoryImageUrl, String couponAuthCode) {
        this.name = name;
        this.address = address;
        this.shortIntroduction = shortIntroduction;
        this.placeCategory = placeCategory;
        this.placeArea = placeArea;
        this.offroadCode = offroadCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.categoryImageUrl = categoryImageUrl;
        this.couponAuthCode = couponAuthCode;
    }
}
