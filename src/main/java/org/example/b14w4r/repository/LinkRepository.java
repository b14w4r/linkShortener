package org.example.b14w4r.repository;

import java.util.UUID;

import org.example.b14w4r.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, UUID> {
}

