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

        FacultyEntity facultyEntity = facultiesRepos.findById(idFaculty).orElseThrow(EntityNotFoundException::new);
        UserEntity userEntity = usersRepos.findById(idStudent).orElseThrow(EntityNotFoundException::new);

        checkApplicationInfo(facultyEntity, userEntity);

        float rating = calcRating(
                new ArrayList<>(userEntity.getRateFactorResultsById()),
                new ArrayList<>(facultyEntity.getSelectionRound().getRateFactorCoefficientsById())
        );

        applicationsRepos.save(new ApplicationEntity(
            rating, Timestamp.from(Instant.now()), facultyEntity, userEntity
        ));
    }


    public void deleteApplication(Long idApplication) {
        ApplicationEntity applicationEntity = applicationsRepos.findById(idApplication).orElseThrow(EntityNotFoundException::new);

        if (applicationEntity.getFacultiesByIdFaculty().getSelectionRound().getEndDate().getTime() < Date.from(Instant.now()).getTime()) {
            throw new IllegalUserInputException("You cant delete application after selection end.");
        }

    }


    private void checkApplicationInfo(FacultyEntity facultyEntity, UserEntity userEntity) {

        if (facultyEntity.getSelectionRound() != null) {
            Long count = applicationsRepos.countApplicationsEntitiesByUsersByIdUser(userEntity);

            if (count > 0) {
                throw new IllegalUserInputException("You already have application. Only 1 allowed.");
            }

            if (facultyEntity.getSelectionRound().getEndDate().getTime() < Date.from(Instant.now()).getTime()) {
                throw new IllegalUserInputException("You cant apply after selection end.");
            }

            List<RateFactorResultEntity> results = new ArrayList<>(userEntity.getRateFactorResultsById());

            for (RateFactorCoefficientEntity coef:
                    facultyEntity.getSelectionRound().getRateFactorCoefficientsById()) {
                if (!isHaveSuitable(results, coef)) {
                    throw new IllegalUserInputException("You cant apply this faculty,");
                }
            }


        }

        throw new IllegalUserInputException("This faculty have no enrollment");
    }



    private boolean isHaveSuitable(List<RateFactorResultEntity> results, RateFactorCoefficientEntity coefficient) {
        return findSuitableResult(results, coefficient) != null;
    }


    private RateFactorResultEntity findSuitableResult(List<RateFactorResultEntity> results, RateFactorCoefficientEntity coefficient) {
        for (RateFactorResultEntity result: results
        ) {

            if (result.getType().equals(coefficient.getType())) {
                return result;
            }
        }

        return null;
    }


    private float calcRating(List<RateFactorResultEntity> results, List<RateFactorCoefficientEntity> coefficients) {

        float rating = 0;


        for (RateFactorCoefficientEntity coefficient: coefficients
        ) {

            RateFactorResultEntity result = findSuitableResult(results, coefficient);
            if (result != null) {
                rating += coefficient.getCoefficient() * result.getResult();
            }

        }

        return rating;
    }


}
