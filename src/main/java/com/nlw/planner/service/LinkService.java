package com.nlw.planner.service;

import com.nlw.planner.dto.link.LinkDataDTO;
import com.nlw.planner.dto.link.LinkRequestDTO;
import com.nlw.planner.dto.link.LinkResponseDTO;
import com.nlw.planner.model.Link;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repository.LinkRepository;
import com.nlw.planner.repository.TripRepository;
import com.nlw.planner.service.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private TripRepository tripRepository;

    public LinkResponseDTO registerLink(LinkRequestDTO linkRequestDTO, UUID id) {
        Optional<Trip> trip = tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip newTrip = trip.get();


            Link newLink = new Link(linkRequestDTO.title(), linkRequestDTO.url(), newTrip);

            this.linkRepository.save(newLink);
            return new LinkResponseDTO(newLink.getId());
        }
        throw new IdNotFoundException();
    }

    public List<LinkDataDTO> getAllLinksFromEvent(UUID id) {
        return linkRepository.findByTripId(id).
                stream().
                map(link -> new LinkDataDTO(
                        link.getId(),
                        link.getTitle(),
                        link.getUrl())).
                toList();
    }
}
