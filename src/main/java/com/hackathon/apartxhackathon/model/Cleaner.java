package com.hackathon.apartxhackathon.model;


import com.hackathon.apartxhackathon.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private User user;
    private BigDecimal rating;

    private LocalDate birthdate;
    private int yearsOfExperience;
}
