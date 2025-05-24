package com.homestaywithme.domain.homestay.entity;

import com.homestaywithme.domain.booking.entity.HomestayAvailability;
import com.homestaywithme.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.locationtech.jts.geom.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Homestay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private String name;
    private String description;
    private int type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private User host;

    private Integer status;
    private String phoneNumber;
    private String address;

    private Double longitude;
    private Double latitude;
    private Point geom;

    private List<String> images;
    private Integer guests;
    private Integer bedrooms;
    private Integer bathrooms;

    @ColumnTransformer(write = "?::jsonb")
    private String extraData;
    private Date createdAt;
    private Long createdBy;
    private Date updatedAt;
    private Long updatedBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "homestay", cascade = CascadeType.ALL, orphanRemoval = true)
    List<HomestayAmenity> homestayAmenities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "homestay", cascade = CascadeType.ALL, orphanRemoval = true)
    List<HomestayAvailability> homestayAvailabilities = new ArrayList<>();
}
