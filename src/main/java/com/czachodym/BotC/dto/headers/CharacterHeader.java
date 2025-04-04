package com.czachodym.BotC.dto.headers;

import com.czachodym.BotC.model.Alignment;

public record CharacterHeader(
        Long id,
        String name,
        int maxStartNumber,
        Alignment alignment,
        String description,
        String linkToWiki
) {}
