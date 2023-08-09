package com.lichtbuch.gamezone.controllers;

import com.lichtbuch.gamezone.exceptions.NotFoundException;
import com.lichtbuch.gamezone.exceptions.StorageException;
import com.lichtbuch.gamezone.models.Image;
import com.lichtbuch.gamezone.services.ImageService;
import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Image getOne(@PathVariable("id") @Nullable Image image) throws NotFoundException {
        if (image == null){
            throw new NotFoundException();
        }

        return image;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") @Nullable Image image
    ) throws NotFoundException, StorageException {
        if (image == null){
            throw new NotFoundException();
        }

        service.delete(image);

        return ResponseEntity
                .noContent()
                .build();
    }

}
