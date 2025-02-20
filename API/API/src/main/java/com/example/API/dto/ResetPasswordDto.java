package com.example.API.dto;

public class ResetPasswordDto {
    private String email;
    private String otpCode;
    private String newPassword;
    // Getter và Setter
    public String getNewPassword() {
		return newPassword;
	}
    public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
    public String getOtpCode() {
		return otpCode;
	}
    public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}
    public String getEmail() {
		return email;
	}
    public void setEmail(String email) {
		this.email = email;
	}
}