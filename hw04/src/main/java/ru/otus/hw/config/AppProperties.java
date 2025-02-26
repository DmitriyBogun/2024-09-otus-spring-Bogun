package ru.otus.hw.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "application")
public class AppProperties implements QuizFileNameProvider, QuizConfig, LocaleConfig {

    private final int numberOfCorrectAnswersToSuccessfullyPass;

    private final Locale locale;

    private final Map<Locale, String> quizFileNameByLocaleTag;

    @Override
    public int getNumberOfCorrectAnswersToSuccessfullyPass() {
        return numberOfCorrectAnswersToSuccessfullyPass;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    public String getQuizFileNameByLocaleTag() {
        return quizFileNameByLocaleTag.get(locale);
    }
}
