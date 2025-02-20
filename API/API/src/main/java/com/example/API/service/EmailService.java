package com.example.API.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    // Lấy email người gửi từ application.properties (spring.mail.username)
    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendOtp(String to, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otpCode);
        
        mailSender.send(message);
        logger.info("OTP email sent to {}", to);
    }

    public void sendEmail(String toEmail, String subject, String text) throws MessagingException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            // Sử dụng email người gửi được cấu hình
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            // true: hỗ trợ HTML
            helper.setText(text, true);

            mailSender.send(message);
            logger.info("Email sent to: {}", toEmail);
        } catch (MailException e) {
            logger.error("Error sending email to {}: {}", toEmail, e.getMessage());
            throw new MessagingException("Error sending email: " + e.getMessage(), e);
        }
    }
}
