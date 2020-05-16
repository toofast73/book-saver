package ru.toofast.booksaver.counter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordCountResponse {

    private OperationStatus status;
    private Map<String, Long> wordCount;

}
