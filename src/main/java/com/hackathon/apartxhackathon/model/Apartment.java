package com.hackathon.apartxhackathon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Apartment {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @NotNull
    private LandLord landLord;

    @NotNull
    @ManyToOne
    private City city;
    @NotNull
    private String address;

    @NotNull
    private Integer area;
    private Integer roomNumber;

    private String description;

    // Constructors, getters, and setters
}

