package com.homestaywithme.domain.homestay.repository;

import com.homestaywithme.domain.homestay.entity.Homestay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomestayRepository extends JpaRepository<Homestay, Long> {

}
