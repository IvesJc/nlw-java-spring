package com.nlw.planner.service;

import com.nlw.planner.dto.activity.ActivityDataDTO;
import com.nlw.planner.dto.activity.ActivityRequestDTO;
import com.nlw.planner.dto.activity.ActivityResponseDTO;
import com.nlw.planner.model.Activity;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public ActivityResponseDTO registerActivity(ActivityRequestDTO activityRequestDTO,
                                                Trip trip){
        Activity newActivity = new Activity(activityRequestDTO.title(),activityRequestDTO.occursAt(), trip);

        this.activityRepository.save(newActivity);
        return new ActivityResponseDTO(newActivity.getId());
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
