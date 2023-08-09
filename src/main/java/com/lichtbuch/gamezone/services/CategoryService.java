package com.lichtbuch.gamezone.services;

import com.lichtbuch.gamezone.dto.CategoryCreateRequest;
import com.lichtbuch.gamezone.dto.CategoryReplaceRequest;
import com.lichtbuch.gamezone.dto.CategoryUpdateRequest;
import com.lichtbuch.gamezone.models.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category create(CategoryCreateRequest request);

    void delete(Category category);

    Category replace(CategoryReplaceRequest request, Category category);

    Category update(CategoryUpdateRequest request, Category category);

}
