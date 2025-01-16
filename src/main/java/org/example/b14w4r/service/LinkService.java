package org.example.b14w4r.service;

import org.springframework.stereotype.Service;

@Service
public class LinkService {
    public String createShortLink(String longUrl, int clicks, int expiryHours) {
        // Логика создания ссылки
        return "shortUrl";
    }

    public void handleRedirect(String shortUrl) {
        // Логика перенаправления
    }
}

