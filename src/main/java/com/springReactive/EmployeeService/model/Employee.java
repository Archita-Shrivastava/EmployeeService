package com.springReactive.EmployeeService.model;

import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value="emp")
public class Employee {
    @PrimaryKeyColumn(value = "emp_id", ordinal = 0,type = PrimaryKeyType.PARTITIONED)
    private int empId;
    @Column(value = "emp_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String empName;
    @Column(value = "emp_city")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String empCity;
    @Column(value = "emp_phone")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String empPhone;
}
