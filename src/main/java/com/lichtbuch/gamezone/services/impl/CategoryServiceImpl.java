package com.lichtbuch.gamezone.services.impl;

import com.lichtbuch.gamezone.dto.CategoryCreateRequest;
import com.lichtbuch.gamezone.dto.CategoryUpdateRequest;
import com.lichtbuch.gamezone.models.Category;
import com.lichtbuch.gamezone.repositories.CategoryRepository;
import com.lichtbuch.gamezone.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Category> getAll() {
        return repository.findAllByOrderByTitleAsc();
    }

    @Override
    public Category create(CategoryCreateRequest request) {
        var category = mapper.map(request, Category.class);
        return repository.save(category);
    }

    @Override
    public void delete(Category category) {
        repository.delete(category);
    }

    @Override
    public Category replace(Category category) {
        return repository.save(category);
    }

    @Override
    public Category update(CategoryUpdateRequest request, Category category) {
        mapper.map(request, category);
        return repository.save(category);
    }

}
