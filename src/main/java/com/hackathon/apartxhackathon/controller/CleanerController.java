package com.hackathon.apartxhackathon.controller;

import com.hackathon.apartxhackathon.response.OrderResponse;
import com.hackathon.apartxhackathon.service.CleanerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cleaner")
@PreAuthorize("hasRole('CLEANER')")
public class CleanerController {
	private final CleanerService service;
	@GetMapping
	@PreAuthorize("hasAuthority('cleaner:create')")
	public ResponseEntity<Iterable<OrderResponse>> getNewOrders(){
		return ResponseEntity.ok(service.getNewOrders());
	}

}
