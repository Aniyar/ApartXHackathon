package com.hackathon.apartxhackathon.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CleanerBetResponse {
	private Integer id;
	private Integer cleanerId;
	private Integer orderId;
	private Integer offeredPrice;
}
