package com.smart_resume_analyzer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.smart_resume_analyzer.model.ResumeAndDescription;

public interface ResumeRepository extends MongoRepository<ResumeAndDescription, String> {

}
