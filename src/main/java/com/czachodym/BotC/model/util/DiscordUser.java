package com.czachodym.BotC.model.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DiscordUser(
        String id,
        String username,
//        String avatar,
//        String discriminator,
//        @JsonProperty("public_flags") int publicFlags,
//        int flags,
//        String banner,
//        @JsonProperty("accent_color") Integer accentColor,
        @JsonProperty("global_name") String globalName
//        @JsonProperty("avatar_decoration_data") Object avatarDecorationData,
//        Object collectibles,
//        @JsonProperty("display_name_styles") Object displayNameStyles,
//        @JsonProperty("banner_color") String bannerColor,
//        Object clan,
//        @JsonProperty("primary_guild") String primaryGuild,
//        @JsonProperty("mfa_enabled") boolean mfaEnabled,
//        String locale,
//        @JsonProperty("premium_type") int premiumType
) {}