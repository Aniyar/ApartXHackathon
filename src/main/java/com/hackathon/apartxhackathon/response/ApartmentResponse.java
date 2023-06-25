package com.hackathon.apartxhackathon.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApartmentResponse {
	private Integer id;
	private Integer landLordId;
	private Integer cityId;
	private String address;
	private Integer area;
	private Integer roomNumber;
	private Integer bathNumber;
	private String description;
	private List<String> imageUrls;
}
