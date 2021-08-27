package com.buhta.service;

import org.apache.kafka.connect.sink.SinkRecord;

import java.util.Collection;

public interface RabbitmqService {
    void process(Collection<SinkRecord> records);
    void closeClient() throws Exception;
}
