package com.hackathon.apartxhackathon.repository;

import com.hackathon.apartxhackathon.model.Apartment;
import com.hackathon.apartxhackathon.model.LandLord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
	Iterable<Apartment> findAllByLandLord(LandLord landLord);
}
