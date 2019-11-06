package com.poll.service.impl;

import com.poll.client.UserClient;
import com.poll.dto.FlagAbleToVoteDto;
import com.poll.exceptions.NotFoundException;
import com.poll.service.UserService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserClient userClient;

    @Override
    public boolean checkIfIsAbleToVote(String cpf) {
        FlagAbleToVoteDto flagAbleToVoteDto = null;
        try{
            flagAbleToVoteDto = userClient.isAbleToVote(cpf);
        }catch (FeignException e){
            throw new NotFoundException("user.not.found");
        }
        return flagAbleToVoteDto.getStatus().equals("ABLE_TO_VOTE");
    }
}
