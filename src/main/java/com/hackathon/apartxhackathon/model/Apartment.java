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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LandLord landLord;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private City city;

    private String address;

    private Integer area;

    private Integer roomNumber;
    private Integer bathNumber;

    private String description;

    @ElementCollection
    @CollectionTable(name = "apartment_images", joinColumns = @JoinColumn(name = "apartment_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    // Constructors, getters, and setters
}

