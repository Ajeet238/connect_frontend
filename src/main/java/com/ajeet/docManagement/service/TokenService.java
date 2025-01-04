package com.ajeet.docManagement.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ajeet.docManagement.Repository.TokenRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService {

//    @Autowired
//    private TokenRepository tokenRepository;

//    // Revoke the token during logout
//    public void revokeToken(String token) {
//       // tokenRepository.revokeTokenByToken(token);
//    }
	
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

