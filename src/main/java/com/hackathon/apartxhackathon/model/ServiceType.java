package com.hackathon.apartxhackathon.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceType {
    @Id @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private Integer price;
}
