package me.q1zz.startprotection.configuration.section;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import lombok.Getter;
import me.q1zz.startprotection.database.DatabaseType;

@Getter
public class DatabaseSection extends OkaeriConfig {

    @Comment("PL: Typ bazy danych.")
    @Comment("PL: Dostępne: MYSQL, H2")
    @Comment(" ")
    @Comment("EN: Database type.")
    @Comment("EN: Available: MYSQL, H2")
    private DatabaseType type = DatabaseType.MYSQL;

    @Comment("PL: Prefix dla bazy danych.")
    @Comment("EN: Database prefix.")
    private String prefix = "server";

    @Comment("PL: Adres hosta bazy danych.")
    @Comment("EN: Database host address.")
    private String hostname = "localhost";

    @Comment("PL: Hasło do bazy danych.")
    @Comment("EN: Database password.")
    private String password = "password123!@#";

    @Comment("PL: Nazwa użytkownika do bazy danych.")
    @Comment("EN: Database username.")
    private String username = "root";

    @Comment("PL: Czy plugin powinien wykorzystywać ssl?")
    @Comment("EN: Should plugin will use ssl?")
    @CustomKey("use-ssl")
    private boolean useSSL = true;

    @Comment("PL: Port bazy danych.")
    @Comment("EN: Database port.")
    private int port = 3306;

    @Comment("PL: Nazwa bazy danych w której zostanie stworzona tabela.")
    @Comment("EN: Name of the database, which table will be created.")
    private String base = "q1zz_startprotection";

}
