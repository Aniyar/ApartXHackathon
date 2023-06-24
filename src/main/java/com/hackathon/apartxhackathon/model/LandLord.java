package com.hackathon.apartxhackathon.model;

import com.hackathon.apartxhackathon.user.User;
import jakarta.persistence.*;
import lombok.*;

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
    @OneToOne @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
    private BigDecimal rating;

}
