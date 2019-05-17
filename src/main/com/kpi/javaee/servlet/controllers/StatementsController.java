package com.kpi.javaee.servlet.controllers;


import com.kpi.javaee.servlet.common.RestStatus;
import com.kpi.javaee.servlet.entities.ApplicationsEntity;
import com.kpi.javaee.servlet.entities.FacultiesEntity;
import com.kpi.javaee.servlet.entities.StatementsEntity;
import com.kpi.javaee.servlet.entities.UsersEntity;
import com.kpi.javaee.servlet.exceptions.EntityNotFoundException;
import com.kpi.javaee.servlet.repos.ApplicationsRepos;
import com.kpi.javaee.servlet.repos.StatementsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;

@RestController
@RequestMapping("/statements")
public class StatementsController {

    private final String FILTER_BY_FACULTY = "faculty";

    private final StatementsRepos statementsRepos;
    private final ApplicationsRepos applicationsRepos;


    @Autowired
    public StatementsController(StatementsRepos statementsRepos, ApplicationsRepos applicationsRepos) {
        this.statementsRepos = statementsRepos;
        this.applicationsRepos = applicationsRepos;
    }


    @GetMapping("/{id}")
    public StatementsEntity get(@PathVariable(name = "id") Long id) {
        return statementsRepos.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping
    public Iterable<StatementsEntity> getAll(
            @RequestParam(name = "filterBy", required = false) String filterBy,
            @RequestParam(name = "filterId", required = false) Integer filterId
    ) {

        if (filterBy != null) {

            if (filterId == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missed filterId parameter");
            }

            if (filterBy.equals(FILTER_BY_FACULTY)) {
                FacultiesEntity facultiesEntity = new FacultiesEntity();
                facultiesEntity.setId(filterId);
                return statementsRepos.findAllByFacultiesByIdFaculty(facultiesEntity);
            } else {
                UsersEntity usersEntity = new UsersEntity();
                usersEntity.setId(filterId);
                return statementsRepos.findAllByUsersByIdEnrollee(usersEntity);
            }

        }

        return statementsRepos.findAll();
    }


    @PostMapping
    public RestStatus insertAll(
            @RequestParam(name = "applicationId") Long applicationId
    ) {

        ApplicationsEntity applicationsEntity = applicationsRepos
                .findById(applicationId).orElseThrow(EntityNotFoundException::new);

        statementsRepos.save(new StatementsEntity(
            applicationsEntity.getRating(),
            Timestamp.from(Instant.now()),
            applicationsEntity.getFacultiesByIdFaculty(),
            applicationsEntity.getUsersByIdUser(),
            applicationsEntity.getFacultiesByIdFaculty().getSelectionRound()
        ));

        return RestStatus.INSERTED;
    }

}
