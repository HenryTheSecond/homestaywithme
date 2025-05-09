package com.homestaywithme.domain.booking.repository.homestayavailability;

import com.homestaywithme.domain.booking.entity.HomestayAvailability;
import com.homestaywithme.domain.booking.entity.HomestayAvailabilityId;
import com.homestaywithme.domain.booking.repository.homestayavailability.model.SetPriceHomestayAvailability;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface HomestayAvailabilityRepository extends JpaRepository<HomestayAvailability, HomestayAvailabilityId> {
    List<HomestayAvailability> findByHomestayIdAndDateBetween(Long homestayId, LocalDate from, LocalDate to);

    @Query("""
           SELECT new HomestayAvailability(ha.homestayId, ha.date, ha.price, ha.status)
           FROM HomestayAvailability ha
           WHERE ha.homestayId = :homestayId AND
                ha.date BETWEEN :from AND :to AND
                ha.status = 0
           """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<HomestayAvailability> findAvailableByHomestayIdAndDateWithLock(@Param("homestayId") Long homestayId,
                                                                        @Param("from") LocalDate from,
                                                                        @Param("to") LocalDate to);

    @Query("""
            SELECT new com.homestaywithme.domain.booking.repository.homestayavailability.model.SetPriceHomestayAvailability(h.id, CAST(AVG(ha.price) AS BigDecimal), MAX(ha.date))
            FROM Homestay h
            JOIN HomestayAvailability ha ON h.id = ha.homestayId
            WHERE ha.date >= :date AND (:homestayId is NULL OR h.id > :homestayId)
            GROUP BY h.id
            ORDER BY h.id
           """)
    List<SetPriceHomestayAvailability> getSetPriceHomestayAvailability(@Param("homestayId") Long homestayId,
                                                                       @Param("date") LocalDate date,
                                                                       Pageable pageable);

    @Modifying
    @Query("""
           UPDATE HomestayAvailability ha
           SET ha.status = :status
           WHERE ha.homestayId = :homestayId AND ha.date = :date
           """)
    void updateStatus(@Param("homestayId") Long homestayId,
                      @Param("date") LocalDate date,
                      @Param("status") Integer status);
}
