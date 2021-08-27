package com.buhta.service;

import com.buhta.RabbitmqSinkConnectorConfig;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.json.JsonConverter;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.kafka.connect.sink.SinkRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Collection;
import java.util.Collections;

public class RabbitmqServiceIml implements RabbitmqService {

    private static final Logger log = LoggerFactory.getLogger(RabbitmqServiceIml.class);
    private Connection conn;
    private Channel channel;
    private String exchangeName;
    private String routingKey;
    private JsonConverter converter;

    public RabbitmqServiceIml(RabbitmqSinkConnectorConfig config) {
        try {
            prepareConverter();
            ConnectionFactory factory = new ConnectionFactory();
            log.error(config.getRabbitmqUrl());
            factory.setUri(config.getRabbitmqUrl());
            conn = factory.newConnection();
            channel = conn.createChannel();

            exchangeName = config.getRabbitmqExchange();
            routingKey = config.getRabbitmqRoutingKey();
            String queueName = config.getRabbitmqQueue();
            String exchangeType = "direct";

            channel.exchangeDeclare(exchangeName, exchangeType, true);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, routingKey);
        } catch (Exception e) {
            log.error("failed to create rabbitmq service instance");
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void prepareConverter() {
        converter = new JsonConverter();
        converter.configure(Collections.singletonMap("schemas.enable", false),false);
        log.info("json converter prepared");
    }

    @Override
    public void process(Collection<SinkRecord> records) {
        //*
        // TODO
        // Here we will to lose records if there is error
        // We need to research some fallback mechanism with offsets to make sure
        // that every record committed
        //
        // */

        log.info("started processing records");
        records.forEach(r -> {
            try {
                if(r.topic() == null || r.valueSchema() == null || r.value() == null){
                    log.error("something is wrong with the record", r.value());
                    throw new RuntimeException("invalid record");
                }
                if(filter(r.value())){
                    log.info("record was filtered as it is not create", r.value());
                    return;
                }
                byte[] data = converter.fromConnectData(r.topic(), r.valueSchema(), r.value());
                channel.basicPublish(exchangeName, routingKey, null, data);
            } catch (Exception e) {
                log.error("something went wrong when processing record");
                log.error(e.getMessage());
                e.printStackTrace();
            }
            log.info("finished processing records");
        });
    }

    private boolean filter(Object record){
        Struct structRecord = (Struct)record;
        String opType = (String) structRecord.get("op");
        return !opType.equals("c");
    }

    @Override
    public void closeClient() throws Exception {
        channel.close();
        conn.close();
    }
}
