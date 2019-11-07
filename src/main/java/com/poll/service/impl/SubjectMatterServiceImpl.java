package com.poll.service.impl;

import com.poll.dto.SubjectMatterDto;
import com.poll.enums.FlagResultEnum;
import com.poll.exceptions.BadRequestException;
import com.poll.exceptions.NotFoundException;
import com.poll.model.SubjectMatter;
import com.poll.repository.SubjectMatterRepository;
import com.poll.service.Producer;
import com.poll.service.SubjectMatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectMatterServiceImpl implements SubjectMatterService {

    @Autowired
    private SubjectMatterRepository repository;

    @Autowired
    private Producer producer;

    @Override
    public SubjectMatter save(SubjectMatter subjectMatter) {
        return repository.save(subjectMatter);
    }

    @Override
    public SubjectMatterDto create(SubjectMatterDto subjectMatterDto) {
        subjectMatterDto.setResult(FlagResultEnum.UNFINISHED);
        subjectMatterDto.setId(null);
        datesProcess(subjectMatterDto);
        return save(subjectMatterDto.toEntity()).toDto();
    }

    @Override
    public List<SubjectMatterDto> listAll() {
        List<SubjectMatterDto> subjectMatterDtos = new ArrayList<>();
        repository.findAll().forEach(subjectMatter -> subjectMatterDtos.add(subjectMatter.toDto()));
        return subjectMatterDtos;
    }

    @Override
    public SubjectMatter findById(String id) {
        Optional<SubjectMatter> subjectMatterOptional = repository.findById(id);
        if(subjectMatterOptional.isPresent())
            return subjectMatterOptional.get();
        else
            throw new NotFoundException("subject.matter.not.found");
    }

    @Override
    public SubjectMatterDto showResult(String id) {
        SubjectMatter subjectMatter = findById(id);
        if(subjectMatter.getResult().equals(FlagResultEnum.UNFINISHED)){
            if (checkIsFinalized(subjectMatter)) {
                subjectMatter.calcAndSetResult();
                save(subjectMatter);
                producer.sendMessage("Subject matter: "+subjectMatter.getName()+", result: "+subjectMatter.getResult());
            }
        }
        return subjectMatter.toDto();
    }

    private boolean checkIsFinalized(SubjectMatter subjectMatter){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = subjectMatter.getVotingEnd();
        return now.isAfter(end);
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
