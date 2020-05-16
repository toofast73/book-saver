package ru.toofast.booksaver.counter;

import org.springframework.stereotype.Component;
import ru.toofast.booksaver.counter.dto.OperationStatus;
import ru.toofast.booksaver.counter.dto.WordCountResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;

@Component
public class WordCounter {

    private WordTokenizer wordTokenizer;

    public WordCounter(WordTokenizer wordTokenizer) {
        this.wordTokenizer = wordTokenizer;
    }

    public WordCountResponse countWords(List<String> lines) {
        Optional<Map<String, Long>> aggregatedResult = lines.stream()
                .map(String::toLowerCase)
                .map(this::countWordsInLine).reduce(new BinaryOperator<Map<String, Long>>() {
                    @Override
                    public Map<String, Long> apply(Map<String, Long> first, Map<String, Long> second) {
                        for (Map.Entry<String, Long> stringLongEntry : second.entrySet()) {
                            first.merge(stringLongEntry.getKey(), stringLongEntry.getValue(), Long::sum);
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

        List<String> strings = wordTokenizer.prepareWords(line);
        return toMap(strings);

    }

    private Map<String, Long> toMap(List<String> strings) {
        Map<String, Long> result = new HashMap<>();
        for (String string : strings) {
            result.merge(string, 1L, Long::sum);
        }

        return result;
    }


}
