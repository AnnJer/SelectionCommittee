package com.kpi.javaee.servlet.services;

import com.kpi.javaee.servlet.entities.*;
import com.kpi.javaee.servlet.exceptions.EntityNotFoundException;
import com.kpi.javaee.servlet.exceptions.IllegalUserInputException;
import com.kpi.javaee.servlet.repos.ApplicationsRepos;
import com.kpi.javaee.servlet.repos.FacultiesRepos;
import com.kpi.javaee.servlet.repos.UsersRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ApplicationManagerService {

    private final UsersRepos usersRepos;
    private final FacultiesRepos facultiesRepos;
    private final ApplicationsRepos applicationsRepos;

    @Autowired
    public ApplicationManagerService(UsersRepos usersRepos, FacultiesRepos facultiesRepos, ApplicationsRepos applicationsRepos) {
        this.usersRepos = usersRepos;
        this.facultiesRepos = facultiesRepos;
        this.applicationsRepos = applicationsRepos;
    }


    public void applyToFaculty(Long idFaculty, Long idStudent) {

        FacultiesEntity facultiesEntity = facultiesRepos.findById(idFaculty).orElseThrow(EntityNotFoundException::new);
        UsersEntity usersEntity = usersRepos.findById(idStudent).orElseThrow(EntityNotFoundException::new);

        checkApplicationInfo(facultiesEntity, usersEntity);

        float rating = calcRating(
                new ArrayList<>(usersEntity.getRateFactorResultsById()),
                new ArrayList<>(facultiesEntity.getSelectionRound().getRateFactorCoefficientsById())
        );

        applicationsRepos.save(new ApplicationsEntity(
            rating, Timestamp.from(Instant.now()), facultiesEntity, usersEntity
        ));
    }


    public void deleteApplication(Long idApplication) {
        ApplicationsEntity applicationsEntity = applicationsRepos.findById(idApplication).orElseThrow(EntityNotFoundException::new);

        if (applicationsEntity.getFacultiesByIdFaculty().getSelectionRound().getEndDate().getTime() < Date.from(Instant.now()).getTime()) {
            throw new IllegalUserInputException("You cant delete application after selection end.");
        }

    }


    private void checkApplicationInfo(FacultiesEntity facultiesEntity, UsersEntity usersEntity) {

        if (facultiesEntity.getSelectionRound() != null) {
            Long count = applicationsRepos.countApplicationsEntitiesByUsersByIdUser(usersEntity);

            if (count > 0) {
                throw new IllegalUserInputException("You already have application. Only 1 allowed.");
            }

            if (facultiesEntity.getSelectionRound().getEndDate().getTime() < Date.from(Instant.now()).getTime()) {
                throw new IllegalUserInputException("You cant apply after selection end.");
            }

            List<RateFactorResultsEntity> results = new ArrayList<>(usersEntity.getRateFactorResultsById());

            for (RateFactorCoefficientsEntity coef:
                    facultiesEntity.getSelectionRound().getRateFactorCoefficientsById()) {
                if (!isHaveSuitable(results, coef)) {
                    throw new IllegalUserInputException("You cant apply this faculty,");
                }
            }


        }

        throw new IllegalUserInputException("This faculty have no enrollment");
    }



    private boolean isHaveSuitable(List<RateFactorResultsEntity> results, RateFactorCoefficientsEntity coefficient) {
        return findSuitableResult(results, coefficient) != null;
    }


    private RateFactorResultsEntity findSuitableResult(List<RateFactorResultsEntity> results, RateFactorCoefficientsEntity coefficient) {
        for (RateFactorResultsEntity result: results
        ) {

            if (result.getType().equals(coefficient.getType())) {
                return result;
            }
        }

        return null;
    }


    private float calcRating(List<RateFactorResultsEntity> results, List<RateFactorCoefficientsEntity> coefficients) {

        float rating = 0;


        for (RateFactorCoefficientsEntity coefficient: coefficients
        ) {

            RateFactorResultsEntity result = findSuitableResult(results, coefficient);
            if (result != null) {
                rating += coefficient.getCoefficient() * result.getResult();
            }

        }

        return rating;
    }


}
