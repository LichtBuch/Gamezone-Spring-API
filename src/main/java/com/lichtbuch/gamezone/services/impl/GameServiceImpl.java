package com.lichtbuch.gamezone.services.impl;

import com.lichtbuch.gamezone.dto.GameCreateRequest;
import com.lichtbuch.gamezone.dto.GameUpdateRequest;
import com.lichtbuch.gamezone.exceptions.StorageException;
import com.lichtbuch.gamezone.models.Category;
import com.lichtbuch.gamezone.models.Game;
import com.lichtbuch.gamezone.models.Image;
import com.lichtbuch.gamezone.repositories.GameRepository;
import com.lichtbuch.gamezone.repositories.ImageRepository;
import com.lichtbuch.gamezone.services.GameService;
import com.lichtbuch.gamezone.services.StorageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository repository;
    private final ModelMapper mapper;
    private final StorageService storageService;
    private final ImageRepository imageRepository;

    public GameServiceImpl(GameRepository repository, ModelMapper mapper, StorageService storageService, ImageRepository imageRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.storageService = storageService;
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Game> getAll() {
        return repository.findByDeleted(false);
    }

    @Override
    public Game create(GameCreateRequest request) {
        var game = mapper.map(request, Game.class);
        return repository.save(game);
    }

    @Override
    public List<Game> getDeleted() {
        return repository.findByDeleted(true);
    }

    @Override
    public Game restoreGame(Game game) {
        game.setDeleted(false);
        return repository.save(game);
    }

    @Override
    public List<Game> getWishlisted() {
        return repository.findByWishlisted(true);
    }

    @Override
    public Game toggleWishlisted(Game game) {
        game.setWishlisted(!game.isWishlisted());
        return repository.save(game);
    }

    @Override
    public List<Game> getRecommendations(Game game) {
        return game
                .getCategories()
                .stream()
                .map(Category::getGames)
                .flatMap(List::stream)
                .filter(item -> item != game)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Game addCategory(Game game, Category category) {
        category.getGames().add(game);
        game.getCategories().add(category);

        return repository.save(game);
    }

    @Override
    public Game removeCategory(Game game, Category category) {
        category.getGames().remove(game);
        game.getCategories().remove(category);

        return repository.save(game);
    }

    @Override
    public Image createImage(Game game, MultipartFile file) throws StorageException {
        var filename = storageService.store(file);

        var image = new Image();
        image.setPath(filename);
        image.setGame(game);

        return imageRepository.save(image);
    }

    @Override
    public void delete(Game game) {
        game.setDeleted(true);
        repository.save(game);
    }

    @Override
    public Game replace(Game game) {
        return repository.save(game);
    }

    @Override
    public Game update(GameUpdateRequest request, Game game){
        mapper.map(request, game);
        return repository.save(game);
    }

}
