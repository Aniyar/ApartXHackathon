package com.hackathon.apartxhackathon.service;
import com.hackathon.apartxhackathon.model.Order;
import com.hackathon.apartxhackathon.model.OrderCleanerResponse;
import com.hackathon.apartxhackathon.repository.OrderResponseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final OrderResponseRepository orderResponseRepository;
    @Autowired
    private Environment env;

    public void sendAuthorizationCode(String recipientEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("spring.mail.username"));
        message.setTo(recipientEmail);
        message.setSubject("ApartX: Код для авторизации");
        message.setText("Ваш код для авторизации: " + code);

        mailSender.send(message);
    }

    public void sendCleanersUpdate(OrderCleanerResponse resp) {
        List<OrderCleanerResponse> cleanerResponses = orderResponseRepository.findAllByOrder_Id(resp.getOrder().getId());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("spring.mail.username"));

        Order order = resp.getOrder();
        message.setTo(order.getLandlord().getUser().getEmail());
        message.setSubject(String.format(
                "ApartX: На ваше обьявление отклинулись %d уборщиков!", cleanerResponses.size()));
        message.setText(String.format(
                "Здравствуйте! На ваш заказ OrderId: %d отклинулись %d уборщиков! Лучшая цена: %s. Поспешите подтвердить заказ!",
                order.getId(),
                cleanerResponses.size(),
                getBestPrice(cleanerResponses).orElse(0)
        ));

        mailSender.send(message);
    }

    private Optional<Integer> getBestPrice(List<OrderCleanerResponse> cleanerResponses) {
        if (cleanerResponses.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(
                Collections.min(
                        cleanerResponses.stream()
                                .map(OrderCleanerResponse::getOfferedPrice)
                                .collect(Collectors.toList())
                )
        );
    }
}
