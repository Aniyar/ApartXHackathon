package com.hackathon.apartxhackathon.request;

import com.hackathon.apartxhackathon.model.CleaningType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CalculateCostRequest {
	private Integer area;
	private Integer roomNumber;
	private Integer bathNumber;
	private List<Integer> serviceIds;
	private CleaningType cleaningType;
}
