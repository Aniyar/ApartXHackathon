package com.hackathon.apartxhackathon.repository;

import com.hackathon.apartxhackathon.model.OrderCleanerResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderResponseRepository extends JpaRepository<OrderCleanerResponse, Integer> {
}
