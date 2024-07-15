package com.nlw.planner.service;

import com.nlw.planner.dto.activity.ActivityDataDTO;
import com.nlw.planner.dto.activity.ActivityRequestDTO;
import com.nlw.planner.dto.activity.ActivityResponseDTO;
import com.nlw.planner.model.Activity;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repository.ActivityRepository;
import com.nlw.planner.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private TripRepository tripRepository;

    public ActivityResponseDTO registerActivity(ActivityRequestDTO activityRequestDTO,
                                                UUID id
    ) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);

        if (optionalTrip.isPresent()) {
            Trip newTrip = optionalTrip.get();

            Activity newActivity = new Activity(
                    activityRequestDTO.title(),
                    activityRequestDTO.occursAt(),
                    newTrip);


            this.activityRepository.save(newActivity);
            return new ActivityResponseDTO(newActivity.getId());
        }
        throw new RuntimeException();
    }

    public List<ActivityDataDTO> getAllActivitiesFromEvent(UUID id) {
        return activityRepository.findByTripId(id).
                stream().
                map(activity -> new ActivityDataDTO(
                        activity.getId(),
                        activity.getTitle(),
                        activity.getOccursAt())).
                toList();

    }

}
