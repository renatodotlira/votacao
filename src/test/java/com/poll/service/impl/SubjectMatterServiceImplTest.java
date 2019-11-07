package com.poll.service.impl;

import com.poll.dto.SubjectMatterDto;
import com.poll.enums.FlagResultEnum;
import com.poll.enums.FlagVoteEnum;
import com.poll.model.SubjectMatter;
import com.poll.repository.SubjectMatterRepository;
import com.poll.service.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubjectMatterServiceImplTest {

    @InjectMocks
    private SubjectMatterServiceImpl subjectMatterService;

    @Mock
    private SubjectMatterRepository subjectMatterRepository;

    @Mock
    private Producer producer;

    @Test
    public void save_OK() {
        subjectMatterService.save(subjectMatter_dummy());
        verify(subjectMatterRepository, times(1)).save(any(SubjectMatter.class));
    }

    @Test
    public void create_OK() {
        when(subjectMatterRepository.save(any())).thenReturn(subjectMatter_dummy());
        subjectMatterService.create(subjectMatter_dummy().toDto());
        verify(subjectMatterRepository, times(1)).save(any(SubjectMatter.class));
    }

    @Test
    public void listAll_OK() {
        SubjectMatter subjectMatter = subjectMatter_dummy();
        SubjectMatter subjectMatter1 = subjectMatter_dummy();
        SubjectMatter subjectMatter2 = subjectMatter_dummy();
        List<SubjectMatter> subjectMatters = new ArrayList<>();
        subjectMatters.add(subjectMatter);
        subjectMatters.add(subjectMatter1);
        subjectMatters.add(subjectMatter2);
        List<SubjectMatterDto> subjectMatterDtos = new ArrayList<>();
        subjectMatters.forEach(sm -> subjectMatterDtos.add(sm.toDto()));
        when(subjectMatterRepository.findAll()).thenReturn(subjectMatters);
        assertEquals(subjectMatterDtos, subjectMatterService.listAll());
    }

    @Test
    public void findById_OK() {
        Optional<SubjectMatter> subjectMatter = Optional.of(subjectMatter_dummy());
        when(subjectMatterRepository.findById(any())).thenReturn(subjectMatter);
        assertEquals(subjectMatterService.findById(any()), subjectMatter.get());
    }

    @Test
    public void showResult_OK() {
        Optional<SubjectMatter> subjectMatter = Optional.of(subjectMatterFinished_dummy());
        SubjectMatter subjectMatter1 = subjectMatterFinished_dummy();
        subjectMatter1.setResult(FlagResultEnum.YES);
        when(subjectMatterRepository.findById(any())).thenReturn(subjectMatter);
        assertEquals(subjectMatterService.findById(any()), subjectMatter.get());
        assertEquals(subjectMatterService.showResult(any()).getResult().toString(), subjectMatter1.getResult().toString());
    }

    private SubjectMatter subjectMatter_dummy(){
        SubjectMatter subjectMatter = new SubjectMatter();
        subjectMatter.setDescription("TestDescription");
        subjectMatter.setName("TestName");
        subjectMatter.setVotingInit(LocalDateTime.now());
        subjectMatter.setVotingEnd(LocalDateTime.now());
        return subjectMatter;
    }

    private SubjectMatter subjectMatterFinished_dummy(){
        SubjectMatter subjectMatter = subjectMatter_dummy();
        subjectMatter.setVotingInit(LocalDateTime.now().minusHours(2));
        subjectMatter.setVotingEnd(LocalDateTime.now().minusHours(1));
        subjectMatter = subjectMatter.updateCounter(FlagVoteEnum.YES);
        subjectMatter.setResult(FlagResultEnum.UNFINISHED);
        return subjectMatter;
    }

}