package com.kpi.javaee.servlet.controllers;

import com.kpi.javaee.servlet.common.RestStatus;
import com.kpi.javaee.servlet.entities.ApplicationsEntity;
import com.kpi.javaee.servlet.exceptions.EntityNotFoundException;
import com.kpi.javaee.servlet.repos.ApplicationsRepos;
import com.kpi.javaee.servlet.services.ApplicationManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/applications")
public class ApplicationsController {

    private final ApplicationsRepos applicationsRepos;

    private final ApplicationManagerService applicationManagerService;

    @Autowired
    public ApplicationsController(ApplicationsRepos applicationsRepos, ApplicationManagerService applicationManagerService) {
        this.applicationsRepos = applicationsRepos;
        this.applicationManagerService = applicationManagerService;
    }


    @GetMapping("/{id}")
    public ApplicationsEntity get(@PathVariable(name = "id") Long id) {
        return applicationsRepos.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping
    public Iterable<ApplicationsEntity> getAll() {
//        return applicationsRepos.findAll();

        System.out.println(applicationsRepos.findAll());

        return applicationsRepos.findAll();

    }


    @PostMapping
    public RestStatus insert(
            @RequestParam(name = "idUser") Long idUser,
            @RequestParam(name = "idFaculty") Long idFaculty
    ) {
        applicationManagerService.applyToFaculty(idFaculty, idUser);
        return RestStatus.INSERTED;
    }

    @DeleteMapping("/{id}")
    public RestStatus delete(@PathVariable(name = "id") Long id) {
        applicationManagerService.deleteApplication(id);
        return RestStatus.DELETED;
    }

}
