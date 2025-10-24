package com.jason.u5_w3_d5.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        //tutti possoono visualizzano la lista degli eventi disponibili
                        .requestMatchers(HttpMethod.GET, "/api/events").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()

                        // Gestione eventi per gli organizzatori
                        .requestMatchers("/api/organizer/events/**").hasAuthority("ORGANIZZATORE_EVENTI")

                        // Prenotazione e visualizzazione/annullamento per utenti normali
                        .requestMatchers("/api/bookings/**").hasAuthority("UTENTE_NORMALE")

                        .anyRequest().authenticated() // ovviamente tutto questo richiede autenticazione
                )
                .httpBasic(withDefaults());

        return http.build();
    }
}