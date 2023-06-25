package com.hackathon.apartxhackathon.service;
import com.hackathon.apartxhackathon.model.Order;
import com.hackathon.apartxhackathon.model.OrderCleanerResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
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
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("spring.mail.username"));

        Order order = resp.getOrder();
        message.setTo(order.getLandlord().getUser().getEmail());
        message.setSubject(String.format(
                "ApartX: На ваше обьявление отклинулись %d уборщиков!", order.respondedCleanerList.size()));
        message.setText(String.format(
                "Здравствуйте! На ваш заказ OrderId: %d отклинулись %d уборщиков! Лучшая цена: %s. Поспешите подтвердить заказ!",
                order.getId(),
                order.respondedCleanerList.size(),
                order.getBestPrice().orElse(0)
        ));

        mailSender.send(message);
    }
}
