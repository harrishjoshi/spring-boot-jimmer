package com.harrish.jimmer.user.core.repositories;

import com.harrish.jimmer.user.core.entities.User;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}