package com.kpi.javaee.servlet.services;


import com.kpi.javaee.servlet.entities.RateFactorResultEntity;
import com.kpi.javaee.servlet.entities.UserEntity;
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
    public void insert(List<RateFactorResultEntity> exams, RateFactorResultEntity schoolCertificate) {

        List<RateFactorResultEntity> rateFactorResultsEntities = new ArrayList<>(exams);
        rateFactorResultsEntities.add(schoolCertificate);

        rateFactorResultsRepos.deleteByUsersByIdUser(schoolCertificate.getUsersByIdUser());

        rateFactorResultsRepos.saveAll(rateFactorResultsEntities);
    }

    public RateFactorResultEntity createExam(double result, String examName, UserEntity userEntity) {
        String type = EXAM_PREFIX + TYPE_SEP + examName;
        return new RateFactorResultEntity(result, type, userEntity);
    }

    public RateFactorResultEntity createSchoolCertificate(double result, UserEntity userEntity) {
        String type = SCHOOL_CERTIFICATE_PREFIX;
        return new RateFactorResultEntity(result, type, userEntity);
    }

}
