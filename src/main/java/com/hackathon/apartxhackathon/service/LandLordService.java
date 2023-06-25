package com.hackathon.apartxhackathon.service;

import com.hackathon.apartxhackathon.exception.*;
import com.hackathon.apartxhackathon.model.*;
import com.hackathon.apartxhackathon.repository.*;
import com.hackathon.apartxhackathon.request.AssignCleanerRequest;
import com.hackathon.apartxhackathon.request.CreateApartmentRequest;
import com.hackathon.apartxhackathon.request.CreateOrderRequest;
import com.hackathon.apartxhackathon.response.CleanerBetResponse;
import com.hackathon.apartxhackathon.response.OrderResponse;
import com.hackathon.apartxhackathon.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.management.ServiceNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class LandLordService {
    private final ApartmentRepository aptRepository;
    private final LandLordRepository llRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final OrderRepository orderRepository;
    private final OrderResponseRepository orderResponseRepository;
    private final ServiceTypeRepository serviceRepository;
    private final CleanerRepository cleanerRepository;

    public ResponseEntity addApartment(UserDetails userDetails, CreateApartmentRequest request) throws UserNotFoundException, LandLordNotFoundException, CityNotFoundException {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        LandLord landLord = llRepository.findByUser_Id(user.getId()).orElseThrow(LandLordNotFoundException::new);

        Apartment apartment = Apartment.builder()
                                        .landLord(landLord)
                                        .city(cityRepository.findById(request.getCityId()).orElseThrow(CityNotFoundException::new))
                                        .address(request.getAddress())
                                        .area(request.getArea())
                                        .roomNumber(request.getRoomNumber())
                                        .bathNumber(request.getBathNumber())
                                        .description(request.getDescription())
                                        .imageUrls(request.getImageUrls())
                                        .build();
        aptRepository.save(apartment);
        return ResponseEntity.ok().build();

    }


    public ResponseEntity deleteApartment(UserDetails userDetails, Integer apartmentId) throws UserNotFoundException, LandLordNotFoundException, ApartmentNotFoundException {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        LandLord landLord = llRepository.findByUser_Id(user.getId()).orElseThrow(LandLordNotFoundException::new);
        Apartment apt = aptRepository.findById(apartmentId).orElseThrow(ApartmentNotFoundException::new);

        if (apt.getLandLord() == landLord){
            aptRepository.delete(apt);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    public Iterable<Apartment> getApartments(UserDetails userDetails) throws LandLordNotFoundException, UserNotFoundException {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        LandLord landLord = llRepository.findByUser_Id(user.getId()).orElseThrow(LandLordNotFoundException::new);
        Iterable<Apartment> apts = aptRepository.findAllByLandLord_Id(landLord.getId());
        return apts;
    }

	public void postOrder(UserDetails userDetails, CreateOrderRequest request) throws UserNotFoundException, LandLordNotFoundException, ApartmentNotFoundException {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        LandLord landLord = llRepository.findByUser_Id(user.getId()).orElseThrow(LandLordNotFoundException::new);
        Apartment apartment = aptRepository.findById(request.getApartmentId()).orElseThrow(ApartmentNotFoundException::new);
        if (apartment.getLandLord() != landLord){
            throw new ApartmentNotFoundException();
        }

        List<ServiceType> services = request.getServiceIds().stream().map(serviceId -> {
            try {
                return serviceRepository.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
            } catch (ServiceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        Order order = Order.builder()
                .description(request.getDescription())
                .landlord(landLord)
                .apartment(apartment)
                .dateAndTime(request.getDateTime())
                .createdAt(LocalDateTime.now())
                .desiredPrice(request.getDesiredPrice())
                .serviceTypeList(services)
                .status(OrderStatus.CREATED)
                .cleaningType(request.getCleaningType())
                .build();

        orderRepository.save(order);


	}

    public Iterable<OrderResponse> getOrders(UserDetails userDetails) throws LandLordNotFoundException, UserNotFoundException {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        LandLord landLord = llRepository.findByUser_Id(user.getId()).orElseThrow(LandLordNotFoundException::new);
        Iterable<Order> orders = orderRepository.findAllByLandlord_IdOrderByCreatedAtDesc(landLord.getId());

        return StreamSupport.stream(orders.spliterator(), false).map(
                order -> convertOrder(order)).collect(Collectors.toList());

    }

    public void assignOrderCleaner(UserDetails userDetails, AssignCleanerRequest request) throws UserNotFoundException, OrderNotFoundException, CleanerNotFoundException {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(OrderNotFoundException::new);
        Cleaner cleaner = cleanerRepository.findById(request.getCleanerId()).orElseThrow(CleanerNotFoundException::new);

        order.setApprovedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.ASSIGNED);
        order.setCleaner(cleaner);

        orderRepository.save(order);

    }

    public OrderResponse getOrderById(UserDetails userDetails, Integer id) throws OrderNotFoundException, UserNotFoundException {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        OrderResponse resp = convertOrder(order);
        return resp;
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
