package com.nlw.planner.service;

import com.nlw.planner.dto.link.LinkDataDTO;
import com.nlw.planner.dto.link.LinkRequestDTO;
import com.nlw.planner.dto.link.LinkResponseDTO;
import com.nlw.planner.model.Link;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public LinkResponseDTO registerLink(LinkRequestDTO linkRequestDTO, Trip trip){
        Link newLink = new Link(linkRequestDTO.title(), linkRequestDTO.url(), trip);

        this.linkRepository.save(newLink);
        return new LinkResponseDTO(newLink.getId());
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
