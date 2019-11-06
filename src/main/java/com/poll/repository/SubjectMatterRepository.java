package com.poll.repository;

import com.poll.model.SubjectMatter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubjectMatterRepository extends MongoRepository<SubjectMatter, String> {
}
