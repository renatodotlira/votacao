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

    private int voteYesAmount = 0;

    private int voteNoAmount = 0;

    @JsonIgnore
    private ModelMapper modelMapper = new ModelMapper();

    public SubjectMatter toEntity(){
        return modelMapper.map(this, SubjectMatter.class);
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
        SubjectMatterDto other = (SubjectMatterDto) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }
}
