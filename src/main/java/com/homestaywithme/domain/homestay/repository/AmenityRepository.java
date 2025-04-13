package com.homestaywithme.domain.homestay.repository;

import com.homestaywithme.domain.homestay.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Integer> {

}
