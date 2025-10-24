package com.jason.u5_w3_d5.repo;


import com.jason.u5_w3_d5.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByAvailableSeatsGreaterThan(int seats);

}