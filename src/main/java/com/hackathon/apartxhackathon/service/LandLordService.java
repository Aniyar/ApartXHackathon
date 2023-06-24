package com.hackathon.apartxhackathon.service;

import com.hackathon.apartxhackathon.exception.ApartmentNotFoundException;
import com.hackathon.apartxhackathon.exception.CityNotFoundException;
import com.hackathon.apartxhackathon.exception.LandLordNotFoundException;
import com.hackathon.apartxhackathon.exception.UserNotFoundException;
import com.hackathon.apartxhackathon.model.Apartment;
import com.hackathon.apartxhackathon.model.LandLord;
import com.hackathon.apartxhackathon.repository.ApartmentRepository;
import com.hackathon.apartxhackathon.repository.CityRepository;
import com.hackathon.apartxhackathon.repository.LandLordRepository;
import com.hackathon.apartxhackathon.repository.UserRepository;
import com.hackathon.apartxhackathon.request.CreateApartmentRequest;
import com.hackathon.apartxhackathon.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LandLordService {
    private final ApartmentRepository aptRepository;
    private final LandLordRepository llRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    public ResponseEntity addApartment(UserDetails userDetails, CreateApartmentRequest request) throws UserNotFoundException, LandLordNotFoundException, CityNotFoundException {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        LandLord landLord = llRepository.findByUser(user).orElseThrow(LandLordNotFoundException::new);

        Apartment apartment = Apartment.builder()
                                        .address(request.getAddress())
                                        .city(cityRepository.findById(request.getCityId()).orElseThrow(CityNotFoundException::new))
                                        .landLord(landLord)
                                        .roomNumber(request.getRoomNumber())
                                        .area(request.getArea())
                                        .build();
        aptRepository.save(apartment);
        return ResponseEntity.ok().build();

    }


    public ResponseEntity deleteApartment(UserDetails userDetails, Integer apartmentId) throws UserNotFoundException, LandLordNotFoundException, ApartmentNotFoundException {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        LandLord landLord = llRepository.findByUser(user).orElseThrow(LandLordNotFoundException::new);
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
        LandLord landLord = llRepository.findByUser(user).orElseThrow(LandLordNotFoundException::new);
        return aptRepository.findAllByLandLord(landLord);
    }
}
