package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LocalizedIOServiceImpl implements LocalizedIOService {

    private final IOService ioService;

    private final LocalizedMessagesService messageService;

    @Override
    public void printLine(String code) {
        ioService.printLine(code);
    }

    @Override
    public void printFormattedLine(String code, Object... args) {
        ioService.printFormattedLine(code, args);
    }

    @Override
    public String readStringWithPrompt(String code) {
        return ioService.readStringWithPrompt(code);
    }

    @Override
    public int readAnswerVariant(int min, int max, String errorMessage) {
        return ioService.readAnswerVariant(min, max, errorMessage);
    }

    @Override
    public void printLineLocalized(String code) {
        ioService.printLine(messageService.getMessage(code));
    }

    @Override
    public void prinfFormattedLineLocalized(String code, Object... args) {
        ioService.printFormattedLine(messageService.getMessage(code, args));
    }

    @Override
    public String readStringWithPromtLocalized(String promptCode) {
        return ioService.readStringWithPrompt(messageService.getMessage(promptCode));
    }

    @Override
    public int readAnswerVariantLocalized(int min, int max, String errorMessageCode) {
        return ioService.readAnswerVariant(min, max, messageService.getMessage(errorMessageCode));
    }

    @Override
    public String getMessage(String code, Object... args) {
        return messageService.getMessage(code, args);
    }
}
