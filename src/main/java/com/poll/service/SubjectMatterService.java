package com.poll.service;

import com.poll.dto.SubjectMatterDto;
import com.poll.exceptions.BadRequestException;

import java.util.List;

public interface SubjectMatterService {

    SubjectMatterDto save(SubjectMatterDto subjectMatterDto);

    SubjectMatterDto create(SubjectMatterDto subjectMatterDto) throws BadRequestException;

    List<SubjectMatterDto> listAll();

}
