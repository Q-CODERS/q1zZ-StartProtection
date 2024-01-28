package me.q1zz.startprotection.protection;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;

import java.util.UUID;

@DocumentCollection(path = "protection", keyLength = 36)
public interface ProtectionRepository extends DocumentRepository<UUID, Protection> {

}
