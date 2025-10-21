# rabbitmq-demo

### This project shows a demo that uses RabbitMQ with three exchanges types between an Upstream service (producer) and a Downstream service (consumer).

## Use Case
1. **Direct exchange** — “OrderCommand” (point-to-point / exact routing)
   * Upstream sends order.create commands to a specific worker queue.
   * Use case: strict command to a single consumer type (idempotent worker).


2. **Topic exchange** — “OrderEvents” (selective subscribers via patterns)
    * Upstream emits domain events like order.created, order.paid, order.shipped.
    * Different downstream handlers subscribe using patterns (e.g., order.*).
    * Use case: loosely coupled event subscribers (e.g., billing, analytics).


3. **Fanout exchange** — “BroadcastAlerts” (broadcast to all)
    * Upstream broadcasts an operational alert to all interested parties.
    * Use case: system-wide notifications (dashboard, Slack bridge, audit).


## Project Layout
```
rabbitmq-demo/
├─ docker-compose.yml
├─ upstream-producer/
│  ├─ pom.xml
│  └─ src/main/java/com/example/upstream/...
│     ├─ UpstreamApplication.java
│     ├─ config/RabbitConfig.java
│     ├─ controller/SendController.java
│     ├─ model/OrderCommand.java
│     ├─ model/OrderEvent.java
│     └─ model/SystemAlert.java
└─ downstream-consumer/
   ├─ pom.xml
   └─ src/main/java/com/example/downstream/...
      ├─ DownstreamApplication.java
      ├─ config/RabbitConfig.java
      ├─ listener/DirectOrderListener.java
      ├─ listener/TopicOrderListener.java
      └─ listener/FanoutAlertListener.java
```


## Get Started
1. Start RabbitMQ
```bash
docker compose up -d
```

2. Start downstream-consumer first (so queues/bindings are declared): 
```bash
cd downstream-consumer
mvn spring-boot:run
```

3. Start upstream-producer:
```bash
cd ../upstream-producer
mvn spring-boot:run
```

4. Send test requests:
   