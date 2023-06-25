package com.hackathon.apartxhackathon.controller;


import com.hackathon.apartxhackathon.constants.Constants;
import com.hackathon.apartxhackathon.model.City;
import com.hackathon.apartxhackathon.model.CleaningType;
import com.hackathon.apartxhackathon.model.ServiceType;
import com.hackathon.apartxhackathon.repository.CityRepository;
import com.hackathon.apartxhackathon.repository.ServiceTypeRepository;
import com.hackathon.apartxhackathon.request.CalculateCostRequest;
import com.hackathon.apartxhackathon.service.HomeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.ServiceNotFoundException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/home")
@AllArgsConstructor
public class HomeController {
	private final ServiceTypeRepository serviceRepository;
	private final HomeService homeService;
	private final CityRepository cityRepository;

	@PostMapping("/calculate_cost")
	public ResponseEntity<BigDecimal> calculateCost(@RequestBody CalculateCostRequest request) throws ServiceNotFoundException {
		return ResponseEntity.ok(BigDecimal.valueOf(homeService.calculateCost(request)));
	}

	@GetMapping("/services")
	public ResponseEntity<Iterable<ServiceType>> getServices() {
		return ResponseEntity.ok(serviceRepository.findAll());
	}

	@GetMapping("/getCities")
	public ResponseEntity<Iterable<City>> getCities() {
		return ResponseEntity.ok(cityRepository.findAll());
	}

}
