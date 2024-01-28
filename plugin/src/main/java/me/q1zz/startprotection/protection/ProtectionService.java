package me.q1zz.startprotection.protection;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class ProtectionService {

    private final ProtectionRepository protectionRepository;
    private final Cache<UUID, Protection> protectionCache;

    @NotNull
    public List<Protection> getProtections() {
        return new ArrayList<>(this.protectionCache.asMap().values());
    }

    @NotNull
    public Protection getProtection(@NotNull UUID playerId) {
        return this.protectionCache.get(playerId, this.protectionRepository::findOrCreateByPath);
    }

    @NotNull
    public CompletableFuture<Protection> saveProtection(@NotNull Protection protection) {
        return CompletableFuture.supplyAsync(() -> this.protectionRepository.save(protection));
    }

    public void removeFromCache(@NotNull UUID playerId) {
        this.protectionCache.invalidate(playerId);
    }

}
