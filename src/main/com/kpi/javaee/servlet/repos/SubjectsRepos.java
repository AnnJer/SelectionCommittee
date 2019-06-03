package com.kpi.javaee.servlet.repos;

import com.kpi.javaee.servlet.entities.SubjectEntity;
import org.springframework.data.repository.CrudRepository;

public interface SubjectsRepos extends CrudRepository<SubjectEntity, Long> {
}
