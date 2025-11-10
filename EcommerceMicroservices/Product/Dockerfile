FROM openjdk:25-ea

WORKDIR /app

COPY target/Product-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]