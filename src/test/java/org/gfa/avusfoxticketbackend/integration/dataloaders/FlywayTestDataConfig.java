package org.gfa.avusfoxticketbackend.integration.dataloaders;

import org.flywaydb.core.Flyway;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public abstract class FlywayTestDataConfig {

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure().dataSource("jdbc:h2:mem:testdb", "sa", "password").load();
    }

}
