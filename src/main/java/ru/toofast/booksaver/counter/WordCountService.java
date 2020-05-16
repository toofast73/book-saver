package ru.toofast.booksaver.counter;

import org.springframework.stereotype.Service;
import ru.toofast.booksaver.counter.dto.WordCountResponse;

import java.util.List;

@Service
public class WordCountService {

    private final LineExtractor lineExtractor;
    private WordCounter wordCounter;

    public WordCountService(LineExtractor lineExtractor, WordCounter wordCounter) {
        this.lineExtractor = lineExtractor;
        this.wordCounter = wordCounter;
    }

    public WordCountResponse countWordsInFile(String filename) {
        List<String> lines = lineExtractor.extractLines(filename);
        return wordCounter.countWords(lines);

    }
}