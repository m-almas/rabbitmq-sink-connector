package com.buhta;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config.ConfigDef.Importance;

import java.util.Map;

public class RabbitmqSinkConnectorConfig extends AbstractConfig {

    public static final String RABBITMQ_URL = "rabbitmq.url";
    private static final String RABBITMQ_URL_DOC = "Rabbitmq URL to connect.";
    private static final String RABBITMQ_EXCHANGE = "rabbitmq.exchange";
    private static final String RABBITMQ_EXCHANGE_DOC = "exchange to publish messages";
    private static  final String RABBITMQ_QUEUE = "rabbitmq.queue";
    private static final String RABBITMQ_QUEUE_DOC = "queue to publish messages";
    private static final String RABBITMQ_ROUTING_KEY = "rabbitmq.routing_key";
    private static final String RABBITMQ_ROUTING_KEY_DOC = "rabbitmq.routing_key_doc";

    public RabbitmqSinkConnectorConfig(ConfigDef config, Map<String, String> parsedConfig){
        super(config, parsedConfig);
    }

    public RabbitmqSinkConnectorConfig( Map<String, String> parsedConfig){
        this(conf(), parsedConfig);
    }

    public static ConfigDef conf(){
        return new ConfigDef()
                .define(RABBITMQ_URL, Type.STRING, Importance.HIGH, RABBITMQ_URL_DOC)
                .define(RABBITMQ_EXCHANGE, Type.STRING, Importance.HIGH, RABBITMQ_EXCHANGE_DOC)
                .define(RABBITMQ_ROUTING_KEY, Type.STRING, Importance.HIGH, RABBITMQ_ROUTING_KEY_DOC)
                .define(RABBITMQ_QUEUE, Type.STRING, Importance.HIGH, RABBITMQ_QUEUE_DOC);
    }

    public String getRabbitmqUrl() {
        return this.getString(RABBITMQ_URL);
    }

    public String getRabbitmqQueue() {
        return this.getString(RABBITMQ_QUEUE);
    }

    public String getRabbitmqRoutingKey() {
        return this.getString(RABBITMQ_ROUTING_KEY);
    }

    public String getRabbitmqExchange() {
        return this.getString(RABBITMQ_EXCHANGE);
    }
}
