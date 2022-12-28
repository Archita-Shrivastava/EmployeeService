package com.springReactive.EmployeeService.service;

import com.springReactive.EmployeeService.constants.KafkaConstants;
import com.springReactive.EmployeeService.model.EmpExperience;
import com.springReactive.EmployeeService.model.Employee;
import com.springReactive.EmployeeService.model.EmployeeRequest;
import com.springReactive.EmployeeService.model.EmployeeResponse;
import com.springReactive.EmployeeService.model.EmployeeSkillSet;
import com.springReactive.EmployeeService.repository.EmployeeRepository;
import com.springReactive.EmployeeService.repository.EmployeeSkillSetRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServices {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Value(value = "${PRODUCER_TOPIC}")
    private String topic;

    @Autowired
    private KafkaProducerEmp kafkaProducerEmp;
    @Autowired
    private EmployeeSkillSetRepository employeeSkillSetRepository;

    @Autowired
    private KafkaTemplate kafkaTemplate;
    Logger log = LoggerFactory.getLogger(EmployeeServices.class);


    public Mono<EmployeeResponse> createEmployee(EmployeeRequest employeeRequest) {
        log.debug("inside create Employee" + employeeRequest.getEmpId());

        String status = "";

        kafkaProducerEmp.sendMessages(employeeRequest);

        Employee employee = new Employee(employeeRequest.getEmpId(), employeeRequest.getEmpName(),
                employeeRequest.getEmpCity(), employeeRequest.getEmpPhone());
        EmployeeSkillSet employeeSkillSet = new EmployeeSkillSet(employeeRequest.getEmpId(), employeeRequest.getJavaExp(),
                employeeRequest.getSpringExp());

        return employeeRepository.existsByEmpId(employeeRequest.getEmpId())
                .flatMap(emp_exists -> {
                    if (emp_exists) {
                        log.debug("Employee already exists");
                        return Mono.zip(
                                Mono.just(employee), Mono.just(employeeSkillSet)
                        ).map(t -> new EmployeeResponse(t.getT1().getEmpId(),
                                t.getT1().getEmpName(), t.getT1().getEmpCity(), t.getT1().getEmpPhone(),
                                t.getT2().getJavaExp(), t.getT2().getSpringExp(), "Already Exists"));
                    } else {
                        log.debug("Employee Create new");
                        return Mono.zip(employeeRepository.save(employee).log("Employee object"), employeeSkillSetRepository.save(employeeSkillSet).log("Employee skill set object"))
                                .map(t -> {
                                            return new EmployeeResponse(t.getT1().getEmpId(),
                                                    t.getT1().getEmpName(), t.getT1().getEmpCity(), t.getT1().getEmpPhone(),
                                                    t.getT2().getJavaExp(), t.getT2().getSpringExp(), "Created");
                                        }
                                );
                    }
                });

    }

    public Flux<EmployeeRequest> findGreaterThanExp(EmpExperience empExp) {
        Flux<EmployeeSkillSet> empSkillFlux = null;
        if (empExp.getSpringExp() == 0.0d) {
            empSkillFlux = employeeSkillSetRepository.findByJavaExpGreaterThan(empExp.getJavaExp());
        } else if (empExp.getJavaExp() == 0.0d) {
            empSkillFlux = employeeSkillSetRepository.findBySpringExpGreaterThan(empExp.getSpringExp());
        } else if (empExp.getJavaExp() != 0.0 && empExp.getSpringExp() != 0.0) {
            empSkillFlux = employeeSkillSetRepository.findByJavaExpGreaterThan(empExp.getJavaExp()).
                    filter(empSkill -> empSkill.getSpringExp() > empExp.getSpringExp());

        }
        Flux<Employee> employeeFlux = empSkillFlux.concatMap((empSkill -> {
            return this.employeeRepository.findByEmpId(empSkill.getEmpId());
        }));
        return Flux.zip(employeeFlux, empSkillFlux)
                .map(emp -> new EmployeeRequest(emp.getT1().getEmpId(), emp.getT1().getEmpName(),
                        emp.getT1().getEmpCity(), emp.getT1().getEmpPhone(), emp.getT2().getJavaExp(),

                        emp.getT2().getSpringExp()));
    }

//    Flux<EmployeeSkillSet> skillSetFlux = employeeSkillSetRepository.findByJavaExpGreaterThan(javaExp);
//    Flux<Employee> employeeFlux = skillSetFlux.concatMap(emp-> { return employeeRepository.findByEmpId(emp.getEmpId());});
//    Flux<EmployeeRequest> employeeRequestFlux = Flux.zip(employeeFlux,skillSetFlux).map(t-> new EmployeeRequest(t.getT1().getEmpId(),
//            t.getT1().getEmpName(),t.getT1().getEmpCity(),
//            t.getT1().getEmpPhone(),t.getT2().getJavaExp(),
//            t.getT2().getSpringExp()));
//
//        return employeeRequestFlux;
//}
}

