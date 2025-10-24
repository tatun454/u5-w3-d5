package com.jason.u5_w3_d5.Controller;

import com.jason.u5_w3_d5.Service.EventService;
import com.jason.u5_w3_d5.Service.UserService;
import com.jason.u5_w3_d5.entity.Event;
import com.jason.u5_w3_d5.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/organizer/events") // Protetta da hasAuthority("ORGANIZZATORE_EVENTI")
@RequiredArgsConstructor
public class OrganizerEventController {

    private final EventService eventService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody Event event, Authentication authentication) {
        User organizer = userService.findByUsername(authentication.getName());
        return eventService.createEvent(event, organizer);
    }

    @GetMapping("/my")
    public List<Event> getMyOrganizedEvents(Authentication authentication) {
        User organizer = userService.findByUsername(authentication.getName());
        return eventService.findEventsByOrganizer(organizer);
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(@PathVariable Long eventId, @RequestBody Event eventDetails, Authentication authentication) {
        User organizer = userService.findByUsername(authentication.getName());
        return eventService.updateEvent(eventId, eventDetails, organizer);
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long eventId, Authentication authentication) {
        User organizer = userService.findByUsername(authentication.getName());
        eventService.deleteEvent(eventId, organizer);
    }
}