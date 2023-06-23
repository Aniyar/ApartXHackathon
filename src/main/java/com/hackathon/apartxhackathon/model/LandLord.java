package com.hackathon.apartxhackathon.model;

import com.hackathon.apartxhackathon.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class LandLord {
    @Id @GeneratedValue
    private Integer id;
    @OneToOne
    private User user;
    private String imageUrl;
    private BigDecimal rating;
}
