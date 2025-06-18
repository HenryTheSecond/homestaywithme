package com.homestaywithme.app.domain.homestay.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String icon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "amenity", cascade = CascadeType.ALL)
    private List<HomestayAmenity> homestayAmenities;
}
