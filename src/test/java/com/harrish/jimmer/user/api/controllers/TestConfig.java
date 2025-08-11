package com.harrish.jimmer.user.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harrish.jimmer.user.api.dtos.UserResponse;
import com.harrish.jimmer.user.api.mappers.UserMapper;
import com.harrish.jimmer.user.core.entities.User;
import com.harrish.jimmer.user.core.services.UserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class TestConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    @Bean
    @Primary
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    @Primary
    public UserMapper userMapper() {
        // Create a real implementation of UserMapper that works in tests
        return new UserMapper() {
            @Override
            public UserResponse toUserResponse(User user) {
                return new UserResponse(
                        user.id(),
                        user.username(),
                        user.email(),
                        user.firstName(),
                        user.lastName(),
                        user.active(),
                        user.createdAt(),
                        user.updatedAt()
                );
            }

            @Override
            public List<UserResponse> toUserResponseList(List<User> users) {
                return users.stream()
                        .map(this::toUserResponse)
                        .collect(Collectors.toList());
            }
        };
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}