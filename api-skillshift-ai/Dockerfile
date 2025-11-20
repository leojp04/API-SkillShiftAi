# Build stage
FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace/app
COPY . .
RUN ./mvnw package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app
COPY --from=build /workspace/app/target/*-runner.jar /app/app.jar

ENV QUARKUS_HTTP_HOST=0.0.0.0
CMD ["java", "-jar", "/app/app.jar"]
