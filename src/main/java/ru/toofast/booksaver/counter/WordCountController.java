package ru.toofast.booksaver.counter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.toofast.booksaver.counter.dto.WordCountResponse;

@RestController
public class WordCountController {


    private WordCountService wordCountService;

    public WordCountController(WordCountService wordCountService) {
        this.wordCountService = wordCountService;
    }

    @GetMapping("/wordCount")
    public WordCountResponse countWords(@RequestParam String filename) {

        return wordCountService.countWordsInFile(filename);
    }

}
