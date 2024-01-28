package me.q1zz.startprotection.configuration.section;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;

import java.time.Duration;

@Getter
public class ProtectionSection extends OkaeriConfig {

    @Comment("PL: Czas ochrony startowej.")
    @Comment("EN: Time of start protection.")
    private Duration time = Duration.ofMinutes(5);

}
