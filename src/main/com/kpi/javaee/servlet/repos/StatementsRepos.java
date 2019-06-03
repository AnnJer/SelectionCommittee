package com.kpi.javaee.servlet.repos;

import com.kpi.javaee.servlet.entities.FacultyEntity;
import com.kpi.javaee.servlet.entities.StatementEntity;
import com.kpi.javaee.servlet.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface StatementsRepos extends CrudRepository<StatementEntity, Long> {

    Iterable<StatementEntity> findAllByFacultiesByIdFaculty(FacultyEntity facultyEntity);
    Iterable<StatementEntity> findAllByUsersByIdEnrollee(UserEntity userEntity);

}
