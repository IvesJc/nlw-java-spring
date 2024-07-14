package com.nlw.planner.service;

import com.nlw.planner.dto.trip.TripDTO;
import com.nlw.planner.dto.trip.TripRequestDTO;
import com.nlw.planner.dto.trip.TripResponseDTO;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ParticipantService participantService;

    public Trip findById(UUID id) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);

        return optionalTrip.orElseThrow(RuntimeException::new);
    }

    public Trip confirmTrip(UUID id) {
        Optional<Trip> trip = tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip newTrip = trip.get();
            newTrip.setConfirmed(true);

            this.tripRepository.save(newTrip);
            this.participantService.triggerConfirmationEmailToParticipants(id);
            return newTrip;
        }
        throw new RuntimeException();
    }

    public Trip createTrip(TripRequestDTO tripRequestDTO){
        Trip newTrip = new Trip(tripRequestDTO);

        this.tripRepository.save(newTrip);
        this.participantService.registerParticipants(
                tripRequestDTO.emails_to_invite(),
                newTrip);
        return newTrip;
    }

    public Trip updateTrip(UUID id, TripRequestDTO tripDTO){
        Optional<Trip> trip = tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip newTrip = trip.get();
            newTrip.setStartsAt(LocalDateTime.parse(tripDTO.starts_at(),
                    DateTimeFormatter.ISO_DATE_TIME));
            newTrip.setEndsAt(LocalDateTime.parse(tripDTO.ends_at(),
                    DateTimeFormatter.ISO_DATE_TIME));
            newTrip.setDestination(tripDTO.destination());
            this.tripRepository.save(newTrip);
            return newTrip;
        }
        throw new RuntimeException();
    }
}
