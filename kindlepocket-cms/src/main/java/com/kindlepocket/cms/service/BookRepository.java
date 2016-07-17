package com.kindlepocket.cms.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.kindlepocket.cms.pojo.Item;

@Component
public interface BookRepository extends MongoRepository<Item, String> {

    Item findByAuthor(String author);

    List<Item> findByTitleLike(String title);

    Item findById(Long id);

    /*
     * @Query("{'age':?0") List<Item> withQueryFindByAge(Integer age);
     */

}
