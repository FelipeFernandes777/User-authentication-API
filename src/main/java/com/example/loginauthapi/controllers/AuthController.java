package com.example.loginauthapi.controllers;

import com.example.loginauthapi.domain.user.User;
import com.example.loginauthapi.dto.LoginRequestDTO;
import com.example.loginauthapi.dto.RegisterRequestDTO;
import com.example.loginauthapi.dto.ResponseDTO;
import com.example.loginauthapi.infra.security.TokenService;
import com.example.loginauthapi.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "auth-api/auth-controller")
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Route to log in to the user, returning the user name and token.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Login successfully" ),
            @ApiResponse(responseCode = "400", description="Failed to request" ),
            @ApiResponse(responseCode = "401", description="Failed to authenticated user" ),
            @ApiResponse(responseCode = "422", description="Data entered invalidly" ),
            @ApiResponse(responseCode = "500", description="Internal Server Error" )
    })
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body ){
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
            //Check if passwords are the same
        if(passwordEncoder.matches(body.password(),user.getPassword())){
            String token = this.tokenService.generateToken(user);

            return ResponseEntity.ok().body(new ResponseDTO(user.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register users in  API", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Register successfully" ),
            @ApiResponse(responseCode = "400", description="Failed to request" ),
            @ApiResponse(responseCode = "401", description="Failed to created user" ),
            @ApiResponse(responseCode = "422", description="Data entered invalidly" ),
            @ApiResponse(responseCode = "500", description="Internal Server Error" )
    })
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body ){

        Optional<User> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()) {

            User newUser = new User();

            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());

            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }
}
