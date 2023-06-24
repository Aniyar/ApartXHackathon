package com.hackathon.apartxhackathon.service;

import com.hackathon.apartxhackathon.model.Order;
import com.hackathon.apartxhackathon.model.OrderStatus;
import com.hackathon.apartxhackathon.repository.OrderRepository;
import com.hackathon.apartxhackathon.response.OrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CleanerService {
	private final OrderRepository orderRepository;

	public Iterable<OrderResponse> getNewOrders() {
		Iterable<Order> orders = orderRepository.findByStatusOrderByCreatedAtDesc(OrderStatus.CREATED);
		return StreamSupport
				.stream(orders.spliterator(), false)
				.map(order -> OrderResponse.builder()
						.id(order.getId())
						.landLordId(order.getLandlord().getId())
						.orderStatus(order.getStatus())
						.apartmentId(order.getApartment().getId())
						.description(order.getDescription())
						.dateTime(order.getDateAndTime())
						.serviceIds(order.getServiceTypeList().stream().map(serviceType -> serviceType.getId()).collect(Collectors.toList()))
						.cleaningType(order.getCleaningType())
						.desiredPrice(order.getDesiredPrice())
						.build()).collect(Collectors.toList());
	}
}
