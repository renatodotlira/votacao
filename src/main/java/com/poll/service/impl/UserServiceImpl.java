package com.poll.service.impl;

import com.poll.client.UserClient;
import com.poll.dto.FlagAbleToVoteDto;
import com.poll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserClient userClient;

    @Override
    public FlagAbleToVoteDto checkIfIsAbleToVote(Long cpf) {
        return userClient.isAbleToVote(cpf);
    }
}
