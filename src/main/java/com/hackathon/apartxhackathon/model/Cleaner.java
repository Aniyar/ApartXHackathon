package com.hackathon.apartxhackathon.model;


import com.hackathon.apartxhackathon.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Cleaner {
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
    private BigDecimal rating;

    private LocalDate birthdate;
    private int yearsOfExperience;
}
