package com.hackathon.apartxhackathon.repository;

import com.hackathon.apartxhackathon.exception.UserAlreadyExistsException;
import com.hackathon.apartxhackathon.exception.UserNotFoundException;
import com.hackathon.apartxhackathon.model.City;
import com.hackathon.apartxhackathon.model.Cleaner;
import com.hackathon.apartxhackathon.model.LandLord;
import com.hackathon.apartxhackathon.model.ServiceType;
import com.hackathon.apartxhackathon.request.RegisterRequest;
import com.hackathon.apartxhackathon.service.AuthenticationService;
import com.hackathon.apartxhackathon.user.Role;
import com.hackathon.apartxhackathon.user.User;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Component
@AllArgsConstructor
public class InitDatabase {
	private final ServiceTypeRepository serviceRepository;
	private final CityRepository cityRepository;
	private final UserRepository userRepository;
	private final LandLordRepository landLordRepository;
	private final CleanerRepository cleanerRepository;
	private final AuthenticationService authService;

	@PostConstruct
	public void initDatabase() throws UserAlreadyExistsException, UserNotFoundException {
		if (serviceRepository.count() == 0){
			ServiceType service1 = ServiceType.builder()
					.name("Заправить постель")
					.description("Заправить постель во всех комнатах")
					.price(500)
					.build();
			serviceRepository.save(service1);

			ServiceType service2 = ServiceType.builder()
					.name("Пропылесосить ковры")
					.description("Пропылесосить ковры во всех комнатах")
					.price(1000)
					.build();
			serviceRepository.save(service2);

			ServiceType service3 = ServiceType.builder()
					.name("Помыть посуду")
					.description("Помыть посуду")
					.price(500)
					.build();
			serviceRepository.save(service3);
		}

		if (cityRepository.count() == 0){
			City city1 = City.builder().name("Astana").build();
			City city2 = City.builder().name("Almaty").build();
			cityRepository.saveAll(List.of(city1, city2));
		}

		if (userRepository.count() == 0){
			RegisterRequest adminReq = RegisterRequest.builder()
					.email("adminApartX@gmail.com")
					.firstname("Admin")
					.lastname("ApartX")
					.password("admin")
					.iin("030727651399")
					.role(Role.ADMIN)
					.build();

			authService.register(adminReq);
			User admin = userRepository.findByEmail("adminApartX@gmail.com").orElseThrow(UserNotFoundException::new);
			admin.setApproved(true);


			RegisterRequest landlordReq = RegisterRequest.builder()
					.email("landlordApartX@gmail.com")
					.firstname("Landlord")
					.lastname("Landlordov")
					.password("landlord")
					.iin("030727651397")
					.role(Role.LANDLORD)
					.build();

			authService.register(landlordReq);
			User landlord = userRepository.findByEmail("landlordApartX@gmail.com").orElseThrow(UserNotFoundException::new);
			landlord.setApproved(true);


			RegisterRequest cleanerReq = RegisterRequest.builder()
					.email("cleanerApartX@gmail.com")
					.firstname("Cleaner")
					.lastname("Cleanerov")
					.password("cleaner")
					.iin("000727651399")
					.role(Role.CLEANER)
					.build();

			authService.register(cleanerReq);
			User cleaner = userRepository.findByEmail("cleanerApartX@gmail.com").orElseThrow(UserNotFoundException::new);
			cleaner.setApproved(true);


			userRepository.saveAll(List.of(admin, landlord, cleaner));

			LandLord ll = LandLord.builder().user(landlord).build();
			landLordRepository.save(ll);

			Cleaner cc = Cleaner.builder().user(cleaner).birthdate(LocalDate.of(2000, Month.AUGUST, 21)).build();
			cleanerRepository.save(cc);
		}
	}
}
