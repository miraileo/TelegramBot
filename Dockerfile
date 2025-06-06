FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY app.jar ./app.jar

EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]
