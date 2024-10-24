package edu.mrp.lab6.repository;
/*
@author   Makohon Roman
@project   Architecture testing
@class  $444A
@version  1.0.0
@since 9.10.2024 - 14.23
*/

import edu.mrp.lab6.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
}
