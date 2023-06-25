package com.hackathon.apartxhackathon.repository;

import com.hackathon.apartxhackathon.model.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CleanerRepository extends JpaRepository<Cleaner, Integer> {

	Optional<Cleaner> findByUser_Id(Integer id);


}
