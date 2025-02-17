package ru.otus.hw.dao;

import com.opencsv.bean.ColumnPositionMappingStrategyBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.otus.hw.config.QuizFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exception.QuestionExceptions;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    @NonNull
    private final QuizFileNameProvider quizFileNameProvider;

    @SneakyThrows
    @Override
    public List<Question> findAll() {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(quizFileNameProvider.getQuizFileName())) {
            if (inputStream == null) {
                throw new QuestionExceptions("Ресурс не найден или не может быть прочтен");
            }
            MappingStrategy<QuestionDto> strategy = new ColumnPositionMappingStrategyBuilder<QuestionDto>().build();
            strategy.setType(QuestionDto.class);

            List<QuestionDto> questionDtoList = new CsvToBeanBuilder<QuestionDto>(
                    new InputStreamReader(inputStream))
                    .withMappingStrategy(strategy).withSeparator(';').withSkipLines(0).build().parse();

            List<Question> questionList = new ArrayList<>(questionDtoList.size());
            for (QuestionDto questionDto : questionDtoList) {
                if (questionDto != null) {
                    questionList.add(questionDto.toQuestion());
                }
            }
            return questionList;
        } catch (Exception e) {
            throw new QuestionExceptions(String
                    .format("Ошибка чтении списка %s", quizFileNameProvider.getQuizFileName(), e));
        }
    }
}
