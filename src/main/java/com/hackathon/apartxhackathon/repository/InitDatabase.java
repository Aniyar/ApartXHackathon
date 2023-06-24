package com.hackathon.apartxhackathon.repository;

import com.hackathon.apartxhackathon.model.ServiceType;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InitDatabase {
	private final ServiceTypeRepository serviceRepository;

	@PostConstruct
	public void initDatabase(){
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
	}
}
