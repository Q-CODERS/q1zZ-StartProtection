package me.q1zz.startprotection.configuration.section;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import lombok.Getter;

import java.time.Duration;

@Getter
public class CacheSection extends OkaeriConfig {

    @Comment("PL: Czas przez jaki gracz będzie przechowywany w pamięci podręcznej (cache).")
    @Comment("EN: Time which the player will be cached.")
    private Duration time = Duration.ofMinutes(30);

    @Comment("PL: Czy gracz powinien zostać usunięty z pamięci podręcznej cache gdy wyjdzie z serwera?")
    @Comment("EN: Should the player will be removed from cache when leave the server?")
    @CustomKey("remove-when-leave")
    private boolean removeWhenLeave = true;

}
