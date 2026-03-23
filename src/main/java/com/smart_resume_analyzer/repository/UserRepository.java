package com.smart_resume_analyzer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.smart_resume_analyzer.model.User;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
