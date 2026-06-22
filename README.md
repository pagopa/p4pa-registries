# p4pa-registries

This application belong to the **entity** tier of the **Piattaforma Unitaria** product.

See [PU Microservice Architecture](https://raw.githubusercontent.com/pagopa/p4pa-doc/refs/heads/main/reference/technical-docs/Architettura_microservizi.pdf) for more details.

See [p4pa-doc](https://github.com/pagopa/p4pa-doc) for further documentation.

## 🧱 Role

* To handle the timeline of debt positions and installments events;
* To handle the registry of interactions between PU and:
  * pagoPa's payment services;
  * Organizations' SILs.
* To handle the timeline of SEND notification events.

## 🌐 APIs
See [OpenAPI](openapi/generated.openapi.json), exposed through the following path:
* `/swagger-ui/index.html`

### 📌 Common HTTP status returned:
* `200`: Successful operation;
* `401`: Invalid access token provided, thus a new login is required;
* `403`: Trying to access a not authorized resource.

## 🌐 AsyncAPIs
See [AsyncAPI](asyncapi/generated.asyncapi.json), exposed through the following path:
* `/springwolf/asyncapi-ui.html`

## 🔎 Monitoring
See available actuator endpoints through the following path:
* `/actuator`

### 📌 Relevant endpoints
* Health (provide an accessToken to see details): `/actuator/health`
  * Liveness: `/actuator/health/liveness`
  * Readiness: `/actuator/health/readiness`
* Metrics: `/actuator/metrics`
  * Prometheus: `/actuator/prometheus`

Further endpoints are exposed through the JMX console.

## ✏️ Logging
See [log configured pattern](/src/main/resources/logback-spring.xml).

## 🔗 Dependencies

### 🗄️ Resources
* MongoDB
* Kafka

## 🗃️ Entities handled
* `debt_position_registry`
* `installment_registry`
* `pagopa_registry`
* `sil_registry`
* `send_timeline_event`

## 🔧 Configuration

See [application.yml](src/main/resources/application.yml) for each configurable property.

### 📌 Relevant configurations

#### 🌐 Application Server
| ENV         | DESCRIPTION                       | DEFAULT |
|-------------|-----------------------------------|---------|
| SERVER_PORT | Application server listening port | 8080    |

#### ✏️ Logging
| ENV                                      | DESCRIPTION                                                                                                                                               | DEFAULT |
|------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|---------|
| LOG_LEVEL_ROOT                           | Base level                                                                                                                                                | INFO    |
| LOG_LEVEL_PAGOPA                         | Base level of custom classes                                                                                                                              | INFO    |
| LOG_LEVEL_SPRING                         | Level applied to Spring framework                                                                                                                         | INFO    |
| LOG_LEVEL_SPRING_BOOT_AVAILABILITY       | To print availability events                                                                                                                              | DEBUG   |
| LOGGING_LEVEL_API_REQUEST_EXCEPTION      | Level applied to APIs exception                                                                                                                           | INFO    |
| LOG_LEVEL_PERFORMANCE_LOG                | Level applied to [PerformanceLog](https://raw.githubusercontent.com/pagopa/p4pa-doc/refs/heads/main/reference/technical-docs/Logging.pdf)                 | INFO    |
| LOG_LEVEL_PERFORMANCE_LOG_API_REQUEST    | Level applied to [API Performance Log](https://raw.githubusercontent.com/pagopa/p4pa-doc/refs/heads/main/reference/technical-docs/Logging.pdf)            | INFO    |
| LOG_LEVEL_PERFORMANCE_LOG_REST_INVOKE    | Level applied to [REST invoke Performance Log](https://raw.githubusercontent.com/pagopa/p4pa-doc/refs/heads/main/reference/technical-docs/Logging.pdf)    | INFO    |
| LOG_LEVEL_PERFORMANCE_LOG_INCOMING_EVENT | Level applied to [Incoming event Performance Log](https://raw.githubusercontent.com/pagopa/p4pa-doc/refs/heads/main/reference/technical-docs/Logging.pdf) | INFO    |

#### 🔁 Integrations

##### 🗄️ Resources
| ENV                                           | DESCRIPTION                             | DEFAULT                   |
|-----------------------------------------------|-----------------------------------------|---------------------------|
| MONGODB_URI                                   | Mongo connection string                 | mongodb://localhost:27017 |
| MONGODB_DBNAME                                | Mongo db name                           | payhub                    |
| MONGODB_CONNECTIONPOOL_MAX_SIZE               | Mongo connection pool max size          | 100                       |
| MONGODB_CONNECTIONPOOL_MIN_SIZE               | Mongo connection pool max size          | 0                         |
| MONGODB_CONNECTIONPOOL_MAX_WAIT_MS            | Timeout milliseconds                    | 120000                    |
| MONGODB_CONNECTIONPOOL_MAX_CONNECTION_LIFE_MS | Connection lifetime (milliseconds)      | 0                         |
| MONGODB_CONNECTIONPOOL_MAX_CONNECTION_IDLE_MS | Connection idle lifetime (milliseconds) | 120000                    |
| MONGODB_CONNECTIONPOOL_MAX_CONNECTING         | Max parallel creating connections       | 2                         |

##### 🌀 KAFKA
| ENV                                       | DESCRIPTION                                                        | DEFAULT                |
|-------------------------------------------|--------------------------------------------------------------------|------------------------|
| KAFKA_BINDER_BROKER                       | Comma separated list of brokers to which the Kafka binder connects |                        |
| KAFKA_PAYMENTS_BINDER_BROKER              | Comma separated list of brokers to which the Kafka binder connects | ${KAFKA_BINDER_BROKER} |
| KAFKA_REGISTRIES_BINDER_BROKER            | Comma separated list of brokers to which the Kafka binder connects | ${KAFKA_BINDER_BROKER} |
| KAFKA_CONFIG_HEARTBEAT_INTERVAL_MS        | Hearth beat interval (milliseconds)                                | 3000                   |
| KAFKA_CONFIG_SESSION_TIMEOUT_MS           | Session timeout (milliseconds)                                     | 30000                  |
| KAFKA_CONFIG_REQUEST_TIMEOUT_MS           | Request timeout (milliseconds)                                     | 60000                  |
| KAFKA_CONFIG_METADATA_MAX_AGE             | Metadata max age (milliseconds)                                    | 180000                 |
| KAFKA_CONFIG_SASL_MECHANISM               | SASL mechanism                                                     | PLAIN                  |
| KAFKA_CONFIG_SECURITY_PROTOCOL            | Security protocol                                                  | SASL_SSL               |
| KAFKA_CONFIG_MAX_REQUEST_SIZE             | Max request size                                                   | 1000000                |

###### 📥 KAFKA CONSUMERS
| ENV                                                | DESCRIPTION                                                                                    | DEFAULT                                            |
|----------------------------------------------------|------------------------------------------------------------------------------------------------|----------------------------------------------------|
| KAFKA_CONSUMER_CONFIG_AUTO_COMMIT                  | True if the acknowledgement of the message is implicit if there are not errors                 | true                                               |
| KAFKA_CONSUMER_CONFIG_CONNECTIONS_MAX_IDLE_MS      | Maximum lifetime for idle connections (milliseconds)                                           | 180000                                             |
| KAFKA_CONFIG_MAX_POLL_INTERVAL_TIMEOUT_MS          | Maximum interval between polls declared toward the broker (milliseconds)                       | 300000                                             |
| KAFKA_CONSUMER_CONFIG_MAX_POLL_SIZE                | Maximum number of messages fetch for each poll                                                 | 500                                                |
| KAFKA_CONSUMER_CONFIG_CONNECTION_TIMEOUT_MS        | Initial timeout configured for the connection process (milliseconds)                           | 100000                                             |
| KAFKA_CONSUMER_CONFIG_CONNECTION_TIMEOUT_MAX_MS    | Maximum timeout configured when connection attempts repeatedly fail (milliseconds)             | 200000                                             |
| KAFKA_CONSUMER_CONFIG_STANDARD_HEADERS             | If ask for contextual metadata headers when reading messages                                   | both                                               |
| KAFKA_CONSUMER_CONFIG_START_OFFSET                 | Where the consumer should begins consuming messages from a topic's partition (earliest/latest) | earliest                                           |
| KAFKA_TOPIC_PAYMENTS                               | Topic where to publish payment event                                                           | p4pa-payhub-payments-evh                           |
| KAFKA_PAYMENTS_PRODUCER_SASL_JAAS_CONFIG           | JAAS Config string used to perform authentication                                              |                                                    |
| KAFKA_PAYMENTS_REGISTRIES_GROUP_ID                 | Consumer group id                                                                              | p4pa-registries-consumer-group                     |
| KAFKA_PAYMENTS_CONSUMER_ENABLED                    | If the consumer should read messages                                                           | false                                              |
| KAFKA_PAYMENTS_AUTO_COMMIT                         | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_AUTO_COMMIT}               |
| KAFKA_PAYMENTS_REQUEST_CONNECTIONS_MAX_IDLE_MS     | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTIONS_MAX_IDLE_MS}   |
| KAFKA_PAYMENTS_INTERVAL_TIMEOUT_MS                 | See default config description                                                                 | ${KAFKA_CONFIG_MAX_POLL_INTERVAL_TIMEOUT_MS}       |
| KAFKA_PAYMENTS_MAX_POLL_SIZE                       | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_MAX_POLL_SIZE}             |
| KAFKA_PAYMENTS_REQUEST_CONNECTION_TIMEOUT_MAX_MS   | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTION_TIMEOUT_MAX_MS} |
| KAFKA_PAYMENTS_REQUEST_CONNECTION_TIMEOUT_MS       | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTION_TIMEOUT_MS}     |
| KAFKA_PAYMENTS_STANDARD_HEADERS                    | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_STANDARD_HEADERS}          |
| KAFKA_PAYMENTS_REQUEST_START_OFFSET                | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_START_OFFSET}              |
| KAFKA_TOPIC_REGISTRIES                             | Topic where to publish registry events                                                         | p4pa-payhub-registries-evh                         |
| KAFKA_REGISTRIES_CONSUMER_SASL_JAAS_CONFIG         | JAAS Config string used to perform authentication                                              |                                                    |
| KAFKA_REGISTRIES_REGISTRIES_GROUP_ID               | Consumer group id                                                                              | p4pa-registries-consumer-group                     |
| KAFKA_REGISTRIES_CONSUMER_ENABLED                  | If the consumer should read messages                                                           | false                                              |
| KAFKA_REGISTRIES_AUTO_COMMIT                       | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_AUTO_COMMIT}               |
| KAFKA_REGISTRIES_REQUEST_CONNECTIONS_MAX_IDLE_MS   | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTIONS_MAX_IDLE_MS}   |
| KAFKA_REGISTRIES_INTERVAL_TIMEOUT_MS               | See default config description                                                                 | ${KAFKA_CONFIG_MAX_POLL_INTERVAL_TIMEOUT_MS}       |
| KAFKA_REGISTRIES_MAX_POLL_SIZE                     | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_MAX_POLL_SIZE}             |
| KAFKA_REGISTRIES_REQUEST_CONNECTION_TIMEOUT_MAX_MS | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTION_TIMEOUT_MAX_MS} |
| KAFKA_REGISTRIES_REQUEST_CONNECTION_TIMEOUT_MS     | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTION_TIMEOUT_MS}     |
| KAFKA_REGISTRIES_STANDARD_HEADERS                  | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_STANDARD_HEADERS}          |
| KAFKA_REGISTRIES_REQUEST_START_OFFSET              | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_START_OFFSET}              |

