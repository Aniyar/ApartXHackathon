package com.hackathon.apartxhackathon.service;

import com.hackathon.apartxhackathon.exception.ApartmentNotFoundException;
import com.hackathon.apartxhackathon.exception.CityNotFoundException;
import com.hackathon.apartxhackathon.exception.LandLordNotFoundException;
import com.hackathon.apartxhackathon.exception.UserNotFoundException;
import com.hackathon.apartxhackathon.model.Apartment;
import com.hackathon.apartxhackathon.model.LandLord;
import com.hackathon.apartxhackathon.model.Order;
import com.hackathon.apartxhackathon.model.ServiceType;
import com.hackathon.apartxhackathon.repository.*;
import com.hackathon.apartxhackathon.request.CreateApartmentRequest;
import com.hackathon.apartxhackathon.request.CreateOrderRequest;
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

@Service
@AllArgsConstructor
public class LandLordService {
    private final ApartmentRepository aptRepository;
    private final LandLordRepository llRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final OrderRepository orderRepository;
    private final ServiceTypeRepository serviceRepository;

    public ResponseEntity addApartment(UserDetails userDetails, CreateApartmentRequest request) throws UserNotFoundException, LandLordNotFoundException, CityNotFoundException {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        LandLord landLord = llRepository.findByUser_Id(user.getId()).orElseThrow(LandLordNotFoundException::new);

        Apartment apartment = Apartment.builder()
                                        .landLord(landLord)
                                        .city(cityRepository.findById(request.getCityId()).orElseThrow(CityNotFoundException::new))
                                        .address(request.getAddress())
                                        .area(request.getArea())
                                        .roomNumber(request.getRoomNumber())
                                        .description(request.getDescription())
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
                .dateAndTime(request.getDateTime())
                .createdAt(LocalDateTime.now())
                .desiredPrice(request.getDesiredPrice())
                .serviceTypeList(services)
                .build();

        orderRepository.save(order);


	}
}
