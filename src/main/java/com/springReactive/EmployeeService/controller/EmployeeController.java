package com.springReactive.EmployeeService.controller;

import com.springReactive.EmployeeService.model.EmpExperience;
import com.springReactive.EmployeeService.model.EmployeeRequest;
import com.springReactive.EmployeeService.model.EmployeeResponse;
import com.springReactive.EmployeeService.service.EmployeeServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeServices employeeServices;

    @PostMapping("/createEmployee")
    public Mono<EmployeeResponse> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest){

        return employeeServices.createEmployee(employeeRequest);
    }

    @GetMapping("/findEmpSkillSet")
    public Flux<EmployeeRequest> findGreaterThanExp(@RequestBody EmpExperience empExp){
        return employeeServices.findGreaterThanExp(empExp);
    }
}