#### 🔑 keys
| ENV                          | DESCRIPTION                                         | DEFAULT |
|------------------------------|-----------------------------------------------------|---------|
| JWT_TOKEN_PUBLIC_KEY         | p4pa-auth JWT public key                            |         |
| DATA_CIPHER_HASH_PEPPER      | Base64 encoded key (256 bit) used to calculate hash |         |
| DATA_CIPHER_ENCRYPT_PASSWORD | Base64 encoded key (256 bit) used to encrypt data   |         |

## 🛠️ Getting Started

### 📝 Prerequisites

Ensure the following tools are installed on your machine:

1. **Java 21+**
2. **Gradle** (or use the Gradle wrapper included in the repository)
3. **Docker** (to build and run on an isolated environment, optional)

### 🔐 Write Locks

```sh
./gradlew dependencies --write-locks
```

### ⚙️ Build

```sh
./gradlew clean build
```

### 🧪 Test

#### 📌 JUnit
```sh
./gradlew test
```

### 🚀 Run local

```sh
./gradlew bootRun
```

### 🐳 Build & run through Docker
```sh
docker build -t <APP_NAME> .
docker run --env-file <ENV_FILE> <APP_NAME>
```

### ⚖️ Generate dependencies licenses
```sh
./gradlew generateLicenseReport
```
