package com.wallet.bankwallet.controller;

import com.wallet.bankwallet.model.User;
import com.wallet.bankwallet.model.UserRequest;
import com.wallet.bankwallet.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(
            summary = "List all users",
            description = "Returns a list of all registered users."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users listed successfully")
    })
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by id",
            description = "Returns a user with the given id, including basic details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUserById(@PathVariable @NotNull @Positive Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/email/{email}")
    @Operation(
            summary = "Get user by email",
            description = "Returns a user with the given email address."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "400", description = "Invalid email supplied"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUserByEmail(@PathVariable @NotBlank @Email String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @PostMapping
    @Operation(
            summary = "Create new user",
            description = "Creates a new user and an associated wallet from the provided data."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public User createUser(@Valid @RequestBody UserRequest request) {
        return userService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update existing user",
            description = "Updates the user with the given id using the provided data."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Invalid id or request body"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public User updateUser(@PathVariable @NotNull @Positive Long id,
                           @Valid @RequestBody UserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user",
            description = "Deletes the user and its wallet. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden – not enough privileges")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable @NotNull @Positive Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
