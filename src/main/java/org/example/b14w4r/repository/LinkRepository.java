package org.example.b14w4r.repository;

import java.util.UUID;

import org.example.b14w4r.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface LinkRepository extends JpaRepository<Link, UUID> {
  Optional<Link> findByShortUrlAndUserUUID(String shortUrl, UUID userUUID);
  List<Link> findAll();
}
