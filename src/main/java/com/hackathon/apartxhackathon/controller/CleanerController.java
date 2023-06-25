package com.hackathon.apartxhackathon.controller;

import com.hackathon.apartxhackathon.exception.CleanerNotFoundException;
import com.hackathon.apartxhackathon.exception.OrderNotFoundException;
import com.hackathon.apartxhackathon.exception.UserNotFoundException;
import com.hackathon.apartxhackathon.request.CleanerRespondRequest;
import com.hackathon.apartxhackathon.response.OrderResponse;
import com.hackathon.apartxhackathon.service.CleanerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cleaner")
@PreAuthorize("hasRole('CLEANER')")
public class CleanerController {
	private final CleanerService service;
	@GetMapping
	@PreAuthorize("hasAuthority('cleaner:create')")
	public ResponseEntity<Iterable<OrderResponse>> getNewOrders(){
		Iterable<OrderResponse> resp = service.getNewOrders();
		return ResponseEntity.ok(resp);
	}


	@PostMapping("/respond")
	@PreAuthorize("hasAuthority('cleaner:create')")
	public ResponseEntity respondToOrder(@AuthenticationPrincipal UserDetails userDetails,
	                                     @RequestBody CleanerRespondRequest request) throws UserNotFoundException, OrderNotFoundException, CleanerNotFoundException {
		service.respond(userDetails, request);
		return ResponseEntity.ok().build();
	}

}
