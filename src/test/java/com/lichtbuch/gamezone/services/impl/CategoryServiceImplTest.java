package com.lichtbuch.gamezone.services.impl;

import com.lichtbuch.gamezone.dto.CategoryCreateRequest;
import com.lichtbuch.gamezone.models.Category;
import com.lichtbuch.gamezone.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository repository;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp(){
        when(repository.save(any(Category.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Action", "Puzzle"})
    void create_test(String title) {
        var expected = new Category();
        expected.setTitle(title);

        var request = new CategoryCreateRequest();
        request.setTitle(title);

        var actual = categoryService.create(request);

        assertEquals(expected, actual);
    }
}
