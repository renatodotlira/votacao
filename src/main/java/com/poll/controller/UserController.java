package com.poll.controller;

import com.poll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("is-able-to-vote/{cpf}")
    public boolean getAll(@PathVariable("cpf") String cpf){
        return userService.checkIfIsAbleToVote(cpf);
    }

}
