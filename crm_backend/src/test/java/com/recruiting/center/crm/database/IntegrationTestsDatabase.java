package com.recruiting.center.crm.database;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.manipulation.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;



@SpringBootTest
@Testcontainers
@DirtiesContext
public class IntegrationTestsDatabase {

    @Container
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("test-db")
                    .withUsername("test")
                    .withPassword("test");
    @Container
    private static final GenericContainer<?> REDIS_CONTAINER =
            new GenericContainer<>(DockerImageName.parse("redis:7"))
                    .withExposedPorts(6379);

    @DynamicPropertySource
    public static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);


        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379));
    }

    @BeforeAll
    public static void runContainer() {
        POSTGRE_SQL_CONTAINER.start();
        REDIS_CONTAINER.start();
    }


    @AfterAll
    public static void stopContainer() {
        POSTGRE_SQL_CONTAINER.stop();
        REDIS_CONTAINER.stop();
    }
}
