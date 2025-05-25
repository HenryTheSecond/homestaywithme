package com.homestaywithme.domain.homestay.repository;

import com.homestaywithme.domain.homestay.entity.Homestay;
import com.homestaywithme.domain.homestay.usecase.searchhomestay.dto.SearchHomestayDto;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HomestayRepository extends JpaRepository<Homestay, Long> {
    @Query(value = """
            SELECT h.id AS homestayId, h.name, ST_X(h.geom) AS longitude, ST_Y(h.geom) AS latitude,
                h.description, vh.avg_price AS pricePerDay, vh.sum_price AS totalPrice, h.images
            FROM homestay h
            JOIN (
                SELECT ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326) AS geom
            ) AS d ON TRUE
            JOIN (
                SELECT h.id, AVG(ha.price) AS avg_price, SUM(ha.price) AS sum_price
                FROM homestay h
                JOIN (
                    SELECT ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326) AS geom
                ) AS d ON TRUE
                JOIN homestay_availability ha ON h.id = ha.homestay_id
                WHERE ha.date BETWEEN :from AND :to
                    AND ha.status = 0
                    AND ST_DWithin(h.geom, d.geom, :distance)
                    GROUP BY h.id
                HAVING COUNT(ha.date) = ((:to)::DATE - (:from)::DATE) + 1
            ) AS vh ON h.id = vh.id
            ORDER BY :orderBy
           """, nativeQuery = true)
    /*@Query("""
                SELECT new com.homestaywithme.domain.homestay.usecase.searchhomestay.dto.SearchHomestayDto(
                           h.id AS homestayId,
                           h.name AS name,
                           FUNCTION('ST_X', h.geom) AS longitude,
                           FUNCTION('ST_Y', h.geom) AS latitude,
                           h.description AS description,
                           AVG(ha.price) AS pricePerDay,
                           SUM(ha.price) AS totalPrice,
                           h.images AS images
                       )
                FROM Homestay h
                JOIN h.homestayAvailabilities ha
                WHERE ha.date BETWEEN :from AND :to
                  AND ha.status = 0
                  AND FUNCTION('ST_DWithin', h.geom, FUNCTION('ST_SetSRID', FUNCTION('ST_MakePoint', :longitude, :latitude), 4326), :distance) = true
                GROUP BY h.id, h.name, h.description, h.geom, h.images
                HAVING COUNT(ha.date) = (FUNCTION('DATE_PART', 'day', :to - :from) + 1)
            """)*/
    Page<SearchHomestayDto> searchHomestay(@Param("longitude") Double longitude,
                                           @Param("latitude") Double latitude,
                                           @Param("distance") Double distance,
                                           @Param("from") LocalDate from,
                                           @Param("to") LocalDate to,
                                           @Param("orderBy") String orderBy,
                                           Pageable pageable);

    @Query("""
           SELECT h
           FROM Homestay h
                JOIN FETCH h.homestayAmenities ha
                JOIN FETCH ha.amenity
           WHERE h.id = :id
           """)
    Optional<Homestay> getHomestayById(@Param("id") Long id);
}
