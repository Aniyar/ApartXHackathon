package com.hackathon.apartxhackathon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

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

    @ElementCollection
    @CollectionTable(name = "apartment_images", joinColumns = @JoinColumn(name = "apartment_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    // Constructors, getters, and setters
}

