package com.hackathon.apartxhackathon.repository;

import com.hackathon.apartxhackathon.model.Order;
import com.hackathon.apartxhackathon.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	Iterable<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);

	@Query("SELECT o FROM Order o WHERE o.status IN (:statuses) ORDER BY o.createdAt DESC")
	Iterable<Order> findByStatusInOrderByCreatedAtDesc(List<OrderStatus> statuses);

	Iterable<Order> findAllByLandlord_IdOrderByCreatedAtDesc(Integer id);
}

