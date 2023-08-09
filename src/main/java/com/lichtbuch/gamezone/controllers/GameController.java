package com.lichtbuch.gamezone.controllers;

import com.lichtbuch.gamezone.dto.GameCreateRequest;
import com.lichtbuch.gamezone.dto.GameReplaceRequest;
import com.lichtbuch.gamezone.dto.GameUpdateRequest;
import com.lichtbuch.gamezone.exceptions.BadRequestException;
import com.lichtbuch.gamezone.exceptions.NotFoundException;
import com.lichtbuch.gamezone.exceptions.StorageException;
import com.lichtbuch.gamezone.models.Category;
import com.lichtbuch.gamezone.models.Game;
import com.lichtbuch.gamezone.services.GameService;
import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping
    public List<Game> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Game getOne(@PathVariable("id") @Nullable Game game) throws NotFoundException {
        if (game == null) {
            throw new NotFoundException();
        }

        return game;
    }

    @PostMapping
    public ResponseEntity<Game> create(@Validated @RequestBody GameCreateRequest request) throws NotFoundException {
        var game = service.create(request);
        var location = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder.on(GameController.class).getOne(game))
                .build()
                .toUri();

        return ResponseEntity
                .created(location)
                .body(game);
    }

    @GetMapping("/deleted")
    public List<Game> getDeleted() {
        return service.getDeleted();
    }

    @PatchMapping("/restore/{id}")
    public Game restoreGame(@PathVariable("id") @Nullable Game game) throws NotFoundException {
        if (game == null) {
            throw new NotFoundException();
        }

        return service.restoreGame(game);
    }

    @GetMapping("/wishlisted")
    public List<Game> getWishlisted() {
        return service.getWishlisted();
    }

    @PatchMapping("/{id}/toggle-wishlist")
    public Game toggleWishlisted(@PathVariable("id") @Nullable Game game) throws NotFoundException {
        if (game == null) {
            throw new NotFoundException();
        }

        return service.toggleWishlisted(game);
    }

    @GetMapping("/{id}/recommendation")
    public List<Game> getRecommendations(@PathVariable("id") @Nullable Game game) throws NotFoundException {
        if (game == null) {
            throw new NotFoundException();
        }

        return service.getRecommendations(game);
    }

    @PostMapping("/{gameId}/category/{categoryId}")
    public Game addCategory(
            @PathVariable("gameId") @Nullable Game game,
            @PathVariable("categoryId") @Nullable Category category
    ) throws NotFoundException {
        if (game == null || category == null) {
            throw new NotFoundException();
        }

        return service.addCategory(game, category);
    }

    @DeleteMapping("/{gameId}/category/{categoryId}")
    public Game removeCategory(
            @PathVariable("gameId") @Nullable Game game,
            @PathVariable("categoryId") @Nullable Category category
    ) throws NotFoundException {
        if (game == null || category == null) {
            throw new NotFoundException();
        }

        return service.removeCategory(game, category);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<Game> createImage(
            @PathVariable("id") @Nullable Game game,
            @RequestParam("file") @Nullable MultipartFile file
    ) throws NotFoundException, StorageException, BadRequestException {
        if (game == null) {
            throw new NotFoundException();
        }

        if (file == null) {
            throw new BadRequestException();
        }

        var image = service.createImage(game, file);
        var location = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder.on(ImageController.class).getOne(image))
                .build()
                .toUri();

        return ResponseEntity
                .created(location)
                .body(game);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") @Nullable Game game) throws NotFoundException {
        if (game == null) {
            throw new NotFoundException();
        }

        service.delete(game);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/{id}/category")
    public List<Category> getCategories(@PathVariable("id") @Nullable Game game) throws NotFoundException {
        if (game == null) {
            throw new NotFoundException();
        }

        return game.getCategories();
    }

    @PutMapping("/{id}")
    public Game put(@PathVariable("id") @Nullable Game game, @Validated @RequestBody GameReplaceRequest request) throws NotFoundException {
        if (game == null) {
            throw new NotFoundException();
        }

        return service.replace(request, game);
    }

    @PatchMapping("/{id}")
    public Game patch(@PathVariable("id") @Nullable Game game, @Validated @RequestBody GameUpdateRequest request) throws NotFoundException {
        if (game == null){
            throw new NotFoundException();
        }

        return service.update(request, game);
    }

}
