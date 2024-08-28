package com.golfsprint.GolfSprint.Email;

import jakarta.annotation.Nullable;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;
    @Override
    @Async
    public void send(String to, String email) {
       try {
           MimeMessage mimeMessage = javaMailSender.createMimeMessage();
           MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"utf-8");
           mimeMessageHelper.setText(email,true);
           mimeMessageHelper.setTo(to);
           mimeMessageHelper.setSubject("Confirm your email.");
           mimeMessageHelper.setFrom("info@golfsprint.com");
           javaMailSender.send(mimeMessage);

       }catch (MessagingException exception){
           LOGGER.error("Failed to send email.",exception);
           throw new IllegalStateException("Failed to send email.");
       }
    }
    @Async
    public void sendUsers(String from, String to,String message ,@Nullable String subject){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"utf-8");
            mimeMessageHelper.setText(message,true);
            mimeMessageHelper.setTo(to);
            if(subject != null) {
                mimeMessageHelper.setSubject("from "+ from + "," +subject);
            }
            else mimeMessageHelper.setSubject("from "+from);
            mimeMessageHelper.setFrom("kubiyigit20@gmail.com");
            javaMailSender.send(mimeMessage);

        }catch (MessagingException exception){
            LOGGER.error("Failed to send email.",exception);
            throw new IllegalStateException("Failed to send email.");
        }
    }
    @Async
    public void sendToMe(@Nullable String email, @Nullable String subject) {
        if(subject != null && email != null) {
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
                mimeMessageHelper.setText(email, true);
                mimeMessageHelper.setTo("kubiyigit20@gmail.com");
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setFrom("kubiyigit20@gmail.com");
                javaMailSender.send(mimeMessage);

            } catch (MessagingException exception) {
                LOGGER.error("Failed to send email.", exception);
            }
        }
    }
}
