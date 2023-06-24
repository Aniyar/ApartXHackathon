package com.hackathon.apartxhackathon.repository;

import com.hackathon.apartxhackathon.model.Apartment;
import com.hackathon.apartxhackathon.model.LandLord;
import com.hackathon.apartxhackathon.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LandLordRepository extends JpaRepository<LandLord, Integer> {
    Optional<LandLord> findByUser_Id(Integer userId);


}
