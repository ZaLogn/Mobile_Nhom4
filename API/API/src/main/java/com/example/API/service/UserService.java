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

    @Autowired
    private OtpRepository otpRepository;

    // Đăng ký người dùng
    public User registerUser(String hoTen, String matKhau, String email, String sdt, String diaChi, String avatar) throws MessagingException {
        // Kiểm tra email đã tồn tại chưa
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }

        User user = new User();
        user.setHoTen(hoTen);
        user.setMatKhau(matKhau); // lưu thẳng mật khẩu thuần
        user.setEmail(email);
        user.setSdt(sdt);
        user.setDiaChi(diaChi);
        user.setAvatar(avatar);

        userRepository.save(user);

        // Gửi OTP để xác thực email (nếu cần)
        //String otpCode = otpService.generateOtp();
        //otpService.saveOtp(email, otpCode);
        //emailService.sendOtp(email, otpCode);

        return user;
    }

    // Gửi lại OTP
    public void sendOtpToEmail(String email) throws MessagingException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Email chưa được đăng ký.");
        }

        String otpCode = otpService.generateOtp();
        otpService.saveOtp(email, otpCode);
        emailService.sendOtp(email, otpCode);
    }

    // Xác thực OTP
    public boolean verifyOtp(String email, String otpCode) {
        Otp otp = otpRepository.findByEmailAndOtpCode(email, otpCode);

        return otp != null && otp.getOtpCode().equals(otpCode) && !isOtpExpired(otp.getExpiryTime());
    }


    private boolean isOtpExpired(LocalDateTime expiryTime) {
        return expiryTime.isBefore(LocalDateTime.now());
    }

    // Đăng nhập người dùng
    public User loginUser(String email, String matKhau) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getMatKhau().equals(matKhau)) {
            return user;
        }
        return null;
    }

    // Đổi mật khẩu thông qua OTP
    public boolean resetPassword(String email, String otpCode, String newPassword) {
    	Otp otp = otpRepository.findByEmailAndOtpCode(email, otpCode);

        if (otp == null) {
            throw new IllegalArgumentException("Mã OTP không hợp lệ.");
        }

        if (isOtpExpired(otp.getExpiryTime())) {
            throw new IllegalArgumentException("Mã OTP đã hết hạn.");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Người dùng không tồn tại.");
        }

        user.setMatKhau(newPassword);
        userRepository.save(user);

        // Xóa OTP sau khi đặt lại mật khẩu thành công
        otpRepository.delete(otp);

        return true;
    }

   


}
