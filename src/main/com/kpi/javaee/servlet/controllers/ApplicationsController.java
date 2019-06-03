package com.kpi.javaee.servlet.controllers;

import com.kpi.javaee.servlet.common.RestStatus;
import com.kpi.javaee.servlet.entities.ApplicationEntity;
import com.kpi.javaee.servlet.exceptions.EntityNotFoundException;
import com.kpi.javaee.servlet.repos.ApplicationsRepos;
import com.kpi.javaee.servlet.repos.UsersRepos;
import com.kpi.javaee.servlet.services.ApplicationManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/applications")
public class ApplicationsController {

    @Autowired
    private final ApplicationsRepos applicationsRepos;

    @Autowired
    private final ApplicationManagerService applicationManagerService;

    @Autowired
    private final UsersRepos usersRepos;

    public ApplicationsController(ApplicationsRepos applicationsRepos, ApplicationManagerService applicationManagerService, UsersRepos usersRepos) {
        this.applicationsRepos = applicationsRepos;
        this.applicationManagerService = applicationManagerService;
        this.usersRepos = usersRepos;
    }


    @GetMapping("/{id}")
    public ApplicationEntity get(@PathVariable(name = "id") Long id) {
        return applicationsRepos.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping
    public Iterable<ApplicationEntity> getAll(
            @RequestParam(name = "byUserId", required = false) Long userId
    ) {

        if (userId != null) {

            return applicationsRepos.findAllByUsersByIdUser(
                    usersRepos.findById(userId).orElseThrow(EntityNotFoundException::new)
            );
        }

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
