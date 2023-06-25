package com.hackathon.apartxhackathon.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CleanerRespondRequest {
	private Integer orderId;
	private Integer price;
}
