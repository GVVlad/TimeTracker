FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/TimeTrecker-0.0.1-SNAPSHOT.jar TimeTreckerApplication.jar

ENTRYPOINT ["java", "-jar", "TimeTreckerApplication.jar"]

# Відкриваємо порт для додатку
EXPOSE 8080
