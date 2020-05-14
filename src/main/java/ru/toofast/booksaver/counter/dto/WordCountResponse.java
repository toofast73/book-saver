package ru.toofast.booksaver.counter.dto;

import java.util.Map;

public class WordCountResponse {

    private OperationStatus status;
    private Map<String, Long> wordCount;

    public WordCountResponse() {
    }

    public WordCountResponse(OperationStatus status, Map<String, Long> wordCount) {
        this.status = status;
        this.wordCount = wordCount;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    public Map<String, Long> getWordCount() {
        return wordCount;
    }

    public void setWordCount(Map<String, Long> wordCount) {
        this.wordCount = wordCount;
    }
}
