package com.jason.u5_w3_d5.Controller;

import com.jason.u5_w3_d5.Service.BookingService;
import com.jason.u5_w3_d5.Service.UserService;
import com.jason.u5_w3_d5.entity.Booking;
import com.jason.u5_w3_d5.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    @PostMapping("/book/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Booking bookEvent(@PathVariable Long eventId, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        return bookingService.bookEvent(eventId, user);
    }

    @GetMapping("/my")
    public List<Booking> getMyBookings(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        return bookingService.findMyBookings(user.getId());
    }

    @DeleteMapping("/cancel/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBooking(@PathVariable Long bookingId, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        bookingService.cancelBooking(bookingId, user.getId());
    }
}