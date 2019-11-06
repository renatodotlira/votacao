package com.poll.controller;

import com.poll.dto.VoteDto;
import com.poll.service.VoteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vote")
public class VoteController {

    @Autowired
    VoteService service;

    @PostMapping("save/")
    @ApiOperation("Salva um novo registro de voto")
    public VoteDto save(@RequestBody VoteDto voteDto){
        return service.save(voteDto);
    }

}
