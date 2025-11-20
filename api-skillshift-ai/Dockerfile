# Build stage with Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace/app
COPY . .
RUN mvn -B -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app
COPY --from=build /workspace/app/target/*-runner.jar /app/app.jar

ENV QUARKUS_HTTP_HOST=0.0.0.0
CMD ["java", "-jar", "/app/app.jar"]
