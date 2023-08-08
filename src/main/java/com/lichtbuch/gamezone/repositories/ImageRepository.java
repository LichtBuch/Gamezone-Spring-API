package com.lichtbuch.gamezone.repositories;

import com.lichtbuch.gamezone.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
