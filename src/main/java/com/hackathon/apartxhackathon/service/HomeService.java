package com.hackathon.apartxhackathon.service;

import com.hackathon.apartxhackathon.constants.Constants;
import com.hackathon.apartxhackathon.model.CleaningType;
import com.hackathon.apartxhackathon.model.ServiceType;
import com.hackathon.apartxhackathon.repository.ServiceTypeRepository;
import com.hackathon.apartxhackathon.request.CalculateCostRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.ServiceNotFoundException;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class HomeService {
	private final ServiceTypeRepository serviceRepository;

	public Double calculateCost(CalculateCostRequest request) throws ServiceNotFoundException {
		Integer baseRate = request.getArea() * Constants.base;
		Integer bathroomRate = request.getBathNumber() * Constants.bathroom;
		Integer addOns = 0;
		for (Integer serviceId: request.getServiceIds()) {
			ServiceType service = serviceRepository.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
			addOns += service.getPrice();
		}

		Integer total = baseRate + bathroomRate + addOns;
		if (request.getCleaningType() == CleaningType.DEEPCLEAN) {
			return total * Constants.deepclean;
		}
		else{
			return total.doubleValue();
		}
	}
}
