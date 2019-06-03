package com.kpi.javaee.servlet.controllers;


import com.kpi.javaee.servlet.entities.FacultyEntity;
import com.kpi.javaee.servlet.exceptions.EntityNotFoundException;
import com.kpi.javaee.servlet.repos.FacultiesRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/faculties")
public class FacultiesController {

    private FacultiesRepos facultiesRepos;

    @Autowired
    public FacultiesController(FacultiesRepos facultiesRepos) {
        this.facultiesRepos = facultiesRepos;
    }

    @GetMapping("/{id}")
    public FacultyEntity get(@PathVariable(name = "id") Long id) {
        return facultiesRepos.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping
    public Iterable<FacultyEntity> getAll() {
        return facultiesRepos.findAll();
    }

}
