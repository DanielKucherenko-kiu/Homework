FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/uno-a4-a5-1.0.0.jar app.jar
VOLUME ["/app/data"]
CMD ["java", "-jar", "app.jar"]
