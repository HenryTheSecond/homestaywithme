package com.homestaywithme.domain.homestay.repository;

import com.homestaywithme.domain.homestay.entity.HomestayAmenity;
import com.homestaywithme.domain.homestay.entity.HomestayAmenityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomestayAmenityRepository extends JpaRepository<HomestayAmenity, HomestayAmenityId> {
}
