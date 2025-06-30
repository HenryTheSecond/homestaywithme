# Stage 1: Runtime environment for your application
FROM openjdk:17-jdk-slim
WORKDIR /app
ARG JAR_FILE=homestaywithme-consumer/build/libs/homestaywithme-consumer-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]