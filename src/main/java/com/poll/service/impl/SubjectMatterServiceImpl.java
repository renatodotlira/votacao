package com.poll.service.impl;

import com.poll.dto.SubjectMatterDto;
import com.poll.enums.FlagResultEnum;
import com.poll.exceptions.BadRequestException;
import com.poll.repository.SubjectMatterRepository;
import com.poll.service.SubjectMatterService;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectMatterServiceImpl implements SubjectMatterService {

    @Autowired
    SubjectMatterRepository repository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public SubjectMatterDto save(SubjectMatterDto subjectMatterDto) {
        return repository.save(subjectMatterDto.toEntity()).toDto();
    }

    @Override
    public SubjectMatterDto create(SubjectMatterDto subjectMatterDto) {
        subjectMatterDto.setResult(FlagResultEnum.UNFINISHED);
        subjectMatterDto.setId(null);
        datesProcess(subjectMatterDto);
        return save(subjectMatterDto);
    }

    @Override
    public List<SubjectMatterDto> listAll() {
        List<SubjectMatterDto> subjectMatterDtos = new ArrayList<>();
        repository.findAll().forEach(student -> subjectMatterDtos.add(student.toDto()));
        return subjectMatterDtos;
    }

    private SubjectMatterDto datesProcess(SubjectMatterDto subjectMatterDto) {
        if(subjectMatterDto.getVotingInit().isEqual(subjectMatterDto.getVotingEnd()))
            setDefaultDates(subjectMatterDto);
        if(subjectMatterDto.getVotingInit().isAfter(subjectMatterDto.getVotingEnd()))
            throw new BadRequestException("Data de inicial não pode ser após a data final");
        return subjectMatterDto;
    }

    private SubjectMatterDto setDefaultDates(SubjectMatterDto subjectMatterDto){
        subjectMatterDto.setVotingEnd(subjectMatterDto.getVotingInit().plusMinutes(1));
        return subjectMatterDto;
    }
}
