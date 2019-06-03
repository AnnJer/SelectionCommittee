package com.kpi.javaee.servlet.controllers;

import com.kpi.javaee.servlet.entities.SubjectEntity;
import com.kpi.javaee.servlet.repos.SubjectsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subjects")
public class SubjectsController {

    private final SubjectsRepos subjectsRepos;

    @Autowired
    public SubjectsController(SubjectsRepos subjectsRepos) {
        this.subjectsRepos = subjectsRepos;
    }

    @GetMapping
    public Iterable<SubjectEntity> getAll() {
        return subjectsRepos.findAll();
    }

}
