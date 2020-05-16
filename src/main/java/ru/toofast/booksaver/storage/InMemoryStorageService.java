package ru.toofast.booksaver.storage;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public class InMemoryStorageService implements StorageService {

    ConcurrentHashMap<String, Resource> storage = new ConcurrentHashMap<>();

    @Override
    public void store(MultipartFile file) {
        try {
            ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes(), file.getName());
            storage.put(file.getName(), byteArrayResource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Stream<String> loadAll() {
        return storage.keySet().stream();
    }

    @Override
    public Resource loadAsResource(String filename) {
        if (storage.containsKey(filename)) {
            return storage.get(filename);
        } else {
            throw new StorageFileNotFoundException(filename);
        }
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
