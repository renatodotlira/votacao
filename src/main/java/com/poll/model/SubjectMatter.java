package com.poll.model;

import com.poll.dto.SubjectMatterDto;
import com.poll.enums.FlagResultEnum;
import com.poll.enums.FlagVoteEnum;
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
@Document(collection = "subject_matter")
public class SubjectMatter {

    @Id
    @Setter
    private String id = UUID.randomUUID().toString();

    @Setter
    @NotNull(message = "name.is.required")
    private String name;

    @Setter
    @NotNull(message = "description.is.required")
    private String description;

    @Setter
    @NotNull(message = "votingInit.is.required")
    private LocalDateTime votingInit;

    @Setter
    @NotNull(message = "votingEnd.is.required")
    private LocalDateTime votingEnd;

    @Setter
    private FlagResultEnum result;

    //vou deixar esses dois contadores estáticos para facilitar a contagem
    //Porém, em um ambiente profissional eu usaria uma outra entidade só para
    //guardar os outros tipos de voto que por ventura pudessem aparecer
    private int voteYesAmount = 0;
    private int voteNoAmount = 0;

    @Transient
    private ModelMapper modelMapper = new ModelMapper();

    public SubjectMatterDto toDto(){
        return modelMapper.map(this, SubjectMatterDto.class);
    }

    private SubjectMatter plusVoteYesAmount(){
        voteYesAmount++;
        return this;
    }

    private SubjectMatter plusVoteNoAmount(){
        voteNoAmount++;
        return this;
    }

    public SubjectMatter updateCounter(FlagVoteEnum flagVoteEnum){
        if(flagVoteEnum.equals(FlagVoteEnum.YES))
            return plusVoteYesAmount();
        else
            return plusVoteNoAmount();
    }

    public void calcAndSetResult(){
        if(this.voteNoAmount > this.voteYesAmount)
            this.result = FlagResultEnum.NO;
        else
            this.result = FlagResultEnum.YES;
    }

}
