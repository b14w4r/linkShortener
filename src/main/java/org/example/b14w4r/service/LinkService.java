package org.example.b14w4r.service;

import org.springframework.stereotype.Service;
import org.example.b14w4r.model.Link;
import org.example.b14w4r.repository.LinkRepository;
// import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LinkService {

  public String createShortLink(String longUrl, int customClicks, int customExpiryHours, UUID userUUID) {
    // Значения по умолчанию
    int defaultClicks = 20;
    int defaultExpiryHours = 24;

    // Проверка пользовательских параметров
    int finalClicks = customClicks > 0 && customClicks <= defaultClicks ? customClicks : defaultClicks;
    int finalExpiryHours = customExpiryHours > 0 && customExpiryHours <= defaultExpiryHours ? customExpiryHours
        : defaultExpiryHours;

    // Создание короткой ссылки
    String shortUrl = "b144" + UUID.randomUUID().toString().substring(0, 8);

    // Установка срока действия
    LocalDateTime expiryTime = LocalDateTime.now().plusHours(finalExpiryHours);
    // UUID.randomUUID()
    // Сохранение в базе данных
    Link link = new Link();
    link.setLongUrl(longUrl);
    link.setShortUrl(shortUrl);
    link.setUserUUID(userUUID);
    link.setRemainingClicks(finalClicks);
    link.setExpiryTime(expiryTime);

    linkRepository.save(link);

    return shortUrl;
  }

  private final LinkRepository linkRepository;

  public LinkService(LinkRepository linkRepository) {
    this.linkRepository = linkRepository;
  }

  public String getLongUrl(String shortUrl, String userUUID) {
    // Проверка существования ссылки и ее валидности
    Link link = linkRepository.findByShortUrlAndUserUUID(shortUrl, UUID.fromString(userUUID))
        .orElseThrow(() -> new IllegalArgumentException("Short URL not found or unauthorized"));

    if (link.getExpiryTime().isBefore(LocalDateTime.now())) {
      linkRepository.delete(link);
      throw new IllegalArgumentException("Short URL has expired");
    }

    if (link.getRemainingClicks() <= 0) {
      throw new IllegalArgumentException("No remaining clicks for this short URL");
    }

    // Обновление оставшихся кликов
    link.setRemainingClicks(link.getRemainingClicks() - 1);
    linkRepository.save(link);

    return link.getLongUrl();
  }
}
