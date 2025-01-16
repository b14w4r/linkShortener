package org.example.b14w4r.controller;

import org.springframework.http.ResponseEntity;
import org.example.b14w4r.service.LinkService;
import org.example.b14w4r.model.Link;
import org.example.b14w4r.repository.LinkRepository;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/links")
public class LinkController {
  private final LinkService linkService;

  private final LinkRepository linkRepository;

  public LinkController(LinkService linkService, LinkRepository linkRepository) {
    this.linkService = linkService;
    this.linkRepository = linkRepository;
  }


  // // Конструктор для внедрения зависимости
  // public LinkController(LinkRepository linkRepository) {
  //   this.linkRepository = linkRepository;
  // }

  @PostMapping
  public ResponseEntity<Map<String, String>> createShortLink(
      @RequestBody Map<String, Object> payload) {
    String longUrl = (String) payload.get("longUrl");
    int customClicks = (int) payload.getOrDefault("clicks", 0);
    int customExpiryHours = (int) payload.getOrDefault("expiryHours", 0);

    // Генерация UUID для пользователя
    UUID userUUID = UUID.randomUUID();

    // Генерация короткой ссылки через сервис
    String shortUrl = linkService.createShortLink(longUrl, customClicks, customExpiryHours, userUUID);

    // Возвращаем короткую ссылку и UUID пользователя
    Map<String, String> response = new HashMap<>();
    response.put("shortUrl", shortUrl);
    response.put("userUUID", userUUID.toString());
    return ResponseEntity.ok(response);
  }

// @RequestHeader("User-UUID")
  @GetMapping("/{shortUrl}")
  public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl, @PathVariable String userUUID) {
    String longUrl = linkService.getLongUrl(shortUrl, userUUID);
    return ResponseEntity.status(302)
        .header("Location", longUrl)
        .build();
  }

  @GetMapping("/api/links/all")
  public ResponseEntity<List<Link>> getAllLinks() {
    return ResponseEntity.ok(linkRepository.findAll());
  }
}
