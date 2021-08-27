# rabbitmq-sink-connector
initial realization of sink connector


## Example config
```json
{
  "name": "rabbitmq-connector",
  "config": {
    "connector.class": "com.buhta.RabbitmqSinkConnector",
		"topics": "<kafka-topic>",
		"rabbitmq.url": "amqp://guest:guest@rabbitmq:5672",
		"rabbitmq.routing_key":"<rabbitkey>",
	  "rabbitmq.queue": "<rabbitqueue>",
		"rabbitmq.exchange": "<rabbitexchage>"
	}
}

```
