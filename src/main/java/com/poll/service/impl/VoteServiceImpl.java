package com.poll.service.impl;

import com.poll.dto.VoteDto;
import com.poll.exceptions.BadRequestException;
import com.poll.model.SubjectMatter;
import com.poll.model.Vote;
import com.poll.repository.VoteRepository;
import com.poll.service.SubjectMatterService;
import com.poll.service.UserService;
import com.poll.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository repository;

    @Autowired
    private SubjectMatterService subjectMatterService;

    @Autowired
    private UserService userService;

    @Override
    public VoteDto save(VoteDto voteDto) {
        voteDto.setId(null);
        String cpf = voteDto.getUserCpf();
        voteDto.setUserCpf(cpf.replaceAll("[^\\d.]", ""));
        SubjectMatter subjectMatter = getSubjectMatter(voteDto);

        checkIsVotingTime(subjectMatter);
        checkIsUserAbleToVote(voteDto.getUserCpf());
        checkUserAlreadyVoted(voteDto);

        subjectMatter = subjectMatter.updateCounter(voteDto.getFlagVote());
        subjectMatterService.save(subjectMatter);
        return repository.save(voteDto.toEntity()).toDto();
    }

    @Override
    public VoteDto findBySubjectMatterIdAndUserCpf(String id, String cpf) {
        Optional<Vote> optionalVote = repository.findBySubjectMatterIdAndUserCpf(id, cpf);
        if (optionalVote.isPresent())
            return optionalVote.get().toDto();
        return null;
    }

    private SubjectMatter getSubjectMatter(VoteDto voteDto){
        return subjectMatterService.findById(voteDto.getSubjectMatterId());
    }

    private void checkUserAlreadyVoted(VoteDto voteDto){
        String id = voteDto.getSubjectMatterId();
        String cpf = voteDto.getUserCpf();
        if(findBySubjectMatterIdAndUserCpf(id, cpf) != null)
            throw new BadRequestException("user.already.voted");
    }

    private void checkIsVotingTime(SubjectMatter subjectMatter){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime init = subjectMatter.getVotingInit();
        LocalDateTime end = subjectMatter.getVotingEnd();
        if(now.isBefore(init))
            throw new BadRequestException("voting.not.started");
        if(now.isAfter(end))
            throw new BadRequestException("voting.finished");

    }

    private void checkIsUserAbleToVote(String cpf){
        if(!userService.checkIfIsAbleToVote(cpf))
            throw new BadRequestException("user.unable.to.vote");
    }

}
