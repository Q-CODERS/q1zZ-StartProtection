package me.q1zz.startprotection.database;

import com.zaxxer.hikari.HikariConfig;
import eu.okaeri.configs.json.gson.JsonGsonConfigurer;
import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.PersistencePath;
import eu.okaeri.persistence.document.ConfigurerProvider;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.jdbc.H2Persistence;
import eu.okaeri.persistence.jdbc.MariaDbPersistence;
import eu.okaeri.persistence.raw.RawPersistence;
import lombok.RequiredArgsConstructor;
import me.q1zz.startprotection.configuration.PluginConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public class DatabaseManager {

    private final PluginConfig pluginConfig;
    private final File dataFolder;
    private DocumentPersistence persistence;

    public void connect() {

        final PersistencePath basePath = PersistencePath.of(this.pluginConfig.getDatabase().getPrefix());
        final DatabaseType databaseType = this.pluginConfig.getDatabase().getType();

        final HikariConfig hikariConfig = this.getHikariConfig(this.pluginConfig.getDatabase().getHostname(), this.pluginConfig.getDatabase().getPort(), this.pluginConfig.getDatabase().getBase(), this.pluginConfig.getDatabase().getUsername(), this.pluginConfig.getDatabase().getPassword(), this.pluginConfig.getDatabase().isUseSSL(), databaseType);
        final ConfigurerProvider configurer = JsonGsonConfigurer::new;

        final RawPersistence rawPersistence = switch (databaseType) {
            case MYSQL -> new MariaDbPersistence(basePath, hikariConfig);
            case H2 -> new H2Persistence(basePath, hikariConfig);
        };

        this.persistence = new DocumentPersistence(rawPersistence, configurer);
    }

    @NotNull
    public DocumentPersistence persistence() {
        return this.persistence;
    }

    public void registerCollections(@NotNull PersistenceCollection... collections) {
        for (final PersistenceCollection collection : collections)
            this.persistence.registerCollection(collection);
    }

    @NotNull
    private HikariConfig getHikariConfig(@Nullable String host, int port, @NotNull String database, @NotNull String username, @NotNull String password, boolean useSSL, @NotNull DatabaseType databaseType) {

        final HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        switch (databaseType) {
            case MYSQL -> {
                hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
                hikariConfig.setJdbcUrl(String.format("jdbc:mysql://%s:%d/%s", host, port, database));
            }
            case H2 -> {
                hikariConfig.setDriverClassName("org.h2.Driver");
                hikariConfig.setJdbcUrl(String.format("jdbc:h2:./%s/database;%s", this.dataFolder.getPath(), "mode=mysql"));
            }
            default -> throw new IllegalArgumentException("Unsupported database type: " + databaseType.name());
        }

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("useLocalSessionState", "true");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", "true");
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", "true");
        hikariConfig.addDataSourceProperty("maintainTimeStats", "false");
        hikariConfig.addDataSourceProperty("useSSL", String.valueOf(useSSL));

        return hikariConfig;
    }

    public void close() {
        try {

            if(this.persistence == null) {
                return;
            }

            this.persistence.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
