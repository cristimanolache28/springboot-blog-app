package com.blog.service;


import com.blog.payload.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
