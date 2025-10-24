package com.jason.u5_w3_d5.repo;


import com.jason.u5_w3_d5.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    Optional<Booking> findByIdAndUserId(Long bookingId, Long userId);
}