package message;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class MessageAnnouncer {

    private final AudienceProvider audienceProvider;
    private final MiniMessage miniMessage;

    public void sendMessage(@Nullable CommandSender sender, @NotNull Message message, Map<String, String> replacements) {

        final Audience audience = this.getAudience(sender);
        final String text = message
                .clone()
                .replaceAll(replacements)
                .getText();

        switch (message.getType()) {
            case TITLE -> audience.showTitle(Title.title(this.miniMessage.deserialize(text), Component.text(" ")));
            case SUBTITLE -> audience.showTitle(Title.title(Component.text(" "), this.miniMessage.deserialize(text)));
            case ACTIONBAR -> audience.sendActionBar(this.miniMessage.deserialize(text));
            case NONE -> {}
            default -> audience.sendMessage(this.miniMessage.deserialize(text));
        }

    }
    public void sendMessage(@Nullable CommandSender sender, @NotNull Message message) {
        this.sendMessage(sender, message, new HashMap<>());
    }

    private Audience getAudience(@Nullable CommandSender sender) {

        if(sender == null) {
            return this.audienceProvider.players();
        }

        if(sender instanceof Player player) {
            return this.audienceProvider.player(player.getUniqueId());
        }

        return this.audienceProvider.console();
    }

}
