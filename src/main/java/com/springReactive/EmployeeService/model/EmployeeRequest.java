package com.springReactive.EmployeeService.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeRequest {
    @NotNull(message = "Employee.id must be present")
    private int empId;
    @NotBlank(message = "Employee.empName must not be null")
    private String empName;
    @NotBlank(message = "Employee.empCity must not be null")
    private String empCity;
    @NotBlank(message = "Employee.empPhone must not be null")
    private String empPhone;
    @NotNull(message = "Employee.javaExp must be present")
    private double javaExp;
    @NotNull(message = "Employee.springExp must be present")
    private double springExp;
}
