package com.example.loginauthapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "auth-api/user-controller")
public class UserController {
    @Operation(summary = "List all users in dataBase", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users successfully listed"),
            @ApiResponse(responseCode = "400", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Error showing message")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("funcionou");
    }
}
