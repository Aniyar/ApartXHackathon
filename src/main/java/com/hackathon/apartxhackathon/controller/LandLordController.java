package com.hackathon.apartxhackathon.controller;

import com.hackathon.apartxhackathon.exception.*;
import com.hackathon.apartxhackathon.model.Apartment;
import com.hackathon.apartxhackathon.model.LandLord;
import com.hackathon.apartxhackathon.model.OrderStatus;
import com.hackathon.apartxhackathon.repository.LandLordRepository;
import com.hackathon.apartxhackathon.repository.UserRepository;
import com.hackathon.apartxhackathon.request.CreateApartmentRequest;
import com.hackathon.apartxhackathon.request.CreateOrderRequest;
import com.hackathon.apartxhackathon.response.ApartmentResponse;
import com.hackathon.apartxhackathon.response.OrderResponse;
import com.hackathon.apartxhackathon.service.LandLordService;
import com.hackathon.apartxhackathon.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/landlord")
@PreAuthorize("hasRole('LANDLORD')")
@AllArgsConstructor
public class LandLordController {
    private final LandLordRepository repository;
    private final UserRepository userRepository;
    private final LandLordService service;

    @GetMapping("/apartments")
    @PreAuthorize("hasAuthority('landlord:create')")
    public ResponseEntity<List<ApartmentResponse>> getApartments(@AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException, LandLordNotFoundException {
        return ResponseEntity.ok(
                StreamSupport
                        .stream(service.getApartments(userDetails)
                                .spliterator(), false)
                        .map(apartment -> ApartmentResponse
                                .builder()
                                .id(apartment.getId())
                                .landLordId(apartment.getLandLord().getId())
                                .address(apartment.getAddress())
                                .cityId(apartment.getCity().getId())
                                .roomNumber(apartment.getRoomNumber())
                                        .bathNumber(apartment.getBathNumber())
                                .description(apartment.getDescription())
                                .area(apartment.getArea())
                                        .imageUrls(apartment.getImageUrls())
                                        .build()
                                ).collect(Collectors.toList()));
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

    @PostMapping("/post_order")
    @PreAuthorize("hasAuthority('landlord:create')")
    public ResponseEntity postOrder(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateOrderRequest request) throws UserNotFoundException, LandLordNotFoundException, ApartmentNotFoundException {
        service.postOrder(userDetails, request);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/orders")
    @PreAuthorize("hasAuthority('landlord:create')")
    public ResponseEntity<Iterable<OrderResponse>> getNewOrders(@AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException, LandLordNotFoundException, ApartmentNotFoundException {
        Iterable<OrderResponse> orders = service.getOrders(userDetails);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasAuthority('landlord:create')")
    public ResponseEntity<OrderResponse> getNewOrders(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer id) throws UserNotFoundException, LandLordNotFoundException, ApartmentNotFoundException, OrderNotFoundException {

        return ResponseEntity.ok(service.getOrderById(userDetails, id));
    }



    @PutMapping("/orders/approve/{cleanerResponseId}")
    @PreAuthorize("hasAuthority('landlord:create')")
    public ResponseEntity assignOrderCleaner(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer cleanerResponseId) throws UserNotFoundException, LandLordNotFoundException, ApartmentNotFoundException, OrderNotFoundException, CleanerNotFoundException {
        service.assignOrderCleaner(userDetails, cleanerResponseId);
        return ResponseEntity.ok().build();
    }







//    @PutMapping("/edit_apartment")
//    @PreAuthorize("hasAuthority('landlord:update')")
//    public ResponseEntity editApartment(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Integer apartmentId, @RequestBody CreateApartmentRequest request) throws UserNotFoundException, LandLordNotFoundException, CityNotFoundException, ApartmentNotFoundException {
//        return ResponseEntity.ok(service.editApartment(userDetails, apartmentId, request));
//    }
}
