package com.hackathon.apartxhackathon.repository;

import com.hackathon.apartxhackathon.model.OrderCleanerResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderResponseRepository extends JpaRepository<OrderCleanerResponse, Integer> {
	List<OrderCleanerResponse> findAllByOrder_Id(Integer id);
}
