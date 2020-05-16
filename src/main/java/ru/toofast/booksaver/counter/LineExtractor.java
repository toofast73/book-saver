package ru.toofast.booksaver.counter;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.toofast.booksaver.storage.StorageService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LineExtractor {

    private StorageService storageService;

    public LineExtractor(StorageService storageService) {
        this.storageService = storageService;
    }

    public List<String> extractLines(String filename) {
        Resource resource = storageService.loadAsResource(filename);

        try(InputStream is = resource.getInputStream()) {
            return new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
