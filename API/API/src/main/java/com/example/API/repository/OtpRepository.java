package com.example.API.repository;

import com.example.API.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    // Tìm OTP theo email và mã OTP
    Otp findByEmailAndOtpCode(String email, String otpCode);
}
