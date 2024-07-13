package site.offload.offloadserver.db.place.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.db.BaseTimeEntity;

import java.util.UUID;

//서비스에 등록된 장소
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String shortIntroduction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceCategory placeCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceArea placeArea;

    @Column(nullable = false)
    private UUID offroadCode;

    @Column(nullable = false, columnDefinition = "double CHECK (latitude >= -90 AND latitude <= 90)")
    private double latitude;

    @Column(nullable = false, columnDefinition = "double CHECK (longitude >= -180 AND longitude <= 180)")
    private double longitude;
}
