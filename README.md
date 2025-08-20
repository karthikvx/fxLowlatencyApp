Based on the provided content, here's a comprehensive README.md file:

# FxLowLatency Project

A high-performance foreign exchange (FX) low-latency processing system built with Spring Boot and Kafka.

## Project Overview

This project is designed for low-latency FX data processing using modern Java technologies and reactive programming patterns.

## Technology Stack

- **Java 24** - Latest LTS version for optimal performance
- **Spring Boot 3.2.6** - Reactive web framework
- **Spring Kafka 3.1.2** - Kafka integration
- **Kafka Streams 3.7.0** - Stream processing capabilities
- **Jackson 2.17.1** - JSON serialization/deserialization
- **Micrometer 1.12.5** - Application metrics and monitoring

## Key Dependencies

### Core Frameworks
- `spring-boot-starter-webflux` - Reactive web support
- `spring-kafka` - Spring Kafka integration
- `kafka-streams` - Kafka Streams API

### Data Processing
- `jackson-databind` - JSON data binding

### Monitoring
- `micrometer-registry-prometheus` - Prometheus metrics export

## Prerequisites

- Java 24 or later
- Apache Maven
- Apache Kafka cluster
- Prometheus (for metrics monitoring, optional)

## Building the Project

```bash
mvn clean package
```

## Configuration

The project uses Maven for dependency management. Key properties are configured in the `pom.xml`:

```xml
<properties>
    <maven.compiler.source>24</maven.compiler.source>
    <maven.compiler.target>24</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <spring.boot.version>3.2.6</spring.boot.version>
    <spring.kafka.version>3.1.2</spring.kafka.version>
    <kafka.streams.version>3.7.0</kafka.streams.version>
    <jackson.version>2.17.1</jackson.version>
    <micrometer.version>1.12.5</micrometer.version>
</properties>
```

## Features

- **Reactive Processing**: Built on WebFlux for non-blocking I/O
- **Kafka Integration**: Real-time message processing
- **Stream Processing**: Kafka Streams for complex event processing
- **Metrics**: Prometheus integration for performance monitoring
- **Low Latency**: Optimized for high-frequency FX data

## Getting Started

1. Clone the repository
2. Configure Kafka connection settings
3. Build with Maven
4. Run the application
5. Monitor metrics via Prometheus endpoint

## Monitoring

The application exposes metrics on the standard actuator endpoints:
- `/actuator/prometheus` - Prometheus metrics
- `/actuator/health` - Health checks

## License

This project is proprietary software. All rights reserved.

## Support

For technical support or questions about this FX low-latency system, please contact karthikvx@gmail.com.

---
