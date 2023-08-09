package com.lichtbuch.gamezone.controllers;

import com.lichtbuch.gamezone.dto.CategoryCreateRequest;
import com.lichtbuch.gamezone.dto.CategoryReplaceRequest;
import com.lichtbuch.gamezone.dto.CategoryUpdateRequest;
import com.lichtbuch.gamezone.exceptions.NotFoundException;
import com.lichtbuch.gamezone.models.Category;
import com.lichtbuch.gamezone.services.CategoryService;
import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/category")
    public List<Category> getAll() {
        return service.getAll();
    }

    @GetMapping("/category/{id}")
    public Category getOne(@PathVariable("id") @Nullable Category category) throws NotFoundException {
        if (category == null) {
            throw new NotFoundException();
        }

        return category;
    }

    @PostMapping("/category")
    public ResponseEntity<Category> create(@Validated @RequestBody CategoryCreateRequest request) throws NotFoundException {
        var category = service.create(request);

        var location = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder.on(CategoryController.class).getOne(category))
                .build()
                .toUri();

        return ResponseEntity
                .created(location)
                .body(category);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") @Nullable Category category) throws NotFoundException {
        if (category == null) {
            throw new NotFoundException();
        }

        service.delete(category);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/category/{id}")
    public Category put(@PathVariable("id") @Nullable Category category, @Validated @RequestBody CategoryReplaceRequest request) throws NotFoundException {
        if (category == null) {
            throw new NotFoundException();
        }

        return service.replace(request, category);
    }

    @PatchMapping("/category/{id}")
    public Category patch(@PathVariable("id") @Nullable Category category, @Validated CategoryUpdateRequest request) throws NotFoundException {
        if (category == null) {
            throw new NotFoundException();
        }

        return service.update(request, category);
    }


}
