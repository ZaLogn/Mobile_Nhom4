package com.example.API.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.API.model.Otp;
import com.example.API.repository.OtpRepository;



@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    public String generateOtp() {
        return String.valueOf(new Random().nextInt(999999));
    }

    public void saveOtp(String email, String otpCode) {
        Otp otp = new Otp();
        otp.setEmail(email);
        otp.setOtpCode(otpCode);
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));  // OTP hết hạn sau 5 phút
        otpRepository.save(otp);
    }
    
    

    public boolean verifyOtp(String email, String otpCode) {
        Otp otp = otpRepository.findByEmailAndOtpCode(email, otpCode);
        if (otp != null && otp.getExpiryTime().isAfter(LocalDateTime.now())) {
            return true;
        }
        return false;
    }
}
