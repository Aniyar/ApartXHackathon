package com.hackathon.apartxhackathon.request;

import com.hackathon.apartxhackathon.model.CleaningType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CreateOrderRequest {
	private Integer apartmentId;
	private String description;
	private LocalDateTime dateTime;
	private List<Integer> serviceIds;
	private CleaningType cleaningType;
	private Integer desiredPrice;
}
