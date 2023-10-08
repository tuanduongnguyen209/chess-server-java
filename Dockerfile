# Use an official Maven image as a build environment
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app


COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]