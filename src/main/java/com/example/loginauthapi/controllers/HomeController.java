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
@RequestMapping("home")
@Tag(name = "auth-api/home-controller")
public class HomeController {

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "First message when entering the api ", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description= "Show Message" ),
            @ApiResponse(responseCode = "400", description= "Failed request" ),
            @ApiResponse(responseCode = "500", description= "Error shown message" )
    })
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok().body("Hello, welcome to api");
    }
}
