package com.hackathon.apartxhackathon.repository;

import com.hackathon.apartxhackathon.model.Order;
import com.hackathon.apartxhackathon.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	Iterable<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);
}

