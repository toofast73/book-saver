package ru.toofast.booksaver.storage;

public class StorageFileNotFoundException extends RuntimeException {
    String filename;

    public StorageFileNotFoundException(String filename) {
        this.filename = filename;
    }
}
