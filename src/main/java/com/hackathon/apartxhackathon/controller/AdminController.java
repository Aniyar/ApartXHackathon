package com.hackathon.apartxhackathon.controller;

import com.hackathon.apartxhackathon.model.City;
import com.hackathon.apartxhackathon.repository.CityRepository;
import com.hackathon.apartxhackathon.repository.UserRepository;
import com.hackathon.apartxhackathon.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class AdminController {
    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public String get() {
        return "GET:: admin controller";
    }
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    @Hidden
    public String post() {
        return "POST:: admin controller";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    @Hidden
    public String put() {
        return "PUT:: admin controller";
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    @Hidden
    public String delete() {
        return "DELETE:: admin controller";
    }


    @PostMapping("/add_city")
    @PreAuthorize("hasAuthority('admin:create')")
    @Hidden
    public City addCity(@RequestParam String cityName)
    {
        City city = City.builder().name(cityName).build();
        return cityRepository.save(city);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin:read')")
    @Hidden
    public ResponseEntity<Iterable<UserInfoResponse>> getUsers(){
        return ResponseEntity.ok(userRepository.findAll().stream().map(user -> UserInfoResponse.builder()
                                                                                                .email(user.getEmail())
                                                                                                .firstname(user.getFirstname())
                                                                                                .lastname(user.getLastname())
                                                                                                .role(user.getRole())
                                                                                                .iin(user.getIin())
                                                                                                .build()
                                                                                                ).collect(Collectors.toList()));
    }


}
