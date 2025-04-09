package com.example.API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API.dto.EmailRequest;
import com.example.API.dto.LoginDto;
import com.example.API.dto.ResetPasswordDto;
import com.example.API.dto.UserDto;
import com.example.API.dto.VerifyOtpDto;
import com.example.API.model.Otp;
import com.example.API.model.User;
import com.example.API.repository.UserRepository;
import com.example.API.service.OtpService;
import com.example.API.service.UserService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private OtpService otpService;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        try {
            userService.registerUser(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
            return ResponseEntity.ok("Registration successful. Please check your email for the OTP.");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending OTP.");
        }
    }
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody UserDto userDto) {
        try {
            userService.email(userDto.getEmail());
            return ResponseEntity.ok("OTP đã được gửi. Vui lòng kiểm tra email của bạn.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi gửi OTP.");
        }
    }

    
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpDto verifyOtpDto) {
        boolean isVerified = userService.verifyOtp(verifyOtpDto.getEmail(), verifyOtpDto.getOtpCode());
        if (isVerified) {
            return ResponseEntity.ok("OTP verified successfully. Account activated.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP.");
        }
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        User user = userService.loginUser(loginDto.getEmail(), loginDto.getPassword());
        if (user != null) {
            return ResponseEntity.ok("Login successful.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials or account not activated.");
        }
    }
   
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto request) {
        boolean success = userService.resetPassword(request.getEmail(), request.getOtpCode(), request.getNewPassword());
        if (success) {
            return ResponseEntity.ok("Mật khẩu đã được đặt lại thành công.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP không hợp lệ hoặc tài khoản không tồn tại.");
    }
}
