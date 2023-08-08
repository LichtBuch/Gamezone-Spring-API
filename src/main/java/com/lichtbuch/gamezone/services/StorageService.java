package com.lichtbuch.gamezone.services;

import com.lichtbuch.gamezone.exceptions.StorageException;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String store(MultipartFile file) throws StorageException;

    void delete(String filename) throws StorageException;

}
