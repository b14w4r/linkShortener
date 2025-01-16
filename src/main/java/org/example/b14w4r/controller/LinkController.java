package org.example.b14w4r.controller;

import org.example.b14w4r.service.LinkService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/links")
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    public String createShortLink(@RequestParam String longUrl, 
                                  @RequestParam int clicks, 
                                  @RequestParam int expiryHours) {
        return linkService.createShortLink(longUrl, clicks, expiryHours);
    }

    @GetMapping("/{shortUrl}")
    public void redirectLink(@PathVariable String shortUrl) {
        linkService.handleRedirect(shortUrl);
    }
}

