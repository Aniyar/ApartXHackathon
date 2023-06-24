package com.hackathon.apartxhackathon.repository;

import com.hackathon.apartxhackathon.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
}
