package com.springReactive.EmployeeService.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class EmployeeRequestSerializer implements Serializer<EmployeeRequest> {
    public static final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Override
    public byte[] serialize(String topic, EmployeeRequest employeeRequest) {
        try {
            return mapper.writeValueAsBytes(employeeRequest);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }
}
