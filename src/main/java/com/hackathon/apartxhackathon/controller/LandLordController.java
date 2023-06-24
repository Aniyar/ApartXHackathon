package com.hackathon.apartxhackathon.controller;

import com.hackathon.apartxhackathon.exception.ApartmentNotFoundException;
import com.hackathon.apartxhackathon.exception.CityNotFoundException;
import com.hackathon.apartxhackathon.exception.LandLordNotFoundException;
import com.hackathon.apartxhackathon.exception.UserNotFoundException;
import com.hackathon.apartxhackathon.model.Apartment;
import com.hackathon.apartxhackathon.model.LandLord;
import com.hackathon.apartxhackathon.repository.LandLordRepository;
import com.hackathon.apartxhackathon.repository.UserRepository;
import com.hackathon.apartxhackathon.request.CreateApartmentRequest;
import com.hackathon.apartxhackathon.service.LandLordService;
import com.hackathon.apartxhackathon.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/landlord")
@PreAuthorize("hasRole('LANDLORD')")
@AllArgsConstructor
public class LandLordController {
    private final LandLordRepository repository;
    private final UserRepository userRepository;
    private final LandLordService service;

    @GetMapping
    @PreAuthorize("hasAuthority('landlord:read')")
    public ResponseEntity<Iterable<Apartment>> getApartments(@AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException, LandLordNotFoundException {
        return ResponseEntity.ok(service.getApartments(userDetails));
    }

    @PostMapping("/add_apartment")
    @PreAuthorize("hasAuthority('landlord:create')")
    public ResponseEntity addApartment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateApartmentRequest request) throws UserNotFoundException, LandLordNotFoundException, CityNotFoundException {
        return ResponseEntity.ok(service.addApartment(userDetails, request));
    }

    @DeleteMapping("/delete_apartment")
    @PreAuthorize("hasAuthority('landlord:delete')")
    public ResponseEntity addApartment(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Integer apartmentId) throws UserNotFoundException, LandLordNotFoundException, ApartmentNotFoundException  {
        return service.deleteApartment(userDetails, apartmentId);
    }

//    @PutMapping("/edit_apartment")
//    @PreAuthorize("hasAuthority('landlord:update')")
//    public ResponseEntity editApartment(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Integer apartmentId, @RequestBody CreateApartmentRequest request) throws UserNotFoundException, LandLordNotFoundException, CityNotFoundException, ApartmentNotFoundException {
//        return ResponseEntity.ok(service.editApartment(userDetails, apartmentId, request));
//    }
}
