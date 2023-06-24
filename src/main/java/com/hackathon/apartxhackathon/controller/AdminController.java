package com.hackathon.apartxhackathon.controller;

import com.hackathon.apartxhackathon.model.City;
import com.hackathon.apartxhackathon.repository.CityRepository;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class AdminController {
    private final CityRepository cityRepository;

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
}
