package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.Book;

import java.util.List;


public interface BookRepository extends MongoRepository<Book, String> {

    @Aggregation(pipeline = { "{ '$group' : { '_id': '$author._id', author: { '$first': '$author' } } }" })
    List<Book> findDistinctAuthors();
}
