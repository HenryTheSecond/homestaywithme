package com.homestaywithme.consumer.repository;

import com.homestaywithme.consumer.entity.HomestayAvailability;
import com.homestaywithme.consumer.entity.HomestayAvailabilityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomestayAvailabilityRepository extends JpaRepository<HomestayAvailability, HomestayAvailabilityId> {
}
