package com.springReactive.EmployeeService.repository;

import com.springReactive.EmployeeService.model.Employee;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EmployeeRepository extends ReactiveCassandraRepository<Employee,Integer> {
    @AllowFiltering
    Mono<Boolean> existsByEmpId(int empId);
    @AllowFiltering
    Mono<Employee> findByEmpId(int empId);
}
