package com.hackathon.apartxhackathon.response;

import com.hackathon.apartxhackathon.model.CleaningType;
import com.hackathon.apartxhackathon.model.OrderCleanerResponse;
import com.hackathon.apartxhackathon.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
	private Integer id;
	private Integer landLordId;
	private OrderStatus orderStatus;
//	private Integer landLordRating;
//	private Integer cleanerRating;
	private Integer apartmentId;
	private String description;
	private LocalDateTime dateTime;
	private List<Integer> serviceIds;
	private CleaningType cleaningType;
	private Integer desiredPrice;
	private List<OrderCleanerResponse> cleanerResponses;
}
