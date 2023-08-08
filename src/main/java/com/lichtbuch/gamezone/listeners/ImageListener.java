package com.lichtbuch.gamezone.listeners;

import com.lichtbuch.gamezone.exceptions.StorageException;
import com.lichtbuch.gamezone.models.Image;
import com.lichtbuch.gamezone.services.StorageService;
import jakarta.persistence.PostRemove;

public class ImageListener {

    private final StorageService storageService;

    public ImageListener(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostRemove
    public void handleAfterDelete(Image image) throws StorageException {
        storageService.delete(image.getPath());
    }

}
