package com.poll.service;

import com.poll.dto.VoteDto;

public interface VoteService {

    VoteDto save(VoteDto voteDto);

    VoteDto findBySubjectMatterIdAndUserCpf(String id, String cpf);

}
