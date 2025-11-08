package Chackaton.com.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmationEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Подтверждение регистрации на UMIRHACK");
        message.setText("Дорогая вот твой персональны пароль для входа, ЛЮБЛЮ ТЕБЯ)): " + code);
        mailSender.send(message);
    }



    public void sendPasswordResetCodeEmail(String to , String code){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Сброс пароля для UMIRCHAK");
        message.setText("Ваш код для сброса пароля: " + code + "\n\n" +
                "Это код истечёт через 15 минут");
        mailSender.send(message);
    }
}


