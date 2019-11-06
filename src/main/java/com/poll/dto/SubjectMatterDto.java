package com.poll.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poll.enums.FlagResultEnum;
import com.poll.model.SubjectMatter;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
public class SubjectMatterDto {

    private String id;

    private String name;

    private String description;

    private LocalDateTime votingInit;

    private LocalDateTime votingEnd;

    private FlagResultEnum result;

    @JsonIgnore
    private ModelMapper modelMapper = new ModelMapper();

    public SubjectMatterDto(){}

    public SubjectMatter toEntity(){
        return modelMapper.map(this, SubjectMatter.class);
    }
}
