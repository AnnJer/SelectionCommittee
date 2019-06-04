package com.kpi.javaee.servlet.controllers;


import com.kpi.javaee.servlet.common.RestStatus;
import com.kpi.javaee.servlet.config.dto.UserDto;
import com.kpi.javaee.servlet.entities.RateFactorResultEntity;
import com.kpi.javaee.servlet.entities.UserEntity;
import com.kpi.javaee.servlet.exceptions.EntityNotFoundException;
import com.kpi.javaee.servlet.repos.RateFactorResultsRepos;
import com.kpi.javaee.servlet.services.RateFactorResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rate_factor_results")
public class RateFactorResultsController {

    private RateFactorResultsRepos rateFactorResultsRepos;

    private RateFactorResultsService rateFactorResultsService;

    @Autowired
    public RateFactorResultsController(RateFactorResultsRepos rateFactorResultsRepos, RateFactorResultsService rateFactorResultsService) {
        this.rateFactorResultsRepos = rateFactorResultsRepos;
        this.rateFactorResultsService = rateFactorResultsService;
    }

    @GetMapping("/{id}")
    public RateFactorResultEntity get(@PathVariable(name = "id") Long id) {
        return rateFactorResultsRepos.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping
    public Iterable<RateFactorResultEntity> getAll() {
        return rateFactorResultsRepos.findAll();
    }


    @PostMapping
    public RestStatus insertAll(
            @RequestParam(name = "exams") String encodedExams,
            @RequestParam(name = "schoolcertificate") Float schoolCertificateResult
    ) {

        UserDto user = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity userEntity = new UserEntity(
                user.getName(), user.getSurname(), user.getLastname(), user.getLogin(), user.getRoles().get(0)
        );

        List<RateFactorResultEntity> examResults = new ArrayList<>();

        for (String encodedPair: encodedExams.split(",")) {

            Double result = Double.parseDouble(encodedExams.split("-")[0]);
            String examName = encodedExams.split("-")[1];

            examResults.add(rateFactorResultsService.createExam(result, examName, userEntity));
        }

        RateFactorResultEntity schoolCertificate = rateFactorResultsService.createSchoolCertificate(
                schoolCertificateResult, userEntity
        );

        rateFactorResultsService.insert(examResults, schoolCertificate);

        return RestStatus.INSERTED;
    }


}
