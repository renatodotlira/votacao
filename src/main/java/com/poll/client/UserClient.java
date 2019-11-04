package com.poll.client;

import com.poll.dto.FlagAbleToVoteDto;
import feign.Param;
import feign.RequestLine;

public interface UserClient {

    @RequestLine("GET /{cpf}")
    FlagAbleToVoteDto isAbleToVote(@Param("cpf") Long cpf);

}