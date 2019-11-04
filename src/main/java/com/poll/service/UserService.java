package com.poll.service;

import com.poll.dto.FlagAbleToVoteDto;

public interface UserService {

    FlagAbleToVoteDto checkIfIsAbleToVote(Long cpf);


}
