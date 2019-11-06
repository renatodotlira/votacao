package com.poll.model;

import com.poll.dto.VoteDto;
import com.poll.enums.FlagVoteEnum;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "vote")
public class Vote {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull(message = "subjectMatterId.is.required")
    private String subjectMatterId;

    @NotNull(message = "userCpf.is.required")
    private String userCpf;

    @NotNull(message = "flagVote.is.required")
    private FlagVoteEnum flagVote;

    @Transient
    private ModelMapper modelMapper = new ModelMapper();

    public VoteDto toDto(){
        return modelMapper.map(this, VoteDto.class);
    }
}
