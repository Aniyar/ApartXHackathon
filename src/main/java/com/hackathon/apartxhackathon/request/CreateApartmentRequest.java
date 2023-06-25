package com.hackathon.apartxhackathon.request;

import com.hackathon.apartxhackathon.model.City;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateApartmentRequest {
    private Integer cityId;
    private String address;
    private Integer area;
    private Integer roomNumber;
    private String description;
    private List<String> imageUrls;
}
