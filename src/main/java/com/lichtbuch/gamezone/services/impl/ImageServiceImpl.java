package com.lichtbuch.gamezone.services.impl;

import com.lichtbuch.gamezone.models.Image;
import com.lichtbuch.gamezone.repositories.ImageRepository;
import com.lichtbuch.gamezone.services.ImageService;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository repository;

    public ImageServiceImpl(ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(Image image) {
        //No need to delete Image File via StorageService this is handled by ImageListener after ImageRepository::delete
        repository.delete(image);
    }

}
