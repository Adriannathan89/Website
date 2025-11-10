FROM openjdk:25-ea

WORKDIR /app

COPY target/User-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]