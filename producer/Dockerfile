FROM openjdk:21-jdk-slim

WORKDIR /app

RUN mkdir -p src/main/resources/

COPY src/main/resources/numbers.csv /app/src/main/resources/numbers.csv

COPY target/producer-0.0.1-SNAPSHOT.jar /app/producer-application.jar

CMD ["java", "-jar", "/app/producer-application.jar"]

