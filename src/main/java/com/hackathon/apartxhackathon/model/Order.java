package com.hackathon.apartxhackathon.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Apartment apartment;

    private String description;

    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ServiceType> serviceTypeList;

    @Enumerated(EnumType.STRING)
    private CleaningType cleaningType;

    private Integer desiredPrice;
    private LocalDateTime dateAndTime;

    private Integer landLordRating;
    private Integer cleanerRating;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;

	@OneToMany(mappedBy = "order")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    public List<OrderCleanerResponse> respondedCleanerList;

    private LocalDateTime approvedAt;

    private LocalDateTime finishedAt;

    @ElementCollection
    @CollectionTable(name = "order_images", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    public Optional<Integer> getBestPrice() {
        if (respondedCleanerList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(
                Collections.min(
                        respondedCleanerList.stream()
                                .map(OrderCleanerResponse::getOfferedPrice)
                                .collect(Collectors.toList())
                )
        );
    }
}
