package com.springReactive.EmployeeService.repository;

import com.springReactive.EmployeeService.model.EmployeeSkillSet;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EmployeeSkillSetRepository extends ReactiveCassandraRepository<EmployeeSkillSet, Integer> {
    @AllowFiltering
    Flux<EmployeeSkillSet> findByJavaExpGreaterThan(double javaExp);
    @AllowFiltering
    Flux<EmployeeSkillSet>  findBySpringExpGreaterThan(double javaExp);
}
