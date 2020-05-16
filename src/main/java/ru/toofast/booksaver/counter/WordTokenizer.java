package ru.toofast.booksaver.counter;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Component
public class WordTokenizer {

    public List<String> prepareWords(String line) {
        List<String> words = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(line, ";,.?\"\t\n\r\f- !':()[]/");
        while (stringTokenizer.hasMoreTokens()) {
            words.add(stringTokenizer.nextToken());
        }
        return words;
    }
}
