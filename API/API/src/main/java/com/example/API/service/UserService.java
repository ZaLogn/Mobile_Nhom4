package com.example.API.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.API.model.Otp;
import com.example.API.model.User;
import com.example.API.repository.OtpRepository;
import com.example.API.repository.UserRepository;

import jakarta.mail.MessagingException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpService otpService;

    public User registerUser(String username, String password, String email) throws MessagingException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);  // Mã hóa mật khẩu nếu cần
        user.setEmail(email);
        user.setActivated(false);
        userRepository.save(user);

        String otpCode = otpService.generateOtp();
        otpService.saveOtp(email, otpCode);
        emailService.sendOtp(email, otpCode);

        return user;
    }
    public void email(String email) throws MessagingException {
        User user = userRepository.findByEmail(email);
        
        if (user == null) {
            throw new IllegalArgumentException("Email chưa được đăng ký.");
        }

        String otpCode = otpService.generateOtp();
        otpService.saveOtp(email, otpCode);
        emailService.sendOtp(email, otpCode);
    }


    @Autowired
    private OtpRepository otpRepository; // Repository để làm việc với bảng OTP

    public boolean verifyOtp(String email, String otpCode) {
        Otp otp = otpRepository.findByEmailAndOtpCode(email,otpCode);
        
        if (otp != null && otp.getOtpCode().equals(otpCode) && !isOtpExpired(otp.getExpiryTime())) {
            User user = userRepository.findByEmail(email);
            if (user != null) {
                user.setActivated(true); // Kích hoạt tài khoản
                userRepository.save(user);

                otpRepository.delete(otp); // Xóa OTP sau khi xác nhận
                return true;
            }
        }
        return false;
    }

    private boolean isOtpExpired(LocalDateTime expiryTime) {
        return expiryTime.isBefore(LocalDateTime.now());
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password) && user.isActivated()) {
            return user;
        }
        return null;
    }

    public boolean resetPassword(String email, String otpCode, String newPassword) {
        if (otpService.verifyOtp(email, otpCode)) {
            User user = userRepository.findByEmail(email);
            if (user != null) {
                user.setPassword(newPassword);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
