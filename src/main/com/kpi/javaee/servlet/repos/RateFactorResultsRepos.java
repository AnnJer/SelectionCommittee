package com.kpi.javaee.servlet.repos;

import com.kpi.javaee.servlet.entities.RateFactorResultEntity;
import com.kpi.javaee.servlet.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface RateFactorResultsRepos extends CrudRepository<RateFactorResultEntity, Long> {

    void deleteByUsersByIdUser(UserEntity userEntity);

}
