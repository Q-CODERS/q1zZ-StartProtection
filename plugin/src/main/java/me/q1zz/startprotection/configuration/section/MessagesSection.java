package me.q1zz.startprotection.configuration.section;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import lombok.Getter;
import me.q1zz.startprotection.message.Message;

@Getter
public class MessagesSection extends OkaeriConfig {

    @Comment("PL: Wiadomość, gdy ochrona startowa została aktywowana.")
    @Comment("EN: Message, when protection has been activated.")
    @CustomKey("protection-start")
    private Message protectionStart = Message.chat("&8» &eTwoja ochrona startowa została aktywowana!");

    @Comment("PL: Wiadomość, gdy ochrona startowa dobiegła końca.")
    @Comment("EN: Message, when start protection is over.")
    @CustomKey("protection-end")
    private Message protectionEnd = Message.subtitle("&eTwoja ochrona startowa dobiegła końca!");

    @Comment("PL: Wiadomość z czasem pozostałym do zakończenia ochrony.")
    @Comment("EN: Message with remaining time of protection.")
    @CustomKey("protection-time")
    private Message protectionTime = Message.actionbar("&8[&6&lOCHRONA&8] » &eOchrona startowa aktywna jeszcze przez:&7 {TIME}");

    @Comment("PL: Wiadomość, gdy gracz ma aktywną ochronę.")
    @Comment("EN: Message, when player has start protection.")
    @CustomKey("player-has-protection")
    private Message playerHasProtection = Message.chat("&8» &cTen gracz ma aktywną ochronę startową!");

    @Comment("PL: Wiadomość, gdy ochrona startowa została wyłączona.")
    @Comment("EN: Message, when start protection has been disabled.")
    @CustomKey("protection-disabled")
    private Message protectionDisabled = Message.chat("&8» &aPomyślnie wyłączyłeś ochronę startową!");

    @Comment("PL: Wiadomość, gdy gracz nie ma aktywnej ochrony startowej.")
    @Comment("EN: Message when player does not have active start protection.")
    @CustomKey("protection-not-active")
    private Message protectionNotActive = Message.chat("&8» &cNie masz aktywnej ochrony startowej!");

    @Comment("Wiadomość, gdy gracz użyje komendy w zły sposób.")
    @CustomKey("correct-usage")
    private Message correctUsage = Message.chat("&8» &cPoprawne użycie:&7 {USAGE}");

    @Comment("Wyświetlany format dla użycia określonej komendy.")
    @CustomKey("correct-usage-entry")
    private Message correctUsageEntry = Message.chat("&8» &c{USAGE}");

}
