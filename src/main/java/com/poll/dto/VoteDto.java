package com.poll.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poll.enums.FlagVoteEnum;
import com.poll.model.Vote;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Setter
public class VoteDto {

    @Id
    private String id = UUID.randomUUID().toString();

    private String subjectMatterId;

    private String userCpf;

    private FlagVoteEnum flagVote;

    @JsonIgnore
    private ModelMapper modelMapper = new ModelMapper();

    public Vote toEntity(){
        return modelMapper.map(this, Vote.class);
    }
}
