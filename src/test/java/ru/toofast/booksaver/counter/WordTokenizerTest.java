package ru.toofast.booksaver.counter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

class WordTokenizerTest {
    WordTokenizer wordTokenizer = new WordTokenizer();

    @Test
    public void test() {

        List<String> actual = wordTokenizer.prepareWords("Leto the Second -- also called as a 'Tyrant', was a nice person!");
        Assertions.assertThat(actual).containsExactlyInAnyOrder(
                "Leto", "the", "Second", "also", "called", "as", "a", "Tyrant", "was", "a", "nice", "person"
        ).hasSize(12);
    }

    @Test
    public void testOnBook() throws IOException {
        String result = new String(new ClassPathResource("static/dune.txt").getInputStream().readAllBytes());

        List<String> actual = wordTokenizer.prepareWords(result);
        Assertions.assertThat(actual).allMatch(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                for (char c : s.toCharArray()) {
                    if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
                        return false;
                    }
                }
                return true;
            }
        });

    }

}