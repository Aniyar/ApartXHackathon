package com.hackathon.apartxhackathon.model;

import com.hackathon.apartxhackathon.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LandLord {
    @Id @GeneratedValue
    private Integer id;
    @OneToOne
    private User user;
    private BigDecimal rating;

}
