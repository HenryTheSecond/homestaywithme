package com.homestaywithme.domain.homestay.repository;

import com.homestaywithme.domain.booking.entity.HomestayAvailability;
import com.homestaywithme.domain.booking.entity.HomestayAvailabilityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HomestayAvailabilityRepository extends JpaRepository<HomestayAvailability, HomestayAvailabilityId> {
    List<HomestayAvailability> findByHomestayIdAndDateBetween(Long homestayId, LocalDate from, LocalDate to);
}
