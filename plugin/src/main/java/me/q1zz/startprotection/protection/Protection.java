package me.q1zz.startprotection.protection;

import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class Protection extends Document {

    private Instant endOfProtection;

    @NotNull
    public UUID getPlayerId() {
        return this.getPath().toUUID();
    }

    public void startProtection(@NotNull Duration time) {
        this.endOfProtection = Instant.now().plus(time);
    }

    public void endProtection() {
        this.endOfProtection = Instant.EPOCH;
    }

    public boolean isActive() {
        return this.hasBeenActivated() && !this.isExpired();
    }

    public boolean hasBeenActivated() {
        return this.endOfProtection != null;
    }

    public boolean isEnd() {
        return this.endOfProtection != null && this.endOfProtection.equals(Instant.EPOCH);
    }

    public boolean isExpired() {
        return this.endOfProtection != null && (this.endOfProtection.equals(Instant.EPOCH) || Instant.now().isAfter(this.endOfProtection));
    }

}
