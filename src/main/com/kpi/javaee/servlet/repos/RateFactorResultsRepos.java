package com.kpi.javaee.servlet.repos;

import com.kpi.javaee.servlet.entities.RateFactorResultsEntity;
import com.kpi.javaee.servlet.entities.UsersEntity;
import org.springframework.data.repository.CrudRepository;

public interface RateFactorResultsRepos extends CrudRepository<RateFactorResultsEntity, Long> {

    void deleteByUsersByIdUser(UsersEntity usersEntity);

}
