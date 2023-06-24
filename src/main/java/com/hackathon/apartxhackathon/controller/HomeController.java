package com.hackathon.apartxhackathon.controller;


import com.hackathon.apartxhackathon.constants.Constants;
import com.hackathon.apartxhackathon.model.CleaningType;
import com.hackathon.apartxhackathon.model.ServiceType;
import com.hackathon.apartxhackathon.repository.ServiceTypeRepository;
import com.hackathon.apartxhackathon.request.CalculateCostRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.ServiceNotFoundException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/home")
@AllArgsConstructor
public class HomeController {
	private final ServiceTypeRepository serviceRepository;

	@PostMapping("/calculate_cost")
	public ResponseEntity<BigDecimal> calculateCost(@RequestBody CalculateCostRequest request) throws ServiceNotFoundException {
		Integer baseRate = request.getArea() * Constants.base;
		Integer bathroomRate = request.getBathNumber() * Constants.bathroom;
		Integer addOns = 0;
		for (Integer serviceId: request.getServiceIds()) {
			ServiceType service = serviceRepository.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
			addOns += service.getPrice();
		}

		Integer total = baseRate + bathroomRate + addOns;
		if (request.getCleaningType() == CleaningType.DEEPCLEAN) {
			return ResponseEntity.ok(BigDecimal.valueOf(total * Constants.deepclean));
		}
		else{
			return ResponseEntity.ok(BigDecimal.valueOf(total));
		}

	}
}
