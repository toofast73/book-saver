package ru.toofast.booksaver.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;

public interface StorageService {

    void store(MultipartFile file);

    Stream<String> loadAll();

    Resource loadAsResource(String filename);

    void deleteAll();

}