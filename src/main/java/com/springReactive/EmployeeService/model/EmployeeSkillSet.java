package com.springReactive.EmployeeService.model;

import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(value = "emp_skill")
public class EmployeeSkillSet {
    @PrimaryKeyColumn(value = "emp_id", ordinal = 0,type = PrimaryKeyType.PARTITIONED)
    private int empId;
    @PrimaryKeyColumn(value="java_exp",ordinal = 1, type=PrimaryKeyType.CLUSTERED,ordering = Ordering.DESCENDING)
    private double javaExp;
    @PrimaryKeyColumn(value="spring_exp",ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private double springExp;
}
