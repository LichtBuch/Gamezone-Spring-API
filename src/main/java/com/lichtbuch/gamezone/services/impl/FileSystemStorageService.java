package com.lichtbuch.gamezone.services.impl;

import com.lichtbuch.gamezone.configs.StorageConfig;
import com.lichtbuch.gamezone.exceptions.StorageException;
import com.lichtbuch.gamezone.services.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService(StorageConfig storage) {
        rootLocation = Paths.get(storage.getLocation());
    }

    @Override
    public String store(MultipartFile file) throws StorageException {
        if (IsEmpty(file)) {
            throw new StorageException("Failed to store empty file.");
        }

        var filename = file.getOriginalFilename();
        var destinationFilePath = createPath(filename);

        if (!isInRootLocation(destinationFilePath)) {
            throw new StorageException("Cannot store file outside current directory.");
        }

        writeFile(file, destinationFilePath);

        return filename;
    }

    @Override
    public void delete(String filename) throws StorageException {
        var file = createPath(filename);
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Failed to write file.", e);
        }
    }

    private boolean IsEmpty(MultipartFile file) {
        return file.isEmpty() || file.getOriginalFilename() == null;
    }

    private void writeFile(MultipartFile file, Path destinationFile) throws StorageException {
        try (var inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    private boolean isInRootLocation(Path path) {
        return path.getParent().equals(rootLocation.toAbsolutePath());
    }

    private Path createPath(String filename) {
        return rootLocation
                .resolve(Paths.get(filename))
                .normalize()
                .toAbsolutePath();
    }

}
