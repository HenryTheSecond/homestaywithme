package com.homestaywithme.domain.homestay.repository;

import com.homestaywithme.domain.homestay.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Integer> {

}
