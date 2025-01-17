FROM openjdk:17-jdk-slim

WORKDIR /app
COPY target/scales-app-0.0.1-SNAPSHOT.jar scales-app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "scales-app.jar"]