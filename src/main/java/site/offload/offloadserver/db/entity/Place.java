package site.offload.offloadserver.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

//서비스에 등록된 장소
@Entity
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Place extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String shortIntroduction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "TEXT")
    private PlaceCategory placeCategory;

    @Column(nullable = false, columnDefinition = "float CHECK (latitude < -90) OR (latitude > 90)")
    private float latitude;

    @Column(nullable = false, columnDefinition = "float CHECK (longitude < -180) OR (longitude > 180)")
    private float longitude;

    @Builder
    private Place(String name, String address, String shortIntroduction,
     PlaceCategory placeCategory, float latitude, float longitude) {
        this.name = name;
        this.address = address;
        this.shortIntroduction = shortIntroduction;
        this.placeCategory = placeCategory;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
