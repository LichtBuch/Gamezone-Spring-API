package com.lichtbuch.gamezone.controllers;

import com.lichtbuch.gamezone.dto.GameCreateRequest;
import com.lichtbuch.gamezone.dto.GameUpdateRequest;
import com.lichtbuch.gamezone.exceptions.BadRequestException;
import com.lichtbuch.gamezone.exceptions.NotFoundException;
import com.lichtbuch.gamezone.exceptions.StorageException;
import com.lichtbuch.gamezone.models.Category;
import com.lichtbuch.gamezone.models.Game;
import com.lichtbuch.gamezone.services.GameService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@RestController
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping("game")
    public List<Game> getAll() {
        return service.getAll();
    }

    @GetMapping("game/{id}")
    public Game getOne(@PathVariable("id") @Nullable Game game) throws NotFoundException {
        if (game == null) {
            throw new NotFoundException();
        }

        return game;
    }

    @PostMapping("/game")
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

    @GetMapping("/game/deleted")
    public List<Game> getDeleted() {
        return service.getDeleted();
    }

    @PatchMapping("/game/restore/{id}")
    public Game restoreGame(@PathVariable("id") @Nullable Game game) throws NotFoundException {
        if (game == null) {
            throw new NotFoundException();
        }

        return service.restoreGame(game);
    }

    @GetMapping("/game/wishlisted")
    public List<Game> getWishlisted() {
        return service.getWishlisted();
    }

    @PatchMapping("/game/{id}/toggle-wishlist")
    public Game toggleWishlisted(@PathVariable("id") @Nullable Game game) throws NotFoundException {
        if (game == null) {
            throw new NotFoundException();
        }

        return service.toggleWishlisted(game);
    }

    @GetMapping("/game/{id}/recommendation")
    public List<Game> getRecommendations(@PathVariable("id") @Nullable Game game) throws NotFoundException {
        if (game == null) {
            throw new NotFoundException();
        }

        return service.getRecommendations(game);
    }

    @PostMapping("/game/{gameId}/category/{categoryId}")
    public Game addCategory(
            @PathVariable("gameId") @Nullable Game game,
            @PathVariable("categoryId") @Nullable Category category
    ) throws NotFoundException {
        if (game == null || category == null) {
            throw new NotFoundException();
        }

        return service.addCategory(game, category);
    }

    @DeleteMapping("/game/{gameId}/category/{categoryId}")
    public Game removeCategory(
            @PathVariable("gameId") @Nullable Game game,
            @PathVariable("categoryId") @Nullable Category category
    ) throws NotFoundException {
        if (game == null || category == null) {
            throw new NotFoundException();
        }

        return service.removeCategory(game, category);
    }

    @PostMapping("/game/{id}/image")
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

    @DeleteMapping("/game/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") @Nullable Game game) throws NotFoundException {
        if (game == null) {
            throw new NotFoundException();
        }

        service.delete(game);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/game/{id}/category")
    public List<Category> getCategories(@PathVariable("id") @Nullable Game game) throws NotFoundException {
        if (game == null) {
            throw new NotFoundException();
        }

        return game.getCategories();
    }

    @PutMapping("/game/{id}")
    public Game put(@PathVariable long id, @Valid @RequestBody Game game) throws BadRequestException {
        if (game.getId() != id){
            throw new BadRequestException();
        }

        return service.replace(game);
    }

    @PatchMapping("/game/{id}")
    public Game patch(@PathVariable("id") @Nullable Game game, @Validated @RequestBody GameUpdateRequest request) throws NotFoundException {
        if (game == null){
            throw new NotFoundException();
        }

        return service.update(request, game);
    }

}
