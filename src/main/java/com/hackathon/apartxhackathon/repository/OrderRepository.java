package com.hackathon.apartxhackathon.repository;

import com.hackathon.apartxhackathon.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
