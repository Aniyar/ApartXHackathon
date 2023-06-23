package com.hackathon.apartxhackathon;

import com.hackathon.apartxhackathon.request.RegisterRequest;
import com.hackathon.apartxhackathon.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.hackathon.apartxhackathon.user.Role.ADMIN;
import static com.hackathon.apartxhackathon.user.Role.CLEANER;

@SpringBootApplication
public class ApartXHackathonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApartXHackathonApplication.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());

            var cleaner = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("CLEANER@mail.com")
                    .password("password")
                    .role(CLEANER)
                    .build();
            System.out.println("CLEANER token: " + service.register(cleaner).getAccessToken());

        };
    }
}
