package com.poll.repository;

import com.poll.model.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface VoteRepository extends MongoRepository<Vote, String> {

    @Query("{'subjectMatterId' : ?0 , 'userCpf' : ?1}")
    Optional<Vote> findBySubjectMatterIdAndUserCpf(String id, String cpf);

}
