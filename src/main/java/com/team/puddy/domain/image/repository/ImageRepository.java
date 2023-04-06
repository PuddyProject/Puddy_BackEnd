package com.team.puddy.domain.image.repository;

import com.team.puddy.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
