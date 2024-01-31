package me.q1zz.startprotection.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import me.q1zz.startprotection.configuration.section.CacheSection;
import me.q1zz.startprotection.configuration.section.DatabaseSection;
import me.q1zz.startprotection.configuration.section.MessagesSection;
import me.q1zz.startprotection.configuration.section.ProtectionSection;

@Getter
public class PluginConfig extends OkaeriConfig {

    @Comment("PL: Konfiguracja bazy danych.")
    @Comment("EN: Database configuration.")
    private DatabaseSection database = new DatabaseSection();

    @Comment("PL: Konfiguracja ochrony startowej.")
    @Comment("EN: Start protection configuration.")
    private ProtectionSection protection = new ProtectionSection();

    @Comment("PL: Konfiguracja pamięci podręcznej (cache).")
    @Comment("EN: Cache configuration.")
    private CacheSection cache = new CacheSection();

    @Comment("PL: Konfiguracja wiadomości.")
    @Comment("EN: Messages configuration.")
    private MessagesSection messages = new MessagesSection();

}
