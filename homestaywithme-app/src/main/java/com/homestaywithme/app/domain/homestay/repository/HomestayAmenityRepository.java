package com.homestaywithme.app.domain.homestay.repository;

import com.homestaywithme.app.domain.homestay.entity.HomestayAmenity;
import com.homestaywithme.app.domain.homestay.entity.HomestayAmenityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomestayAmenityRepository extends JpaRepository<HomestayAmenity, HomestayAmenityId> {
}
