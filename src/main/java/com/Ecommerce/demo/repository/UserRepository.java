package com.Ecommerce.demo.repository;

import com.Ecommerce.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
