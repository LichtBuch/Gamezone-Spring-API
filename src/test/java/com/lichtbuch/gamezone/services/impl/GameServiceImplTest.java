package com.lichtbuch.gamezone.services.impl;

import com.lichtbuch.gamezone.dto.GameCreateRequest;
import com.lichtbuch.gamezone.exceptions.StorageException;
import com.lichtbuch.gamezone.models.Category;
import com.lichtbuch.gamezone.models.Game;
import com.lichtbuch.gamezone.models.Image;
import com.lichtbuch.gamezone.repositories.GameRepository;
import com.lichtbuch.gamezone.repositories.ImageRepository;
import com.lichtbuch.gamezone.services.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {

    @Mock
    private GameRepository repository;

    @Spy
    private ModelMapper mapper;

    @Mock
    private StorageService storageService;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private GameServiceImpl service;

    @BeforeEach
    void mockRepository(TestInfo info) {
        if (!info.getTags().contains("NoRepository")) {
            when(repository.save(any(Game.class)))
                    .thenAnswer(i -> i.getArguments()[0]);
        }
    }


    @ParameterizedTest
    @MethodSource("testCreateArguments")
    void testCreate(GameCreateRequest request, Game expected) {
        assertEquals(expected, service.create(request));
    }

    private static Stream<Arguments> testCreateArguments() {
        return Stream.of(
                Arguments.arguments(
                        new GameCreateRequest("Portal 2", "Cool Puzzle Game", LocalDate.of(2011, Month.APRIL, 18), new BigDecimal("9.75"), (byte) 5, false, true),
                        new Game("Portal 2", "Cool Puzzle Game", LocalDate.of(2011, Month.APRIL, 18), new BigDecimal("9.75"), (byte) 5, false, true)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("testRestoreGameArguments")
    void testRestoreGame(Game game) {
        service.restoreGame(game);
        assertFalse(game.isDeleted());
    }

    private static Stream<Arguments> testRestoreGameArguments() {
        return Stream.of(
                Arguments.arguments(new Game("Portal 2", "Cool Puzzle Game", LocalDate.of(2011, Month.APRIL, 18), new BigDecimal("9.75"), (byte) 5, true, true)),
                Arguments.arguments(new Game("Portal 2", "Cool Puzzle Game", LocalDate.of(2011, Month.APRIL, 18), new BigDecimal("9.75"), (byte) 5, false, true))
        );
    }

    @ParameterizedTest
    @MethodSource("testToggleWishlistedArguments")
    void testToggleWishlisted(Game game, boolean expected) {
        service.toggleWishlisted(game);
        assertEquals(game.isWishlisted(), expected);
    }

    private static Stream<Arguments> testToggleWishlistedArguments() {
        return Stream.of(
                Arguments.arguments(new Game("Portal 2", "Cool Puzzle Game", LocalDate.of(2011, Month.APRIL, 18), new BigDecimal("9.75"), (byte) 5, true, true), false),
                Arguments.arguments(new Game("Portal 2", "Cool Puzzle Game", LocalDate.of(2011, Month.APRIL, 18), new BigDecimal("9.75"), (byte) 5, false, false), true)
        );
    }

    @Test
    void testAddCategory() {
        var game = new Game();
        var category = new Category();
        service.addCategory(game, category);
        assertTrue(game.getCategories().contains(category) && category.getGames().contains(game));
    }

    @Test
    void testRemoveCategory(){
        var game = new Game();
        var category = new Category();
        game.getCategories().add(category);
        category.getGames().add(game);

        service.removeCategory(game, category);

        assertTrue(!game.getCategories().contains(category) && !category.getGames().contains(game));
    }

    @Test
    @Tag("NoRepository")
    void testCreateImage() throws StorageException {
        var game = new Game();
        var file = mock(MultipartFile.class);

        var filename = "test.jpg";
        when(storageService.store(any(MultipartFile.class)))
                .thenReturn(filename);

        when(imageRepository.save(any(Image.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        var image = service.createImage(game, file);

        assertEquals(image.getPath(), filename);
        assertEquals(game, image.getGame());
    }

    @ParameterizedTest
    @MethodSource("testDeleteArguments")
    void testDelete(Game game){
        service.delete(game);
        assertTrue(game.isDeleted());
    }

    private static Stream<Arguments> testDeleteArguments() {
        return Stream.of(
                Arguments.arguments(new Game("Portal 2", "Cool Puzzle Game", LocalDate.of(2011, Month.APRIL, 18), new BigDecimal("9.75"), (byte) 5, true, true)),
                Arguments.arguments(new Game("Portal 2", "Cool Puzzle Game", LocalDate.of(2011, Month.APRIL, 18), new BigDecimal("9.75"), (byte) 5, false, false))
        );
    }

}
