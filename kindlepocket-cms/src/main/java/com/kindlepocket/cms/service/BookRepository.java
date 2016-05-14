package com.kindlepocket.cms.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.kindlepocket.cms.pojo.Item;

@Component
public interface BookRepository extends MongoRepository<Item, String> {

    Item findByAuthor(String author);

    Item findByTitle(String title);

    /*
     * @Query("{'age':?0") List<Item> withQueryFindByAge(Integer age);
     */

}
