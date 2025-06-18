package com.homestaywithme.app.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Entity
@Table(name = "\"user\"")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @ColumnTransformer(write = "?::jsonb")
    private String extraData;
    private Long version;

    @CreationTimestamp
    private Date createdAt;
    private Long createdBy;

    @UpdateTimestamp
    private Date updatedAt;
    private Long updatedBy;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Profile profile;
}
