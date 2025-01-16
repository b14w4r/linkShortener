package org.example.b14w4r.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Link {
    @Id
    private UUID id;
    private String longUrl;
    private String shortUrl;
    private int remainingClicks;
    private LocalDateTime expiryTime;
    public UUID getId() {
      return id;
    }
    public void setId(UUID id) {
      this.id = id;
    }
    public String getLongUrl() {
      return longUrl;
    }
    public void setLongUrl(String longUrl) {
      this.longUrl = longUrl;
    }
    public String getShortUrl() {
      return shortUrl;
    }
    public void setShortUrl(String shortUrl) {
      this.shortUrl = shortUrl;
    }
    public int getRemainingClicks() {
      return remainingClicks;
    }
    public void setRemainingClicks(int remainingClicks) {
      this.remainingClicks = remainingClicks;
    }
    public LocalDateTime getExpiryTime() {
      return expiryTime;
    }
    public void setExpiryTime(LocalDateTime expiryTime) {
      this.expiryTime = expiryTime;
    }

    // Геттеры и сеттеры
}

