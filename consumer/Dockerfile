FROM openjdk:21-jdk-slim

WORKDIR /app

RUN mkdir -p src/main/resources/

COPY src/main/resources/prime_numbers.csv /app/src/main/resources/prime_numbers.csv

COPY target/consumer-0.0.1-SNAPSHOT.jar /app/consumer-application.jar

CMD ["java", "-jar", "/app/consumer-application.jar"]

