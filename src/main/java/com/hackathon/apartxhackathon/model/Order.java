package com.hackathon.apartxhackathon.model;


import com.hackathon.apartxhackathon.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="_order")
public class Order {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LandLord landlord;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cleaner cleaner;

    private String description;

    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ServiceType> serviceTypeList;

    private Integer desiredPrice;
    private LocalDateTime dateAndTime;

    private Integer landLordRating;
    private Integer cleanerRating;

    private Boolean approved;

    private Boolean finished;



    private LocalDateTime createdAt;

    private LocalDateTime approvedAt;

    private LocalDateTime finishedAt;



}
