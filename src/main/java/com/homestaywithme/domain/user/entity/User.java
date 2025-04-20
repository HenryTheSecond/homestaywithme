package com.homestaywithme.domain.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String fullname;
    private Integer type;
    private Integer status;
    private String extraData;
    private Long version;
    private Date createdAt;
    private Long createdBy;
    private Date updatedAt;
    private Long updatedBy;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Profile profile;
}
