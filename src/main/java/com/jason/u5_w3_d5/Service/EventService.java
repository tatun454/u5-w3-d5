package com.jason.u5_w3_d5.Service;


import com.jason.u5_w3_d5.Exception.AccessDeniedException;
import com.jason.u5_w3_d5.Exception.ResourceNotFoundException;
import com.jason.u5_w3_d5.entity.Event;
import com.jason.u5_w3_d5.entity.User;
import com.jason.u5_w3_d5.repo.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    @Transactional
    public Event createEvent(Event event, User organizer) {
        event.setOrganizer(organizer); // Riferimento al creatore
        event.setAvailableSeats(event.getTotalSeats()); // Imposta i posti disponibili
        return eventRepository.save(event);
    }

    // tutti visualizzano gli eventi con posti disponibili
    public List<Event> findAllAvailableEvents() {
        return eventRepository.findByAvailableSeatsGreaterThan(0);
    }

    // un organizzatore può vedere i propri eventi
    public List<Event> findEventsByOrganizer(User organizer) {
        return organizer.getOrganizedEvents();
    }

    // l'organizzatore può aggiornare i propri eventi
    @Transactional
    public Event updateEvent(Long eventId, Event details, User organizer) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato."));

        // verifica Autorizzazione
        if (!event.getOrganizer().getId().equals(organizer.getId())) {
            throw new AccessDeniedException("Non sei l'organizzatore di questo evento.");
        }


        return eventRepository.save(event);
    }
}