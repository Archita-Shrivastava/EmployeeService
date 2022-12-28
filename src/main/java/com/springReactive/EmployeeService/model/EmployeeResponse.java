package com.springReactive.EmployeeService.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeResponse {
    private int empId;
    private String empName;
    private String empCity;
    private String empPhone;
    private double javaExp;
    private double springExp;
    private String status;
}
