package com.example.API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API.dto.LoginDto;
import com.example.API.dto.ResetPasswordDto;
import com.example.API.dto.UserDto;
import com.example.API.dto.VerifyOtpDto;
import com.example.API.model.User;
import com.example.API.service.UserService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        try {
            userService.registerUser(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
            return ResponseEntity.ok("Registration successful. Please check your email for the OTP.");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending OTP.");
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
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        boolean isReset = userService.resetPassword(resetPasswordDto.getEmail(), resetPasswordDto.getOtpCode(), resetPasswordDto.getNewPassword());
        if (isReset) {
            return ResponseEntity.ok("Password reset successful.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP or expired.");
        }
    }
}
