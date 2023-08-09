package com.lichtbuch.gamezone.services;

import com.lichtbuch.gamezone.dto.GameCreateRequest;
import com.lichtbuch.gamezone.dto.GameReplaceRequest;
import com.lichtbuch.gamezone.dto.GameUpdateRequest;
import com.lichtbuch.gamezone.exceptions.StorageException;
import com.lichtbuch.gamezone.models.Category;
import com.lichtbuch.gamezone.models.Game;
import com.lichtbuch.gamezone.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GameService {

    List<Game> getAll();

    Game create(GameCreateRequest request);

    List<Game> getDeleted();

    Game restoreGame(Game game);

    List<Game> getWishlisted();

    Game toggleWishlisted(Game game);

    List<Game> getRecommendations(Game game);

    Game addCategory(Game game, Category category);

    Game removeCategory(Game game, Category category);

    Image createImage(Game game, MultipartFile file) throws StorageException;

    void delete(Game game);

    Game replace(GameReplaceRequest request, Game game);

    Game update(GameUpdateRequest request, Game game);

}
