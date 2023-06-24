package com.hackathon.apartxhackathon;

import com.hackathon.apartxhackathon.config.JwtService;
import com.hackathon.apartxhackathon.exception.UserAlreadyExistsException;
import com.hackathon.apartxhackathon.repository.UserRepository;
import com.hackathon.apartxhackathon.request.RegisterRequest;
import com.hackathon.apartxhackathon.service.AuthenticationService;
import com.hackathon.apartxhackathon.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.hackathon.apartxhackathon.user.Role.ADMIN;

@SpringBootApplication
public class ApartXHackathonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApartXHackathonApplication.class, args);
    }
//    @Bean
//    public CommandLineRunner commandLineRunner(
//            AuthenticationService service,
//            UserRepository repository,
//            JwtService jwtService) {
//        return args -> {
//            var admin = RegisterRequest.builder()
//                    .email("adminApartX@gmail.com")
//                    .password("password")
//                    .role(ADMIN)
//                    .build();
//            service.register(admin);
//            User adminUser = repository.findByEmail("adminApartX@gmail.com").orElseThrow(UserAlreadyExistsException::new);
//            System.out.println("Admin (aniyar) token: " + jwtService.generateToken(adminUser));
//
////            var cleaner = RegisterRequest.builder()
////                    .email("durmagambetovaa4@gmail.com")
////                    .password("password")
////                    .role(CLEANER)
////                    .build();
////            System.out.println("CLEANER token: " + service.register(cleaner).getAccessToken());
//
//        };
//    }
}
