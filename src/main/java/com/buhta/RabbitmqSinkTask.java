package com.buhta;

import com.buhta.service.RabbitmqService;
import com.buhta.service.RabbitmqServiceIml;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;


public class RabbitmqSinkTask extends SinkTask {

    private static final Logger log = LoggerFactory.getLogger(RabbitmqServiceIml.class);
    private RabbitmqService rabbitmqService;

    @Override
    public String version() {
        return VersionUtil.getVersion();
    }

    @Override
    public void start(Map<String, String> map) {
        rabbitmqService = new RabbitmqServiceIml(new RabbitmqSinkConnectorConfig(map));
    }

    @Override
    public void put(Collection<SinkRecord> collection) {
        try {
            rabbitmqService.process(collection);
        }catch (Exception e){
            log.error("error when processing record put");
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try{
            rabbitmqService.closeClient();
        }catch (Exception e){
            log.error("could not close rabbitmq client");
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
