package com.kpi.javaee.servlet.repos;

import com.kpi.javaee.servlet.entities.FacultiesEntity;
import com.kpi.javaee.servlet.entities.StatementsEntity;
import com.kpi.javaee.servlet.entities.UsersEntity;
import org.springframework.data.repository.CrudRepository;

public interface StatementsRepos extends CrudRepository<StatementsEntity, Long> {

    Iterable<StatementsEntity> findAllByFacultiesByIdFaculty(FacultiesEntity facultiesEntity);
    Iterable<StatementsEntity> findAllByUsersByIdEnrollee(UsersEntity usersEntity);

}
