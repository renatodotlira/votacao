package com.poll.model;

import com.poll.dto.SubjectMatterDto;
import com.poll.enums.FlagResultEnum;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "subject_matter")
public class SubjectMatter {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull(message = "name.is.required")
    private String name;

    @NotNull(message = "description.is.required")
    private String description;

    @NotNull(message = "votingInit.is.required")
    private LocalDateTime votingInit;

    @NotNull(message = "votingEnd.is.required")
    private LocalDateTime votingEnd;

    private FlagResultEnum result;

    //vou deixar esses dois contadores estáticos para facilitar a contagem
    //Porém, em um ambiente profissional eu usaria uma outra entidade só para
    //guardar os outros tipos de voto que por ventura pudessem aparecer
    private int voteYesAmount = 0;
    private int voteNoAmount = 0;

    @Transient
    private ModelMapper modelMapper = new ModelMapper();

    public SubjectMatter(){}

    public SubjectMatterDto toDto(){
        return modelMapper.map(this, SubjectMatterDto.class);
    }

}
