package com.poll.controller;

import com.poll.dto.SubjectMatterDto;
import com.poll.service.SubjectMatterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("subject-matter")
public class SubjectMatterController {

    @Autowired
    SubjectMatterService service;

    @PostMapping("save/")
    @ApiOperation("Salva uma nova pauta")
    public SubjectMatterDto save(@RequestBody SubjectMatterDto customerDto){
        return service.create(customerDto);
    }

    @GetMapping("getAll/")
    @ApiOperation("Lista todas as pautas")
    public List<SubjectMatterDto> getAll() {
        return service.listAll();
    }

}
