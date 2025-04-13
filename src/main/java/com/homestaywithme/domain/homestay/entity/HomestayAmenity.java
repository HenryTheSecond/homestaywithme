package com.homestaywithme.domain.homestay.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@IdClass(HomestayAmenityId.class)
@Data
public class HomestayAmenity {
    @Id
    @Column(name = "homestay_id")
    private Long homestayId;
    @Id
    @Column(name = "amenity_id")
    private Integer amenityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "homestay_id")
    private Homestay homestay;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "amenity_id")
    private Amenity amenity;
}
