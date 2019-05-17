package com.kpi.javaee.servlet.services;


import com.kpi.javaee.servlet.entities.RateFactorResultsEntity;
import com.kpi.javaee.servlet.entities.UsersEntity;
import com.kpi.javaee.servlet.repos.RateFactorResultsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RateFactorResultsService {

    private static final String EXAM_PREFIX = "exam";
    private static final String SCHOOL_CERTIFICATE_PREFIX = "schoolcertificate";
    private static final String TYPE_SEP = ":";


    private final RateFactorResultsRepos rateFactorResultsRepos;

    @Autowired
    public RateFactorResultsService(RateFactorResultsRepos rateFactorResultsRepos) {
        this.rateFactorResultsRepos = rateFactorResultsRepos;
    }

    @Transactional  // must lock table by global lock ? will be fixed latter
    public void insert(List<RateFactorResultsEntity> exams, RateFactorResultsEntity schoolCertificate) {

        List<RateFactorResultsEntity> rateFactorResultsEntities = new ArrayList<>(exams);
        rateFactorResultsEntities.add(schoolCertificate);

        rateFactorResultsRepos.deleteByUsersByIdUser(schoolCertificate.getUsersByIdUser());

        rateFactorResultsRepos.saveAll(rateFactorResultsEntities);
    }

    public RateFactorResultsEntity createExam(double result, String examName, UsersEntity usersEntity) {
        String type = EXAM_PREFIX + TYPE_SEP + examName;
        return new RateFactorResultsEntity(result, type, usersEntity);
    }

    public RateFactorResultsEntity createSchoolCertificate(double result, UsersEntity usersEntity) {
        String type = SCHOOL_CERTIFICATE_PREFIX;
        return new RateFactorResultsEntity(result, type, usersEntity);
    }

}
