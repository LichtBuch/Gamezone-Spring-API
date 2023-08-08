package com.lichtbuch.gamezone.repositories;

import com.lichtbuch.gamezone.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByDeleted(boolean deleted);

    List<Game> findByWishlisted(boolean wishlisted);

}
