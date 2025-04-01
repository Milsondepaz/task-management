package com.milsondev.taskmanagement.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

@Configuration
@Profile("local")
public class PostgresContainerConfig {

    private static final String IMAGE_VERSION = "postgres:15.4";
    private static final DockerImageName DOCKER_IMAGE = DockerImageName.parse(IMAGE_VERSION);


    private static PostgreSQLContainer<?> postgreSQLContainer;

    @Bean
    public PostgreSQLContainer<?> postgresContainer() {
        if (postgreSQLContainer == null) {
            postgreSQLContainer = new PostgreSQLContainer<>(DOCKER_IMAGE)
                    .withDatabaseName("milsondev-db")
                    .withUsername("milsondev")
                    .withPassword("Bola10xut");
            postgreSQLContainer.start();
        }
        return postgreSQLContainer;
    }

    @Bean
    public DataSource dataSource(PostgreSQLContainer<?> postgresContainer) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(postgreSQLContainer.getJdbcUrl());
        dataSource.setUsername(postgreSQLContainer.getUsername());
        dataSource.setPassword(postgreSQLContainer.getPassword());
        return dataSource;
    }
}
