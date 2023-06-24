package com.hackathon.apartxhackathon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LandLord landLord;

    @NotNull
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private City city;
    @NotNull
    private String address;

    @NotNull
    private Integer area;

    @NotNull
    private Integer roomNumber;

    private String description;

    // Constructors, getters, and setters
}

