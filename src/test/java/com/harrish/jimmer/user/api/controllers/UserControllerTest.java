package com.harrish.jimmer.user.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harrish.jimmer.user.api.dtos.CreateUserRequest;
import com.harrish.jimmer.user.api.dtos.UpdateUserRequest;
import com.harrish.jimmer.user.api.dtos.UserResponse;
import com.harrish.jimmer.user.core.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = com.harrish.jimmer.SpringBootJimmerApplication.class)
@AutoConfigureMockMvc
@Import(TestConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Test
    void createUser_ValidRequest_ReturnsCreated() throws Exception {
        // Arrange
        CreateUserRequest request = new CreateUserRequest(
                "testuser",
                "test@example.com",
                "Test",
                "User"
        );

        UserResponse mockResponse = createMockUserResponse(
                1L, "testuser", "test@example.com", "Test", "User");
        when(userService.createUser(any(CreateUserRequest.class)))
                .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void getUserById_ExistingUser_ReturnsUser() throws Exception {
        // Arrange
        UserResponse mockResponse = createMockUserResponse(
                1L, "testuser", "test@example.com", "Test", "User");
        when(userService.getUserById(1L)).thenReturn(Optional.of(mockResponse));

        // Act & Assert
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void getUserById_NonExistingUser_ReturnsNotFound() throws Exception {
        // Arrange
        when(userService.getUserById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUsers_ReturnsListOfUsers() throws Exception {
        // Arrange
        List<UserResponse> responses = List.of(createMockUserResponse(
                        1L, "user1", "user1@example.com", "User", "One"),
                createMockUserResponse(
                        2L, "user2", "user2@example.com", "User", "Two"));
        when(userService.getAllUsers()).thenReturn(responses);

        // Act & Assert
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("user2"));
    }

    @Test
    void updateUser_ValidRequest_ReturnsUpdatedUser() throws Exception {
        // Arrange
        UpdateUserRequest request = new UpdateUserRequest(
                "updateduser",
                "updated@example.com",
                "Updated",
                "User"
        );

        UserResponse mockResponse = createMockUserResponse(
                1L, "updateduser", "updated@example.com", "Updated", "User");
        when(userService.updateUser(anyLong(), any(UpdateUserRequest.class)))
                .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("updateduser"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    void deleteUser_ExistingUser_ReturnsNoContent() throws Exception {
        // Arrange
        doNothing().when(userService).deleteUser(anyLong());

        // Act & Assert
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createUser_InvalidRequest_ReturnsBadRequest() throws Exception {
        // Arrange
        CreateUserRequest request = new CreateUserRequest(
                "u",
                "invalid-email",
                "",
                null
        );

        // Act & Assert
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation Error"))
                .andExpect(jsonPath("$.errors.username").exists())
                .andExpect(jsonPath("$.errors.email").exists());
    }

    private UserResponse createMockUserResponse(Long id, String username, String email, String firstName, String lastName) {
        return new UserResponse(id, username, email, firstName, lastName, true, LocalDateTime.now(), LocalDateTime.now());
    }
}