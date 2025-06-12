package com.czachodym.BotC.dto;

import com.czachodym.BotC.dto.details.character.CharacterDetails;
import com.czachodym.BotC.model.Alignment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CharacterDto(
        Long id,
        @NotBlank
        String name,
        @Min(1)
        int maxStartNumber,
        @NotNull
        Alignment alignment,
        @NotBlank
        String description,
        String linkToWiki,
        String tips,
        boolean imageUploaded,
        CharacterDetails characterDetails
){}
