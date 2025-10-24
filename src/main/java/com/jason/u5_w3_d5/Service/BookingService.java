package com.jason.u5_w3_d5.Service;

import com.jason.u5_w3_d5.Exception.ResourceNotFoundException;
import com.jason.u5_w3_d5.entity.Booking;
import com.jason.u5_w3_d5.entity.Event;
import com.jason.u5_w3_d5.entity.User;
import com.jason.u5_w3_d5.repo.BookingRepository;
import com.jason.u5_w3_d5.repo.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;

    // prenotazione posto
    @Transactional
    public Booking bookEvent(Long eventId, User user) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato."));

        if (event.getAvailableSeats() <= 0) {
            throw new IllegalStateException("Nessun posto disponibile.");//eccezione per mancanza di posto
        }


        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepository.save(event);


        Booking booking = new Booking(null, user, event, LocalDateTime.now());
        return bookingRepository.save(booking);

    }
    //provo già a fare l'extra o almeno a buttarci una base
    // visualizzazione prenotazioni dell'utente
    public List<Booking> findMyBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    // annullamento prenotazione
    @Transactional
    public void cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findByIdAndUserId(bookingId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Prenotazione non trovata o non di proprietà."));
        Event event = booking.getEvent();


        event.setAvailableSeats(event.getAvailableSeats() + 1);
        eventRepository.save(event);


        bookingRepository.delete(booking);
    }
}