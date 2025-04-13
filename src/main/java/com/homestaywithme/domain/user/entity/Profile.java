package com.homestaywithme.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@Builder
public class Profile {
    @Id
    @Column(name = "user_id")
    private Long Id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private String avatar;
    private String work;
    private String about;

    private List<String> interests;

    private Long version;
    private Date createdAt;
    private Long createdBy;
    private Date updatedAt;
    private Long updatedBy;
}
