package com.poll.service;

import com.poll.dto.SubjectMatterDto;
import com.poll.exceptions.BadRequestException;
import com.poll.model.SubjectMatter;

import java.util.List;

public interface SubjectMatterService {

    SubjectMatter save(SubjectMatter subjectMatter);

    SubjectMatterDto create(SubjectMatterDto subjectMatterDto) throws BadRequestException;

    List<SubjectMatterDto> listAll();

    SubjectMatter findById(String id);

    SubjectMatterDto showResult(String id);

}
