package com.nlw.planner.dto.link;

import java.util.UUID;

public record LinkDataDTO(
        UUID id,
        String title,
        String url
) {
}
