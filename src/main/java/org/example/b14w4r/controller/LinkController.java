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
  // this.linkRepository = linkRepository;
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

  // @Requestb144cbf0a10a
  // 4a891864-df65-4e26-acca-cb7869587256
  // ya.ru
  // 4a891864-df65-4e26-acca-cb7869587256
  // 2025-01-16T15:18:31.976+03:00 ERROR 25473 --- [b14w4r] [nio-8080-exec-3]
  // o.a.c.c.C.[.[.[/].[dispatcherServlet] : Servlet.service() for servlet
  // [dispatcherServlet] in context with path [] threw exception [Re
  // quest processing failed: java.lang.IllegalArgumentException: Short URL not
  // found or unauthorized] with root causeHeader("User-UUID")
  @GetMapping("/{shortUrl}")
  // public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl,
  //     @RequestHeader("User-UUID") String userUUID) {
  public ResponseEntity<Map<String, String>> redirectToLongUrl(
      @PathVariable String shortUrl, @RequestHeader("User-UUID") String userUUID) {
    System.out.println(shortUrl);
    System.out.println(userUUID);
    String longUrl = linkService.getLongUrl(shortUrl, userUUID);
    Map<String, String> response = new HashMap<>();
    response.put("longUrl", longUrl);
    return ResponseEntity.ok(response);
    // return ResponseEntity.status(302)
    //     .header("Location", longUrl)
    //     .build();
  }

  @GetMapping("/api/links/all")
  public ResponseEntity<List<Link>> getAllLinks() {
    return ResponseEntity.ok(linkRepository.findAll());
  }
}
