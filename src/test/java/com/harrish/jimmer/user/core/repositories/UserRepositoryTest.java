package com.harrish.jimmer.user.core.repositories;

import com.harrish.jimmer.user.core.entities.User;
import com.harrish.jimmer.user.core.entities.UserDraft;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = com.harrish.jimmer.SpringBootJimmerApplication.class)
@ActiveProfiles("test")
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUser() {
        // Create a User object using Jimmer's generated builder
        User user = UserDraft.$.produce(draft -> {
            draft.setUsername("testuser");
            draft.setEmail("test@example.com");
            draft.setFirstName("Test");
            draft.setLastName("User");
            draft.setActive(true);
            draft.setCreatedAt(LocalDateTime.now());
            draft.setUpdatedAt(LocalDateTime.now());
        });

        // When
        userRepository.save(user);
        Optional<User> foundUser = userRepository.findByUsername("testuser");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().username()).isEqualTo("testuser");
        assertThat(foundUser.get().email()).isEqualTo("test@example.com");
    }
}