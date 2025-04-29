package com.example.API.controller;

import com.example.API.dto.EmailRequest;
import com.example.API.dto.LoginDto;
import com.example.API.dto.ResetPasswordDto;
import com.example.API.dto.SendOtpRequest;
import com.example.API.model.User;
import com.example.API.service.UserService;
import jakarta.mail.MessagingException;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        User user = userService.loginUser(loginDto.getEmail(), loginDto.getMatKhau());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().body("Sai email hoặc mật khẩu.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) throws MessagingException {
        User registeredUser = userService.registerUser(
                user.getHoTen(),
                user.getMatKhau(),
                user.getEmail(),
                user.getSdt(),
                user.getDiaChi(),
                user.getAvatar()
        );
        return ResponseEntity.ok(registeredUser);
    }

    /** Gửi OTP nhận JSON {"email":"..."} */
    @PostMapping("/send")
    public ResponseEntity<?> sendOtp(@RequestBody SendOtpRequest request) throws MessagingException {
        String email = request.getEmail();
        userService.sendOtpToEmail(email);
        return ResponseEntity.ok("Đã gửi OTP tới email.");
    }
/**
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        boolean verified = userService.verifyOtp(email, otp);
        if (verified) {
            return ResponseEntity.ok("Xác thực OTP thành công.");
        } else {
            return ResponseEntity.badRequest().body("OTP không hợp lệ hoặc đã hết hạn.");
        }
    }**/


    // API đặt lại mật khẩu bằng OTP
    /** Reset password (cũng có thể tiếp tục dùng JSON) */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto request) {
        boolean success = userService.resetPassword(
            request.getEmail(),
            request.getOtpCode(),
            request.getNewPassword()
        );
        if (success) {
            return ResponseEntity.ok("Đặt lại mật khẩu thành công.");
        } else {
            return ResponseEntity.badRequest().body("Đặt lại mật khẩu thất bại.");
        }
    }
}
