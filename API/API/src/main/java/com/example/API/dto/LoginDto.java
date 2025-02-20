package com.example.API.dto;

public class LoginDto {
    private String email;
    private String password;
    // Getter và Setter
    public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}