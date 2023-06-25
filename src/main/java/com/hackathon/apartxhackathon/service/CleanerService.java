package com.hackathon.apartxhackathon.service;

import com.hackathon.apartxhackathon.exception.CleanerNotFoundException;
import com.hackathon.apartxhackathon.exception.OrderNotFoundException;
import com.hackathon.apartxhackathon.exception.UserNotFoundException;
import com.hackathon.apartxhackathon.model.*;
import com.hackathon.apartxhackathon.repository.CleanerRepository;
import com.hackathon.apartxhackathon.repository.OrderRepository;
import com.hackathon.apartxhackathon.repository.OrderResponseRepository;
import com.hackathon.apartxhackathon.repository.UserRepository;
import com.hackathon.apartxhackathon.request.CleanerRespondRequest;
import com.hackathon.apartxhackathon.model.OrderCleanerResponse;
import com.hackathon.apartxhackathon.response.CleanerBetResponse;
import com.hackathon.apartxhackathon.response.OrderResponse;
import com.hackathon.apartxhackathon.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CleanerService {
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final CleanerRepository cleanerRepository;
	private final EmailService emailService;
	private final OrderResponseRepository orderResponseRepository;

	public Iterable<OrderResponse> getNewOrders() {
		Iterable<Order> orders = orderRepository.findByStatusInOrderByCreatedAtDesc(List.of(OrderStatus.CREATED, OrderStatus.BETTING));
		return StreamSupport
				.stream(orders.spliterator(), false)
				.map(order -> convertOrder(order))
				.collect(Collectors.toList());
	}

	public void respond(UserDetails userDetails, CleanerRespondRequest request) throws UserNotFoundException, CleanerNotFoundException, OrderNotFoundException {
		User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
		Cleaner cleaner = cleanerRepository.findByUser_Id(user.getId()).orElseThrow(CleanerNotFoundException::new);
		Order order = orderRepository.findById(request.getOrderId()).orElseThrow(OrderNotFoundException::new);
		OrderCleanerResponse resp = OrderCleanerResponse.builder()
				.offeredPrice(request.getPrice())
				.cleaner(cleaner)
				.order(order)
				.build();
		orderResponseRepository.save(resp);

		order.setStatus(OrderStatus.BETTING);
		orderRepository.save(order);


		emailService.sendCleanersUpdate(resp);
	}


	public OrderResponse convertOrder(Order order){
		Iterable<OrderCleanerResponse> cleanerResponses = orderResponseRepository.findAllByOrder_Id(order.getId());
		List<CleanerBetResponse> betResponses = StreamSupport.stream(cleanerResponses.spliterator(), false)
												.map(resp -> CleanerBetResponse.builder()
														.id(resp.getId())
															.orderId(order.getId())
														.cleanerId(resp.getCleaner().getId())
														.offeredPrice(resp.getOfferedPrice())
														.build()).collect(Collectors.toList());

		OrderResponse resp = OrderResponse.builder()
				.id(order.getId())
				.landLordId(order.getLandlord().getId())
				.orderStatus(order.getStatus())
				.apartmentId(order.getApartment().getId())
				.description(order.getDescription())
				.dateTime(order.getDateAndTime())
				.serviceIds(order.getServiceTypeList().stream().map(serviceType -> serviceType.getId()).collect(Collectors.toList()))
				.cleaningType(order.getCleaningType())
				.desiredPrice(order.getDesiredPrice())
				.cleanerResponses(betResponses)
				.build();

		return resp;

	}
}
