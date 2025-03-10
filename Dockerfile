FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.11.0/opentelemetry-javaagent.jar /app/libs/

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/product-service-0.0.1-SNAPSHOT.jar app.jar

COPY --from=builder /app/libs/opentelemetry-javaagent.jar /app/libs/

EXPOSE 8081

ENV JAVA_TOOL_OPTIONS="-javaagent:/app/libs/opentelemetry-javaagent-2.11.0.jar"

ENTRYPOINT ["java", "-jar", "app.jar"]
