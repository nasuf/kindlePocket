package com.kindlepocket.cms.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.kindlepocket.cms.pojo.TextBook;

@Component
public interface BookRepository extends MongoRepository<TextBook, String> {

    TextBook findByAuthor(String author);

    List<TextBook> findByTitleLike(String title);

    TextBook findById(Long id);

    /*
     * @Query("{'age':?0") List<Item> withQueryFindByAge(Integer age);
     */

}
