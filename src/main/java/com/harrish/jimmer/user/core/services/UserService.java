package com.harrish.jimmer.user.core.services;

import com.harrish.jimmer.user.api.dtos.CreateUserRequest;
import com.harrish.jimmer.user.api.dtos.UpdateUserRequest;
import com.harrish.jimmer.user.api.dtos.UserResponse;
import com.harrish.jimmer.user.api.mappers.UserMapper;
import com.harrish.jimmer.user.core.entities.User;
import com.harrish.jimmer.user.core.entities.UserDraft;
import com.harrish.jimmer.user.core.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        String username = request.username();
        String email = request.email();

        if (existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists: " + username);
        }

        if (existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists: " + email);
        }

        User user = UserDraft.$.produce(draft -> {
            draft.setUsername(username);
            draft.setEmail(email);
            draft.setFirstName(request.firstName());
            draft.setLastName(request.lastName());
            draft.setActive(true);
            draft.setCreatedAt(LocalDateTime.now());
            draft.setUpdatedAt(LocalDateTime.now());
        });

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserResponse);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserResponseList(users);
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));

        String username = request.username();
        String email = request.email();

        // Check if username is already taken by another user
        if (username != null && !username.equals(existingUser.username()) && existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists: " + username);
        }

        // Check if email is already taken by another user
        if (email != null && !email.equals(existingUser.email()) && existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists: " + email);
        }

        // Create a new User with updated values using UserDraft
        User updatedUser = UserDraft.$.produce(existingUser, draft -> {
            if (request.username() != null) {
                draft.setUsername(username);
            }
            if (request.email() != null) {
                draft.setEmail(email);
            }
            if (request.firstName() != null) {
                draft.setFirstName(request.firstName());
            }
            if (request.lastName() != null) {
                draft.setLastName(request.lastName());
            }
            draft.setUpdatedAt(LocalDateTime.now());
        });

        User savedUser = userRepository.save(updatedUser);
        return userMapper.toUserResponse(savedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}