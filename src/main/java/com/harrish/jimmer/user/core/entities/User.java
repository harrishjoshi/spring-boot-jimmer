package com.harrish.jimmer.user.core.entities;

import org.babyfish.jimmer.sql.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public interface User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    @Key
    String username();

    @Key
    String email();

    String firstName();

    String lastName();

    boolean active();

    @Column(name = "created_at")
    LocalDateTime createdAt();

    @Column(name = "updated_at")
    LocalDateTime updatedAt();
}