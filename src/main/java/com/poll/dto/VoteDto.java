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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VoteDto other = (VoteDto) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

}
