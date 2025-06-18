package com.homestaywithme.app.domain.homestay.repository;

import com.homestaywithme.app.domain.homestay.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Integer> {

}
