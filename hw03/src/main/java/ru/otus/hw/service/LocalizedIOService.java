package ru.otus.hw.service;

public interface LocalizedIOService extends LocalizedMessagesService, IOService {
    void printLineLocalized(String code);

    void prinfFormattedLineLocalized(String code, Object... args);

    String readStringWithPromtLocalized(String promptCode);

    int readAnswerVariantLocalized(int min, int max, String errorMessageCode);
}
