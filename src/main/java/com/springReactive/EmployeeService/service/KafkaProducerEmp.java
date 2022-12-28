package com.springReactive.EmployeeService.service;

import com.springReactive.EmployeeService.model.EmployeeRequest;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaProducerEmp {
    private final Logger log = LoggerFactory.getLogger(KafkaProducerEmp.class);

    @Value(value = "${PRODUCER_TOPIC}")
    private String topic;
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public void sendMessages(EmployeeRequest employeeRequest){
        KafkaSender<Integer, EmployeeRequest> sender;

        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "sample-producer");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EmployeeSerDes.class);
        //SenderOptions.create(props);

        SenderOptions<Integer, EmployeeRequest> senderOptions = SenderOptions.create(props);

        sender = KafkaSender.create(senderOptions);

        SenderRecord<Integer,EmployeeRequest, Integer> message = SenderRecord.create(new ProducerRecord<>(topic,employeeRequest.getEmpId(),employeeRequest),employeeRequest.getEmpId());
//
        sender.send(Mono.just(message).log())
                .doOnNext(r -> System.out.printf("Message #%d send response: %s\n", r.correlationMetadata(), r.recordMetadata()))
                .subscribe();
//
    }

}

