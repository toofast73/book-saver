package ru.toofast.booksaver.counter;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.toofast.booksaver.counter.dto.OperationStatus;
import ru.toofast.booksaver.counter.dto.WordCountResponse;
import ru.toofast.booksaver.storage.StorageService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.regex.Pattern;

@Service
public class WordCountService {

    private final StorageService storageService;

    public WordCountService(StorageService storageService) {
        this.storageService = storageService;
    }

    public WordCountResponse countWordsInFile(String filename) {
        List<String> lines = extractLines(filename);
        return countWords(lines);

    }

    private WordCountResponse countWords(List<String> lines) {
        Optional<Map<String, Long>> aggregatedResult = lines.parallelStream().map(this::countWordsInLine).reduce(new BinaryOperator<Map<String, Long>>() {
            @Override
            public Map<String, Long> apply(Map<String, Long> first, Map<String, Long> second) {
                for (Map.Entry<String, Long> stringLongEntry : second.entrySet()) {
                    first.merge(stringLongEntry.getKey(), 1L, Long::sum);
                }
                return first;
            }
        });
        if (aggregatedResult.isPresent()) {
            return new WordCountResponse(OperationStatus.SUCCESS, aggregatedResult.get());
        } else {
            return new WordCountResponse(OperationStatus.SKIPPED, new HashMap<>());
        }
    }

    private Map<String, Long> countWordsInLine(String line) {

        List<String> strings = prepareWords(line);
        return toMap(strings);

    }

    private Map<String, Long> toMap(List<String> strings) {
        Map<String, Long> result = new HashMap<>();
        for (String string : strings) {
            result.merge(string, 1L, Long::sum);
        }

        return result;
    }

    private List<String> prepareWords(String line) {
        List<String> words = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(line, ";,.?\"\t\n\r\f-");
        while (stringTokenizer.hasMoreTokens()) {
            words.add(stringTokenizer.nextToken());
        }
        return words;
    }

    private List<String> extractLines(String filename) {
        Resource resource = storageService.loadAsResource(filename);
        List<String> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(resource.getInputStream(), StandardCharsets.UTF_8.name())) {
            Pattern emptyString = Pattern.compile("\\s*");
            Scanner scannerWithDelimeters = scanner.useDelimiter(System.lineSeparator()).skip(emptyString);
            while (scannerWithDelimeters.hasNext()) {
                result.add(scannerWithDelimeters.next());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}