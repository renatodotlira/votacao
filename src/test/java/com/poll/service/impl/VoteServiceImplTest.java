package com.poll.service.impl;

import com.poll.enums.FlagResultEnum;
import com.poll.enums.FlagVoteEnum;
import com.poll.exceptions.BadRequestException;
import com.poll.model.SubjectMatter;
import com.poll.model.Vote;
import com.poll.repository.VoteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VoteServiceImplTest {

    @InjectMocks
    private VoteServiceImpl voteService;

    @Mock
    private SubjectMatterServiceImpl subjectMatterService;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private UserServiceImpl userService;


    public void save_() {
        when(voteRepository.save(any())).thenReturn(vote_dummy());
        when(userService.checkIfIsAbleToVote(any())).thenReturn(true);
        voteService.save(vote_dummy().toDto());
        verify(voteRepository, times(1)).save(any(Vote.class));
    }

    @Test
    public void save_OK() {
        when(subjectMatterService.findById(any())).thenReturn(subjectMatterInVotingTime_dummy());
        save_();
    }

    @Test(expected = BadRequestException.class)
    public void saveNotStartedVoting_Exception() {
        when(subjectMatterService.findById(any())).thenReturn(subjectMatterNotStarted_dummy());
        save_();
    }

    @Test(expected = BadRequestException.class)
    public void saveFinishedVoting_Exception() {
        when(subjectMatterService.findById(any())).thenReturn(subjectMatterFinished_dummy());
        save_();
    }

    @Test
    public void findBySubjectMatterIdAndUserCpf_OK() {
        Optional<Vote> optionalVote = Optional.of(vote_dummy());
        when(voteRepository.findBySubjectMatterIdAndUserCpf(any(), any())).thenReturn(optionalVote);
        assertEquals(voteService.findBySubjectMatterIdAndUserCpf(any(), any()), optionalVote.get().toDto());
    }

    private Vote vote_dummy(){
        Vote vote = new Vote();
        vote.setFlagVote(FlagVoteEnum.YES);
        vote.setSubjectMatterId("test");
        vote.setUserCpf("test");
        return vote;
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

    private SubjectMatter subjectMatterInVotingTime_dummy(){
        SubjectMatter subjectMatter = subjectMatter_dummy();
        subjectMatter.setVotingInit(LocalDateTime.now().minusHours(1));
        subjectMatter.setVotingEnd(LocalDateTime.now().plusHours(1));
        subjectMatter = subjectMatter.updateCounter(FlagVoteEnum.YES);
        subjectMatter.setResult(FlagResultEnum.UNFINISHED);
        return subjectMatter;
    }

    private SubjectMatter subjectMatterNotStarted_dummy(){
        SubjectMatter subjectMatter = subjectMatter_dummy();
        subjectMatter.setVotingInit(LocalDateTime.now().plusHours(1));
        subjectMatter.setVotingEnd(LocalDateTime.now().plusHours(2));
        subjectMatter = subjectMatter.updateCounter(FlagVoteEnum.YES);
        subjectMatter.setResult(FlagResultEnum.UNFINISHED);
        return subjectMatter;
    }


}