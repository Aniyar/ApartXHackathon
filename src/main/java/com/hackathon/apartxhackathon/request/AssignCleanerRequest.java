package com.hackathon.apartxhackathon.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignCleanerRequest {
	private Integer cleanerId;
	private Integer orderId;
}
