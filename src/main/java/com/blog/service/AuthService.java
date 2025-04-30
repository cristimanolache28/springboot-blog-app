package com.blog.service;


import com.blog.payload.LoginDto;
import com.blog.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
