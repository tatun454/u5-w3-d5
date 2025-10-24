package com.jason.u5_w3_d5.Controller;

import com.jason.u5_w3_d5.Exception.ResourceNotFoundException;
import com.jason.u5_w3_d5.Service.EventService;
import com.jason.u5_w3_d5.entity.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // Eventi accessibili a tutti
    @GetMapping
    public List<Event> getAllAvailableEvents() {
        return eventService.findAllAvailableEvents();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        // La gestione del non trovato
        return eventService.eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato con ID: " + id));
    }
}