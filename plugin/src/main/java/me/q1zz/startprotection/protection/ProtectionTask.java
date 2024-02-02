package me.q1zz.startprotection.protection;

import lombok.RequiredArgsConstructor;
import me.q1zz.startprotection.configuration.PluginConfig;
import me.q1zz.startprotection.message.MessageAnnouncer;
import me.q1zz.startprotection.util.DurationUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@RequiredArgsConstructor
public class ProtectionTask extends BukkitRunnable {

    private final PluginConfig pluginConfig;
    private final MessageAnnouncer announcer;
    private final ProtectionService protectionService;

    @Override
    public void run() {
        for (final Protection protection : this.protectionService.getProtections()) {

            final Player player = Bukkit.getPlayer(protection.getPlayerId());

            if(player == null) {
                continue;
            }

            if(protection.isEnd()) {
                continue;
            }

            if(protection.isExpired()) {

                protection.endProtection();

                this.protectionService.saveProtection(protection);
                this.announcer.sendMessage(player, this.pluginConfig.getMessages().getProtectionEnd());

                continue;
            }

            final Instant endOfProtection = protection.getEndOfProtection();
            final Duration remainingTime = Duration.between(Instant.now(), endOfProtection);

            this.announcer.sendMessage(player, this.pluginConfig.getMessages().getProtectionTime(), Map.of(
                    "{TIME}", DurationUtil.format(remainingTime)));
        }
    }

}
