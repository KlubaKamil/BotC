package com.czachodym.BotC.dto.util;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BalanceMarkDto (
    @Min(-10)
    @Max(10)
    int mark,
    @NotBlank
    String username
){}
